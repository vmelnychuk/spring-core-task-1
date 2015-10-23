package epam.spring.services;

import epam.spring.beans.Event;
import epam.spring.beans.Ticket;
import epam.spring.beans.User;

import java.util.Collection;
import java.util.Date;

public interface BookingService {
    int getTicketPrice(Event event, Date date, Collection<Integer> seats, User user);
    void bookTicket(User user, Ticket ticket);
    Collection<Ticket> getTicketsForEvent(Event event, Date date);
}
