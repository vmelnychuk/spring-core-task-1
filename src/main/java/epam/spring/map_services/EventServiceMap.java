package epam.spring.map_services;

import epam.spring.beans.*;
import epam.spring.services.EventService;

import java.util.*;

public class EventServiceMap implements EventService {
    private static Map<Integer, Event> events = new HashMap<Integer, Event>();
    private static int eventCount = 0;
    private static int assignedEventCount = 0;


    public Event create(String name, int price, EventRating rating) {
        Event event = new Event(name, price, rating);
        events.put(++eventCount, event);
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

    public void assignAuditorium(Event event, Auditorium auditorium, Date date) {
        event.setAuditorium(auditorium);
        event.setDate(date);
    }
}
