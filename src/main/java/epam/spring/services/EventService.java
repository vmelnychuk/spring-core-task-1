package epam.spring.services;

import epam.spring.beans.Auditorium;
import epam.spring.beans.Event;

import java.util.Collection;
import java.util.Date;

public interface EventService {
    void create(Event event);
    void remove(Event event);
    Event getByName(String name);
    Collection<Event> getAll();
    void assignAuditorium(Event event, Auditorium auditorium, Date date);
}
