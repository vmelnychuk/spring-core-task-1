package epam.spring.map_services;

import epam.spring.beans.*;
import epam.spring.services.BookingService;
import epam.spring.services.DiscountService;
import org.joda.time.DateTime;

import java.util.*;

public class BookingServiceMap implements BookingService {
    private DiscountService discountService;
    private Map<Integer, Ticket> bookedTickets;
    private int ticketsCount;

    public BookingServiceMap(DiscountService discountService, Map<Integer, Ticket> bookedTickets) {
        this.discountService = discountService;
        this.bookedTickets = bookedTickets;
        this.ticketsCount = 0;
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
        ticket.setUser(user);
        user.addTicket(ticket);
        bookedTickets.put(++ticketsCount, ticket);
    }

    public Collection<Ticket> getTicketsForEvent(Event event, Date date) {
        Collection<Ticket> tickets = new ArrayList<Ticket>();
        for(Map.Entry<Integer, Ticket> entry : bookedTickets.entrySet()) {
            Ticket ticket = entry.getValue();
            if(ticket.getDate().equals(date)
                    && ticket.getEvent().equals(event)) {
                tickets.add(ticket);
            }
        }
        return tickets;
    }

    public Collection<Ticket> getPurchasedTickets() {
        return bookedTickets.values();
    }
}
