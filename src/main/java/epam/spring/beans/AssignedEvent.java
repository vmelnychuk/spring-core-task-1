package epam.spring.beans;

import java.util.Date;

public class AssignedEvent {
    private int id;
    private Event event;
    private Auditorium auditorium;
    private Date date;

    public AssignedEvent() {
    }

    public AssignedEvent(Auditorium auditorium, Event event, Date date) {
        this.auditorium = auditorium;
        this.event = event;
        this.date = date;
    }

    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AssignedEvent{" +
                "id=" + id +
                ", event=" + event +
                ", auditorium=" + auditorium +
                ", date=" + date +
                '}';
    }
}
