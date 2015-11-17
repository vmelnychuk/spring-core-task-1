package epam.spring.aspects;

import epam.spring.beans.Event;
import epam.spring.beans.Pair;
import epam.spring.beans.Ticket;
import epam.spring.beans.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;


@Aspect
public class CounterAspect {
    private static final String NAME_TABLE = "name_table";
    private static final String PRICE_TABLE = "price_table";
    private static final String BOOKED_TABLE = "booked_table";

    private static final String CREATE_NAME_TABLE = "create table " + NAME_TABLE + " (" +
            "event_id integer not null," +
            "counter integer," +
            "primary key (event_id))";
    private static final String CREATE_PRICE_TABLE = "create table " + PRICE_TABLE + " (" +
            "event_id integer not null," +
            "counter integer," +
            "primary key(event_id))";
    private static final String CREATE_BOOKED_TABLE = "create table " + BOOKED_TABLE + " (" +
            "event_id integer not null," +
            "counter integer," +
            "primary key (event_id))";

    private static final String COLUMNS = "event_id, counter";

    private static final String DROP_NAME_TABLE = "drop table " + NAME_TABLE;
    private static final String DROP_PRICE_TABLE = "drop table " + PRICE_TABLE;
    private static final String DROP_BOOKED_TABLE = "drop table " + BOOKED_TABLE;

    private static final String NAME_UPSERT = "insert into " + NAME_TABLE + " (" + COLUMNS + ") values (?, ?) " +
            "on duplicate key update counter = counter + 1";

    private static final String PRICE_UPSERT = "insert into " + PRICE_TABLE + " (" + COLUMNS + ") values (?, ?) " +
            "on duplicate key update counter = counter + 1";

    private static final String BOOKED_UPSERT = "insert into " + BOOKED_TABLE + " (" + COLUMNS + ") values (?, ?) " +
            "on duplicate key update counter = counter + 1";

    private static final String GET_ALL_NAME = "select " + COLUMNS + " from " + NAME_TABLE;
    private static final String GET_ALL_PRICE = "select " + COLUMNS + " from " + PRICE_TABLE;
    private static final String GET_ALL_BOOKED = "select " + COLUMNS + " from " + BOOKED_TABLE;

    private JdbcTemplate template;

    public CounterAspect(JdbcTemplate template) {
        this.template = template;
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
        final int eventId = event.getId();
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(NAME_UPSERT);
                preparedStatement.setInt(1, eventId);
                preparedStatement.setInt(2, 1);
                return preparedStatement;
            }
        });
    }

    @AfterReturning(
            pointcut="eventGetPrice()")
    public void countGetPrice(JoinPoint joinPoint) {
        Event event = (Event) joinPoint.getTarget();
        final int eventId = event.getId();
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(PRICE_UPSERT);
                preparedStatement.setInt(1, eventId);
                preparedStatement.setInt(2, 1);
                return preparedStatement;
            }
        });
    }

    @Before("ticketBookedFroEvent() && args(user, ticket)")
    public void countBookTicket(User user, Ticket ticket) {
        Event event = ticket.getEvent();
        final int eventId = event.getId();
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(BOOKED_UPSERT);
                preparedStatement.setInt(1, eventId);
                preparedStatement.setInt(2, 1);
                return preparedStatement;
            }
        });
    }

    @Override
    public String toString() {
        Collection<Pair<Integer, Integer>> names = template.query(GET_ALL_NAME, new EventMapper());
        Collection<Pair<Integer, Integer>> prices = template.query(GET_ALL_PRICE, new EventMapper());
        Collection<Pair<Integer, Integer>> booked = template.query(GET_ALL_BOOKED, new EventMapper());
        return "CounterAspect{\n" +
                "getByNameCounter=" + names.toString() +
                ",\n getPriceCounter=" + prices.toString() +
                ",\n bookedCounter=" + booked.toString() +
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

    private class EventMapper implements RowMapper<Pair<Integer, Integer>> {

        @Override
        public Pair<Integer, Integer> mapRow(ResultSet resultSet, int i) throws SQLException {
            Pair<Integer, Integer> pair = new Pair<Integer, Integer>();
            pair.setKey(resultSet.getInt("event_id"));
            pair.setValue(resultSet.getInt("counter"));
            return pair;
        }
    }
}
