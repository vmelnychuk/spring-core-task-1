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

    public int getTicketPrice(Event event, DateTime date, Collection<Integer> seats, User user) {
        int totalPrice = 0;
        int basePrice = event.getPrice();
        Auditorium auditorium = event.getAuditorium();
        int additionalPrice = auditorium.getVipAdditionalPrice();
        for(Integer seat : seats) {
            int seatPrice = 0;
            if(auditorium.getVipSeats().contains(seat)) {
                seatPrice = (basePrice * (100 + additionalPrice)) / 100;
            } else {
                seatPrice = basePrice;
            }
            totalPrice +=seatPrice;
        }
        int discount = discountService.getDiscount(user, event, date);
        totalPrice = (totalPrice * (100 - discount)) / 100;
        return totalPrice;
    }

    public void bookTicket(User user, Ticket ticket) {
        //TODO: check if ticket is overlap with getTicketsForEvent
        ticket.setUser(user);
        bookedTickets.put(++ticketsCount, ticket);
    }

    public Collection<Ticket> getTicketsForEvent(Event event, DateTime date) {
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
}
