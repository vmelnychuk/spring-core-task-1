package epam.spring;

import epam.spring.aspects.CounterAspect;
import epam.spring.aspects.DiscountAspect;
import epam.spring.aspects.LuckyWinnerAspect;
import epam.spring.beans.*;
import epam.spring.services.AuditoriumService;
import epam.spring.services.BookingService;
import epam.spring.services.EventService;
import epam.spring.services.UserService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class App {
    private AuditoriumService auditoriumService;
    private BookingService bookingService;
    private EventService eventService;
    private UserService userService;

    private boolean isRunning = true;
    private int currentUserId = -1; // -1 not valid user

    private ConfigurableApplicationContext applicationContext;

    public App(AuditoriumService auditoriumService,
               BookingService bookingService,
               EventService eventService,
               UserService userService) {
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
        drawMainMenu();
    }

    public static void main( String[] args ) {
        ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        App application = applicationContext.getBean("app", App.class);
        application.setApplicationContext(applicationContext);
        while (application.isRunning) {
            application.doWork();
        }
        CounterAspect counterAspect = applicationContext.getBean("counterAspect", CounterAspect.class);
        System.out.println(counterAspect.toString());

        DiscountAspect discountAspect = applicationContext.getBean("discountAspect", DiscountAspect.class);
        System.out.println(discountAspect.toString());

        LuckyWinnerAspect luckyWinnerAspect = applicationContext.getBean("luckyWinnerAspect", LuckyWinnerAspect.class);
        System.out.println(luckyWinnerAspect.toString());

        applicationContext.close();
    }

    private void drawMainMenu() {
        System.out.println("----Main Menu----");
        System.out.println("0 - Exit");
        System.out.println("1 - Enter as user");
        System.out.println("2 - Enter as admin");
        System.out.print("Make your choice: ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        switch (choice) {
            case "0":
                isRunning = false;
                break;
            case "1":
                drawUserMenu();
                break;
            case "2":
                drawAdminMenu();
                break;
            default:
                drawMainMenu();
                break;
        }
    }

    private void drawUserMenu() {
        System.out.println("----User Menu----");
        System.out.println("0 - Exit to Main menu");
        System.out.println("1 - Register");
        System.out.println("2 - Login");
        System.out.print("Make your choice: ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        switch (choice) {
            case "0":
                drawMainMenu();
                break;
            case "1":
                registerUserMenu();
                break;
            case "2":
                loginMenu();
                break;
            default:
                drawUserMenu();
                break;
        }
    }

    private void drawAdminMenu() {
        System.out.println("----Admin Menu----");
        System.out.println("0 - Exit to Main menu");
        System.out.println("1 - Create event");
        System.out.println("2 - View purchased tickets");
        System.out.println("3 - View all events");
        System.out.println("4 - View all users");
        System.out.print("Make your choice: ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        switch (choice) {
            case "0":
                drawMainMenu();
                break;
            case "1":
                createEvent();
                drawAdminMenu();
                break;
            case "2":
                viewPurchasedTickets();
                drawAdminMenu();
                break;
            case "3":
                viewAllEvents();
                drawAdminMenu();
                break;
            case "4":
                viewAllUsers();
                drawAdminMenu();
                break;
            default:
                drawAdminMenu();
                break;
        }
    }

    private void viewAllUsers() {
        Collection<User> users= userService.getAll();
        for(User user : users) {
            System.out.println(user.toString());
        }
    }

    private void viewAllEvents() {
        Collection<Event> events = eventService.getAll();
        for(Event event : events) {
            System.out.println(event.toString());
        }
    }

    private void loginMenu() {
        System.out.println("---Login Menu----");
        Scanner scanner = new Scanner(System.in);

        System.out.print("email: ");
        String email = scanner.nextLine();

        System.out.print("password: ");
        String password = scanner.nextLine();

        User user = userService.getUserByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            System.out.println("logged in as " + user.getFullName());
            currentUserId = user.getId();
            System.out.println("curId " + currentUserId);
            drawUserActionMenu();
        } else {
            System.out.println("wrong password");
            drawUserMenu();
        }
    }

    private void drawUserActionMenu() {
        System.out.println("----User Actions Menu----");
        System.out.println("0 - Log out");
        System.out.println("1 - View Events");
        System.out.println("2 - Get ticket price and buy it");
        System.out.print("Make your choice: ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();
        switch (choice) {
            case "0":
                currentUserId = -1;
                System.out.println("===logged out");
                drawMainMenu();
                break;
            case "1":
                showAllEvents();
                drawUserActionMenu();
                break;
            case "2":
                showPriceForEventAndBuyTicket();
                drawUserActionMenu();
                break;
            default:
                drawUserActionMenu();
                break;
        }
    }

    private void showPriceForEventAndBuyTicket() {
        showAllEvents();
        System.out.println("----Show Price Menu----");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter id of event: ");
        String assignedEventId = scanner.nextLine();
        AssignedEvent assignedEvent = eventService.getAssignedEventById(Integer.parseInt(assignedEventId));

        System.out.print("Enter seat number: ");
        String seat = scanner.nextLine();

        User user = userService.getById(currentUserId);

        Ticket ticket = bookingService.getTicketPrice(assignedEvent, Integer.parseInt(seat), user);
        System.out.println("price is " + ticket.getPrice());
        System.out.print("Do you want to buy this ticket? (y/n):");
        String answer = scanner.nextLine();
        if(answer.equals("y")) {
            bookingService.bookTicket(user, ticket);
            System.out.println("the ticket was purchased");
        }
    }

    private void showAllEvents() {
        Collection<AssignedEvent> events = eventService.getAssignedEvents();
        for(AssignedEvent event : events) {
            System.out.println(event.toString());
        }
    }

    private void registerUserMenu() {
        System.out.println("----Register Menu----");
        User user = applicationContext.getBean("user", User.class);
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter birthday date (yyyy-mm-dd): ");
        String birthday = scanner.nextLine();
        Date birthdayDate = null;

        try {
            birthdayDate = new SimpleDateFormat("yyyy-MM-dd").parse(birthday);
        } catch (ParseException e) {
            birthdayDate = null;
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthday(birthdayDate);
        int userId = userService.register(user);
        System.out.println("You id is: " + userId);
        if (userId == -1) {
            System.out.println("User with email " + user.getEmail() + " is already exists");
        }
        drawUserMenu();
    }


    private void viewPurchasedTickets() {
        Collection<Ticket> tickets = bookingService.getPurchasedTickets();
        System.out.println("----Purchased Tickets----");
        if (tickets == null) return;

        for(Ticket ticket : tickets) {
            System.out.println(ticket);
        }
        System.out.println("--------");
        drawAdminMenu();
    }

    private void createEvent() {
        System.out.println("----Create Event----");
        Scanner scanner = new Scanner(System.in);

        Event event = applicationContext.getBean("event", Event.class);

        System.out.print("Name: ");
        String name = scanner.nextLine();

        System.out.print("Price: ");
        int price = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Event rating (high, mid, low): ");
        String ratingString = scanner.nextLine().toUpperCase();
        EventRating rating = EventRating.valueOf(ratingString);

        event.setName(name);
        event.setPrice(price);
        event.setRating(rating);

        int eventId = eventService.create(event);

        System.out.println("Enter auditorium id: ");
        Collection<Auditorium> auditoriums = auditoriumService.getAuditoriums();
        for(Auditorium auditorium : auditoriums) {
            System.out.println(auditorium.getId() + " " + auditorium.getName());
        }
        String auditoriumName = scanner.nextLine();
        Auditorium auditorium = auditoriumService.getAuditoriumById(Integer.parseInt(auditoriumName));

        System.out.print("Event date and time (yyyy-MM-dd-HH-mm): ");
        String dateString = scanner.nextLine();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd-HH-mm").parse(dateString);
        } catch (ParseException e) {
            date = null;
        }
        Event eventToAssign = eventService.getById(eventId);
        eventService.assignAuditorium(eventToAssign, auditorium, date);
    }
}
