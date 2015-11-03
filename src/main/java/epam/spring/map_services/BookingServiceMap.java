package epam.spring.map_services;

import epam.spring.beans.*;
import epam.spring.services.BookingService;
import epam.spring.services.DiscountService;

import java.util.*;

public class BookingServiceMap implements BookingService {
    private DiscountService discountService;
    private Map<Integer, Ticket> bookedTickets;
    private Map<Event, List<Ticket>> bookedTicketsForEvent;
    private int ticketsCount;

    public BookingServiceMap(DiscountService discountService, Map<Integer, Ticket> bookedTickets) {
        this.discountService = discountService;
        this.bookedTickets = bookedTickets;
        this.ticketsCount = 0;
        this.bookedTicketsForEvent = new HashMap<>();
    }

    public Ticket getTicketPrice(Event event, Date date, Auditorium auditorium, int seatsNumber, User user) {
        int basePrice = event.getPrice();
        int additionalPrice = auditorium.getVipAdditionalPrice();
        int seatPrice = basePrice;
        if(auditorium.getVipSeats().contains(seatsNumber)) {
            seatPrice = (basePrice * (100 + additionalPrice)) / 100;
        }
        if (event.getRating() == EventRating.HIGH) {
            seatPrice += basePrice * 0.2;
        }
        int discount = discountService.getDiscount(user, event, date);
        seatPrice = (seatPrice * (100 - discount)) / 100;
        Ticket ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setDate(date);
        ticket.setAuditorium(auditorium);
        ticket.setSeat(seatsNumber);
        ticket.setUser(user);
        ticket.setPrice(seatPrice);
        return ticket;
    }

    public void bookTicket(User user, Ticket ticket) {
        if (user != null) {
            ticket.setUser(user);
            user.addTicket(ticket);
        }
        bookedTickets.put(++ticketsCount, ticket);
        Event event = ticket.getEvent();
        if(!bookedTicketsForEvent.containsKey(event)) {
            bookedTicketsForEvent.put(event, new ArrayList<Ticket>());
        }
        List<Ticket> ticketsForEvent = bookedTicketsForEvent.get(event);
        ticketsForEvent.add(ticket);
    }

    public Collection<Ticket> getTicketsForEvent(Event event, Date date) {
        Collection<Ticket> bookedTickets = bookedTicketsForEvent.get(event);
        Collection<Ticket> ticketsForDate = new ArrayList<Ticket>();
        for(Ticket ticket : bookedTickets) {
            if(ticket.getDate().equals(date)) {
                ticketsForDate.add(ticket);
            }
        }
        return ticketsForDate;
    }

    public Collection<Ticket> getPurchasedTickets() {
        return bookedTickets.values();
    }
}
