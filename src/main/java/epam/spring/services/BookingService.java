package epam.spring.services;

import epam.spring.beans.Event;
import epam.spring.beans.Ticket;
import epam.spring.beans.User;
import org.joda.time.DateTime;

import java.util.Collection;

public interface BookingService {
    int getTicketPrice(Event event, DateTime date, Collection<Integer> seats, User user);
    void bookTicket(User user, Ticket ticket);
    Collection<Ticket> getTicketsForEvent(Event event, DateTime date);
}
