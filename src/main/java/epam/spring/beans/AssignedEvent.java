package epam.spring.beans;

import java.util.Date;

public class AssignedEvent {
    private Event event;
    private Auditorium auditorium;

    public AssignedEvent() {
    }

    public AssignedEvent(Auditorium auditorium, Event event) {
        this.auditorium = auditorium;
        this.event = event;
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

    @Override
    public String toString() {
        return "AssignedEvent{" +
                "auditorium=" + auditorium +
                ", event=" + event +
                '}';
    }
}
