package epam.spring.map_services;

import epam.spring.beans.AssignedEvent;
import epam.spring.beans.Auditorium;
import epam.spring.beans.Event;
import epam.spring.beans.EventRating;
import epam.spring.services.AuditoriumService;
import epam.spring.services.EventService;

import java.util.*;

public class EventServiceMap implements EventService {
    private Map<Integer, Event> events;
    private List<AssignedEvent> assignedEvents;
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

    public void assignAuditorium(int eventId, Auditorium auditorium, Date date) {
        AssignedEvent assignedEvent = new AssignedEvent();
        assignedEvent.setEvent(events.get(eventId));
        assignedEvent.setAuditorium(auditoriumService.getAuditoriumByName(auditorium.getName()));
        assignedEvent.setDate(date);
        assignedEvents.add(assignedEvent);
    }

    public AuditoriumService getAuditoriumService() {
        return auditoriumService;
    }

    public void setAuditoriumService(AuditoriumService auditoriumService) {
        this.auditoriumService = auditoriumService;
    }

    public List<AssignedEvent> getAssignedEvents() {
        return assignedEvents;
    }

    public void setAssignedEvents(List<AssignedEvent> assignedEvents) {
        this.assignedEvents = assignedEvents;
    }
}
