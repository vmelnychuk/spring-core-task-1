package training.spring.jdbc_services;

import training.spring.beans.*;
import training.spring.services.*;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

public class BookingServiceJdbc implements BookingService {
    private static final String TABLE_NAME = "purchased_tickets";
    private static final String COLUMNS = "ticket_id, assigned_event_id, price, seat_id, user_id";
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" +
            "ticket_id integer not null AUTO_INCREMENT," +
            "assigned_event_id integer," +
            "price integer," +
            "seat_id integer," +
            "user_id integer," +
            "primary key(ticket_id))";
    private static final String DROP_TABLE = "drop table " + TABLE_NAME;
    private static final String INSERT_TICKET = "insert into " + TABLE_NAME + " ( " + COLUMNS + " )"
            + " values (null,?,?,?,?)";

    private static final String GET_ALL = "select " + COLUMNS + " from " + TABLE_NAME;


    private JdbcTemplate template;
    private DiscountService discountService;
    private AuditoriumService auditoriumService;
    private EventService eventService;
    private UserService userService;


    public BookingServiceJdbc(JdbcTemplate template,
                              DiscountService discountService,
                              AuditoriumService auditoriumService,
                              EventService eventService,
                              UserService userService) {
        this.template = template;
        this.discountService = discountService;
        this.auditoriumService = auditoriumService;
        this.eventService = eventService;
        this.userService = userService;
    }

    @Override
    public Ticket getTicketPrice(AssignedEvent assignedEvent, int seatNumber, User user) {
        Event event = assignedEvent.getEvent();
        Auditorium auditorium = assignedEvent.getAuditorium();
        Date date = assignedEvent.getDate();
        int basePrice = event.getPrice();
        int additionalPrice = auditorium.getVipAdditionalPrice();
        int seatPrice = basePrice;
        if(isVipSeat(auditorium, seatNumber)) {
            seatPrice = (basePrice * (100 + additionalPrice)) / 100;
        }
        if (event.getRating() == EventRating.HIGH) {
            seatPrice += basePrice * 0.2;
        }
        int discount = discountService.getDiscount(user, event, date);
        seatPrice = (seatPrice * (100 - discount)) / 100;
        Ticket ticket = new Ticket();
        ticket.setEvent(event);
        ticket.setDate(date);
        ticket.setAuditorium(auditorium);
        ticket.setSeat(seatNumber);
        ticket.setUser(user);
        ticket.setPrice(seatPrice);
        ticket.setAssignedEventId(assignedEvent.getId());
        return ticket;
    }

    @Override
    public void bookTicket(User user, final Ticket ticket) {
        if (user != null) {
            ticket.setUser(user);
            user.addTicket(ticket);
        }
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TICKET);
                preparedStatement.setInt(1, ticket.getAssignedEventId());
                preparedStatement.setInt(2, ticket.getPrice());
                preparedStatement.setInt(3, ticket.getSeat());
                preparedStatement.setInt(4, ticket.getUser().getId());
                return preparedStatement;
            }
        });
    }

    @Override
    public Collection<Ticket> getTicketsForEvent(Event event, Date date) {
        return null;
    }

    @Override
    public Collection<Ticket> getPurchasedTickets() {
        return template.query(GET_ALL, new TicketMapper());
    }

    public void init() {
        template.execute(CREATE_TABLE);
    }

    public void destroy() {
        template.execute(DROP_TABLE);
    }

    private boolean isVipSeat(Auditorium auditorium, int seat) {
        return auditoriumService.getVipSeats(auditorium).contains(seat);
    }

    private class TicketMapper implements RowMapper<Ticket> {
        @Override
        public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
            Ticket ticket = new Ticket();
            ticket.setId(resultSet.getInt("ticket_id"));
            int assignedEventId = resultSet.getInt("assigned_event_id");
            AssignedEvent assignedEvent = eventService.getAssignedEventById(assignedEventId);
            ticket.setAuditorium(assignedEvent.getAuditorium());
            ticket.setEvent(assignedEvent.getEvent());
            ticket.setDate(assignedEvent.getDate());
            ticket.setAssignedEventId(assignedEventId);
            ticket.setPrice(resultSet.getInt("price"));
            ticket.setSeat(resultSet.getInt("seat_id"));
            int userId = resultSet.getInt("user_id");
            User user = userService.getById(userId);
            ticket.setUser(user);
            return ticket;
        }
    }
}
