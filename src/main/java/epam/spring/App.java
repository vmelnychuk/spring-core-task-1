package epam.spring;

import epam.spring.beans.Auditorium;
import epam.spring.beans.Event;
import epam.spring.beans.EventRating;
import epam.spring.beans.User;
import epam.spring.services.*;
import org.joda.time.DateTime;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class App {
    private AuditoriumService auditoriumService;
    private BookingService bookingService;
    private EventService eventService;
    private UserService userService;

    private boolean isRunnig = true;

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

    public void doWork() {
        drawMenu();
    }

    public static void main( String[] args ) {
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        App application = applicationContext.getBean("app", App.class);
        while (application.isRunnig) {
            application.doWork();
        }
        applicationContext.close();
    }

    private void drawMenu() {
        System.out.println("0 - Exit");
        System.out.println("1 - Add user");
        System.out.println("2 - Add event");
        System.out.println("3 - Book ticket");
        System.out.print("Make your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 0:
                isRunnig = false;
                break;
            case 1:
                doUser();
                break;
            case 2:
                doAddEvent();
                break;
            case 3:
                doBookTicket();
                break;
            default:
                drawMenu();
                break;
        }
        
    }

    private void doBookTicket() {

    }

    // TODO: add date parsing
    private void doAddEvent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Event Name: ");
        String name = scanner.nextLine();
        System.out.print("Event price: ");
        int price = scanner.nextInt();
        System.out.print("Event rating (high, mid, low): ");
        String ratingString = scanner.nextLine().toUpperCase();
        EventRating rating = EventRating.valueOf(ratingString);
        Event event = eventService.create(name, price, rating);
        System.out.print("Auditorium Name: ");
        String auditoriumName = scanner.nextLine();
        Auditorium auditorium = auditoriumService.getAuditoriumByName(auditoriumName);
        System.out.print("Event Date (yyy-mm-dd): ");
        String eventDate = scanner.nextLine();
        System.out.print("Event Time(hh-mm): ");
        String eventTime = scanner.nextLine();


    }

    private void doUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Birthday(yyyy-mm-dd): ");
        String birthdayString = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(birthdayString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateTime birthday = new DateTime(date);
        User user = new User(firstName, lastName, email, birthday, password);
        int id = userService.register(user);
        System.out.println("id: " + id);
        doWork();
    }
}
