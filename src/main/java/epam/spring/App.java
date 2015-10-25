package epam.spring;

import epam.spring.beans.Event;
import epam.spring.beans.User;
import epam.spring.services.*;
import org.joda.time.DateTime;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    private AuditoriumService auditoriumService;
    private BookingService bookingService;
    private EventService eventService;
    private UserService userService;

    public App(AuditoriumService auditoriumService, BookingService bookingService, EventService eventService, UserService userService) {
        this.auditoriumService = auditoriumService;
        this.bookingService = bookingService;
        this.eventService = eventService;
        this.userService = userService;
    }

    public AuditoriumService getAuditoriumService() {
        return auditoriumService;
    }

    public BookingService getBookingService() {
        return bookingService;
    }

    public EventService getEventService() {
        return eventService;
    }

    public UserService getUserService() {
        return userService;
    }

    public static void main( String[] args ) {
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        App application = applicationContext.getBean("app", App.class);
        System.out.println(application.getUserService().getUserByEmail("a@mail.com").toString());
        System.out.println(application.getEventService().getAll().toString());
        System.out.println(application.getAuditoriumService().getAuditoriums().toString());
        Event event = applicationContext.getBean("event1", Event.class);
        System.out.println(application.getBookingService().getTicketsForEvent(event, new DateTime()));
    }
}
