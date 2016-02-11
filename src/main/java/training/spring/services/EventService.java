package training.spring.services;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import training.spring.beans.AssignedEvent;
import training.spring.beans.Auditorium;
import training.spring.beans.Event;
import training.spring.beans.EventRating;

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
