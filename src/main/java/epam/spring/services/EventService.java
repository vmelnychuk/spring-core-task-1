package epam.spring.services;

import epam.spring.beans.Auditorium;
import epam.spring.beans.Event;
import epam.spring.beans.EventRating;

import java.util.Collection;
import java.util.Date;

public interface EventService {
    Event create(String name, int price, EventRating rating);
    void remove(Event event);
    Event getByName(String name);
    Collection<Event> getAll();
    void assignAuditorium(int eventId, int auditoriumId, Date date);
}
