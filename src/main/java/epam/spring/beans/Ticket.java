package epam.spring.beans;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collection;

public class Ticket {
    private Event event;
    private DateTime date;
    private Collection<Integer> seats;
    private User user;

    public Ticket(Event event, DateTime date, Collection<Integer> seats, User user) {
        this.event = event;
        this.date = date;
        this.seats = seats;
        this.user = user;
    }

    public Ticket() {
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public Collection<Integer> getSeats() {
        return seats;
    }

    public void setSeats(Collection<Integer> seats) {
        this.seats = seats;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean overlap(Ticket ticket) {
        if (!ticket.event.equals(this.event)) return false;
        Collection<Integer> common = new ArrayList<Integer>(seats);
        common.retainAll(ticket.seats);
        if (common.size() != 0) return false;
        return true;
    }
}
