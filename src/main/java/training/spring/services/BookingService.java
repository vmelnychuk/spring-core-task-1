package training.spring.services;

import java.util.Collection;
import java.util.Date;

import training.spring.beans.*;

public interface BookingService {
    Ticket getTicketPrice(AssignedEvent assignedEvent, int seatNumber, User user);
    void bookTicket(User user, Ticket ticket);
    Collection<Ticket> getTicketsForEvent(Event event, Date date);
    Collection<Ticket> getPurchasedTickets();
}
