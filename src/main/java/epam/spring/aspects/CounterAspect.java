package epam.spring.aspects;

import epam.spring.beans.Event;
import epam.spring.beans.Ticket;
import epam.spring.beans.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class CounterAspect {
    private static final String NAME_TABLE = "name_table";
    private static final String PRICE_TABLE = "price_table";
    private static final String BOOKED_TABLE = "booked_table";

    private static final String CREATE_NAME_TABLE = "create table " + NAME_TABLE + " (" +
            "event_id integer," +
            "counter integer)";
    private static final String CREATE_PRICE_TABLE = "create table " + PRICE_TABLE + " (" +
            "event_id integer," +
            "counter integer)";
    private static final String CREATE_BOOKED_TABLE = "create table " + BOOKED_TABLE + " (" +
            "event_id integer," +
            "counter integer)";

    private static final String DROP_NAME_TABLE = "drop table " + NAME_TABLE;
    private static final String DROP_PRICE_TABLE = "drop table " + PRICE_TABLE;
    private static final String DROP_BOOKED_TABLE = "drop table " + BOOKED_TABLE;

    private JdbcTemplate template;
    private Map<Event, Integer> getByNameCounter = new HashMap<>();
    private Map<Event, Integer> getPriceCounter = new HashMap<>();
    private Map<Event, Integer> bookedCounter = new HashMap<>();

    public CounterAspect(JdbcTemplate template) {
        this.template = template;
    }

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

    public void init() {
        template.execute(CREATE_NAME_TABLE);
        template.execute(CREATE_PRICE_TABLE);
        template.execute(CREATE_BOOKED_TABLE);
    }

    public void destroy() {
        template.execute(DROP_NAME_TABLE);
        template.execute(DROP_PRICE_TABLE);
        template.execute(DROP_BOOKED_TABLE);
    }
}
