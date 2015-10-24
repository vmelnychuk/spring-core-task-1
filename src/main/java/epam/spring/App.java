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

    public static void main( String[] args ) {
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        User user = applicationContext.getBean("user", User.class);
        System.out.println(user.getEmail());
        User user1 = applicationContext.getBean("user", User.class);
        System.out.println(user1.getEmail());
    }
}
