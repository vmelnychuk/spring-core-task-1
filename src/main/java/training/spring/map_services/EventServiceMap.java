package training.spring.map_services;

import training.spring.beans.AssignedEvent;
import training.spring.beans.Auditorium;
import training.spring.beans.Event;
import training.spring.beans.EventRating;
import training.spring.services.AuditoriumService;
import training.spring.services.EventService;

import java.util.*;

public abstract class EventServiceMap implements EventService {
    private Map<Integer, Event> events;
    private Map<Date, AssignedEvent> assignedEvents;
    private AuditoriumService auditoriumService;

    public EventServiceMap(Map<Integer, Event> events) {
        this.events = events;
    }

    public Event create(String name, int price, EventRating rating) {
        int eventId = events.size() + 1;
        Event event = new Event(name, price, rating);
        events.put(eventId, event);
        return event;
    }

    public int create(Event event) {
        int eventId = events.size() + 1;
        event.setId(eventId);
        events.put(eventId, event);
        return eventId;
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

    public Event getById(int eventId) {
        return events.get(eventId);
    }

    public Collection<Event> getAll() {
        return events.values();
    }

    public void assignAuditorium(Event event, Auditorium auditorium, Date date) {
        AssignedEvent assignedEvent = new AssignedEvent();
        assignedEvent.setEvent(getByName(event.getName()));
        assignedEvent.setAuditorium(auditoriumService.getAuditoriumByName(auditorium.getName()));
        assignedEvents.put(date, assignedEvent);
    }

    public AuditoriumService getAuditoriumService() {
        return auditoriumService;
    }

    public void setAuditoriumService(AuditoriumService auditoriumService) {
        this.auditoriumService = auditoriumService;
    }

    public void setAssignedEvents(Map<Date, AssignedEvent> assignedEvents) {
        this.assignedEvents = assignedEvents;
    }
}
