package epam.spring;

import epam.spring.beans.Auditorium;
import epam.spring.beans.Event;
import epam.spring.beans.EventRating;
import epam.spring.beans.User;
import epam.spring.services.*;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Scanner;

public class App {
    private AuditoriumService auditoriumService;
    private BookingService bookingService;
    private EventService eventService;
    private UserService userService;

    private boolean isRunnig = true;

    private ConfigurableApplicationContext applicationContext;

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

    public ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ConfigurableApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void doWork() {
        drawMenu();
    }

    public static void main( String[] args ) {
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        App application = applicationContext.getBean("app", App.class);
        application.setApplicationContext(applicationContext);
        while (application.isRunnig) {
            application.doWork();
        }
        applicationContext.close();
    }

    private void drawMenu() {
        System.out.println("0 - Exit");
        System.out.println("1 - User service");
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
        System.out.println("0 - Go to main menu");
        System.out.println("1 - add user");
        System.out.println("2 - ");
        System.out.println("3 - Book ticket");
        System.out.print("Make your choice: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 0:
                drawMenu();
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
    private void addUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Birthday(yyyy-mm-dd): ");
        String birthday = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        User user = applicationContext.getBean("user", User.class);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthday(birthday);
        int id = userService.register(user);
        System.out.println("id: " + id);
        doUser();
    }
}
