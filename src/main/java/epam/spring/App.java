package epam.spring;

import epam.spring.beans.User;
import epam.spring.services.*;
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
        User user = applicationContext.getBean("user", User.class);
        UserService userService = application.getUserService();
        userService.register(user);
        System.out.println(userService.getById(1));
    }
}
