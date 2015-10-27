package epam.spring.services;

import epam.spring.beans.AssignedEvent;
import epam.spring.beans.Auditorium;
import epam.spring.beans.Event;
import epam.spring.beans.EventRating;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface EventService {
    Event create(String name, int price, EventRating rating);
    int create(Event event);
    void remove(Event event);
    Event getByName(String name);
    Event getById(int eventId);
    Collection<Event> getAll();
    void assignAuditorium(int eventId, Auditorium auditorium, Date date);
    List<AssignedEvent> getAssignedEvents();
}
