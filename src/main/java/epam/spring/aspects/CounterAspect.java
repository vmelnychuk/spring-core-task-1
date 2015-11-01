package epam.spring.aspects;

import epam.spring.beans.Event;
import epam.spring.beans.Ticket;
import epam.spring.beans.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Map;
import java.util.Objects;

@Aspect
public class CounterAspect {
    private Map<Event, Integer> getByNameCounter;
    private Map<Event, Integer> getPriceCounter;
    private Map<Event, Integer> bookedCounter;

    public CounterAspect(Map<Event, Integer> getByNameCounter,
                         Map<Event, Integer> getPriceCounter,
                         Map<Event, Integer> bookedCounter) {
        this.getByNameCounter = getByNameCounter;
        this.getPriceCounter = getPriceCounter;
        this.bookedCounter = bookedCounter;
    }

    @Pointcut("execution(* *.getByName(..))")
    private void eventByName() {}

    @Pointcut("execution(* epam.spring.beans.Event.getPrice(..))")
    private void eventGetPrice() {}

    @Pointcut("execution(* epam.spring.services.BookingService.bookTicket(..))")
    private void ticketBookedFroEvent() {}

    @AfterReturning(
            pointcut="eventByName()",
            returning="eventObject")
    public void countGetByName(Object eventObject) {
        Event event = (Event) eventObject;
        if(!getByNameCounter.containsKey(event)) {
            getByNameCounter.put(event, 0);
        }
        getByNameCounter.put(event, getByNameCounter.get(event)+1);
    }

    @AfterReturning(
            pointcut="eventGetPrice()")
    public void countGetPrice(JoinPoint joinPoint) {
        Event event = (Event) joinPoint.getTarget();
        if(!getPriceCounter.containsKey(event)) {
            getPriceCounter.put(event, 0);
        }
        getPriceCounter.put(event, getPriceCounter.get(event)+1);
    }

    @Before("ticketBookedFroEvent() && args(user, ticket)")
    public void countBookTicket(User user, Ticket ticket) {
        Event event = ticket.getEvent();
        if(!bookedCounter.containsKey(event)) {
            bookedCounter.put(event, 0);
        }
        bookedCounter.put(event, bookedCounter.get(event)+1);
    }

    @Override
    public String toString() {
        return "CounterAspect{\n" +
                "getByNameCounter=" + getByNameCounter +
                ",\n getPriceCounter=" + getPriceCounter +
                ",\n bookedCounter=" + bookedCounter +
                '}';
    }
}
