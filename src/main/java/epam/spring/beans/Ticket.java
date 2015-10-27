package epam.spring.beans;

import java.util.Date;

public class Ticket {
    private Event event;
    private Date date;
    private Auditorium auditorium;
    private int seat;
    private User user;
    private int price;

    public Ticket(Event event, Date date, Auditorium auditorium, int seat, User user) {
        this.event = event;
        this.date = date;
        this.auditorium = auditorium;
        this.seat = seat;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "event=" + event.getName() +
                ", date=" + date +
                ", auditorium=" + auditorium.getName() +
                ", seat=" + seat +
                ", user=" + user.getFullName() +
                ", price=" + price +
                '}';
    }
}
