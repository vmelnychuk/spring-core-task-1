package epam.spring.beans;

import java.util.Date;

public class AssignedEvent {
    private Event event;
    private Auditorium auditorium;
    private Date date;

    public AssignedEvent() {
    }

    public AssignedEvent(Event event, Auditorium auditorium, Date date) {
        this.event = event;
        this.date = date;
        this.auditorium = auditorium;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
