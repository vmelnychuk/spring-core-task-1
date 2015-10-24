package epam.spring.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class Ticket {
    private Event event;
    private Date date;
    private Collection<Integer> seats;
    private User user;

    public Ticket(Event event, Date date, Collection<Integer> seats, User user) {
        this.event = event;
        this.date = date;
        this.seats = seats;
        this.user = user;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
