package epam.spring.services;

import epam.spring.beans.AssignedEvent;
import epam.spring.beans.Auditorium;
import epam.spring.beans.Event;
import epam.spring.beans.EventRating;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface EventService {
    int create(Event event);
    void remove(Event event);
    Event getByName(String name);
    Event getById(int eventId);
    Collection<Event> getAll();
    void assignAuditorium(Event event, Auditorium auditorium, Date date);
    AssignedEvent getAssignedEventById(int id);
    Collection<AssignedEvent> getAssignedEvents();
}
