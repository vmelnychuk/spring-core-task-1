package epam.spring.map_services;

import epam.spring.beans.*;
import epam.spring.services.AuditoriumService;
import epam.spring.services.EventService;
import org.joda.time.DateTime;

import java.util.*;

public class EventServiceMap implements EventService {
    private Map<Integer, Event> events;

    public EventServiceMap(Map<Integer, Event> events) {
        this.events = events;
    }

    public Event create(String name, int price, EventRating rating) {
        int eventId = events.size() + 1;
        Event event = new Event(name, price, rating);
        events.put(eventId, event);
        return event;
    }

    public void remove(Event event) {
        Iterator iterator = events.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, Event> entry = (Map.Entry) iterator.next();
            if(entry.getValue().equals(event)) {
                iterator.remove();
                break;
            }
        }
    }

    public Event getByName(String name) {
        for(Map.Entry<Integer, Event> entry : events.entrySet()) {
            if(entry.getValue().getName().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Collection<Event> getAll() {
        return events.values();
    }

    public void assignAuditorium(Event event, Auditorium auditorium, DateTime date) {
        event.setAuditorium(auditorium);
        event.setDate(date);
    }
}
