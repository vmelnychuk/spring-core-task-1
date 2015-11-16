package epam.spring.jdbc_services;

import epam.spring.beans.AssignedEvent;
import epam.spring.beans.Auditorium;
import epam.spring.beans.Event;
import epam.spring.beans.EventRating;
import epam.spring.services.EventService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EventServiceJdbc implements EventService {
    private static final String TABLE_NAME = "events";
    private static final String COLUMNS = "id, name, price, rating";
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + " (" +
            "id integer not null AUTO_INCREMENT," +
            "name varchar(20)," +
            "price integer," +
            "rating varchar(10)," +
            "primary key (id))";
    private static final String DROP_TABLE = "drop table " + TABLE_NAME;
    private static final String ASSIGNED_EVENTS_TABLE = "assigned_events";
    private static final String ASSIGNED_EVENTS_COLUMNS = "assigned_id, event_id, date_time, auditorium_id";
    private static final String CREATE_ASSIGNED_EVENTS_TABLE = "create table " + ASSIGNED_EVENTS_TABLE + " (" +
            "assigned_id integer not null AUTO_INCREMENT," +
            "event_id integer," +
            "date_time timestamp," +
            "auditorium_id integer," +
            "primary key (assigned_id))";
    private static final String DROP_ASSIGNED_EVENTS_TABLE = "drop table " + ASSIGNED_EVENTS_TABLE;

    private static final String DELETE_QUERY = "delete from " + TABLE_NAME + " where id = ?";
    private static final String DELETE_FORM_ASSIGNED_QUERY = "delete from " + ASSIGNED_EVENTS_TABLE + " where event_id = ?";

    private static final String INSERT_QUERY = "insert into " + TABLE_NAME + " (" + COLUMNS + ") values (null,?,?,?)";

    private static final String GET_BY_NAME = "select " + COLUMNS + " from " + TABLE_NAME + " where name = ?";
    private static final String GET_BY_ID = "select " + COLUMNS + " from " + TABLE_NAME + " where id = ?";
    private static final String GET_ALL = "select " + COLUMNS + " from " + TABLE_NAME;

    private static final String ASSIGN_EVENT = "insert into " + ASSIGNED_EVENTS_TABLE
            + " (" + ASSIGNED_EVENTS_COLUMNS + ") values (null,?,?,?)";

    private static final String GET_ASSIGNED_EVENTS =
            "select " + COLUMNS + ", " + ASSIGNED_EVENTS_COLUMNS
                    + " from " + TABLE_NAME + " join " + ASSIGNED_EVENTS_TABLE
                    + " on " + TABLE_NAME + ".id = " + ASSIGNED_EVENTS_TABLE + ".event_id";

    private static final String GET_ASSIGNED_EVENT =
            "select " + ASSIGNED_EVENTS_COLUMNS
            + " from " + ASSIGNED_EVENTS_TABLE + " where assigned_id = ?";

    private JdbcTemplate template;
    private AuditoriumServiceJdbc auditoriumService;

    public EventServiceJdbc(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public int create(final Event event) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, event.getName());
                preparedStatement.setInt(2, event.getPrice());
                preparedStatement.setString(3, event.getRating().toString());
                return preparedStatement;
            }
        }, keyHolder);
        event.setId(keyHolder.getKey().intValue());
        return event.getId();
    }

    @Override
    public void remove(Event event) {
        int id = event.getId();
        template.update(DELETE_QUERY, new Object[]{id});
        template.update(DELETE_FORM_ASSIGNED_QUERY, new Object[]{id});
    }

    @Override
    public Event getByName(String name) {
        return template.queryForObject(GET_BY_NAME, new Object[]{name}, new EventMapper());
    }

    @Override
    public Event getById(int eventId) {
        return template.queryForObject(GET_BY_ID, new Object[]{eventId}, new EventMapper());
    }

    @Override
    public Collection<Event> getAll() {
        return template.query(GET_ALL, new EventMapper());
    }

    @Override
    public void assignAuditorium(Event event, Auditorium auditorium, Date date) {
        template.update(ASSIGN_EVENT, new Object[]{
                event.getId(),
                new Timestamp(date.getTime()),
                        auditorium.getId()
        });
    }

    @Override
    public AssignedEvent getAssignedEventById(int id) {
        return template.queryForObject(GET_ASSIGNED_EVENT, new Object[]{id},new AssignedEventMapper());
    }

    @Override
    public Collection<AssignedEvent> getAssignedEvents() {
        return template.query(GET_ASSIGNED_EVENTS, new ResultSetExtractor<Collection<AssignedEvent>>() {
            @Override
            public Collection<AssignedEvent> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                Map<Integer, AssignedEvent> assignedEvents = new HashMap<Integer, AssignedEvent>();
                AssignedEvent assignedEvent;
                while (resultSet.next()) {
                    Integer assignedId = resultSet.getInt("assigned_id");
                    assignedEvent = assignedEvents.get(assignedId);
                    if(assignedEvent == null) {
                        assignedEvent = new AssignedEvent();
                        assignedEvent.setId(resultSet.getInt("assigned_id"));
                        assignedEvent.setDate(new Date(resultSet.getDate("date_time").getTime()));
                        int auditoriumId = resultSet.getInt("auditorium_id");
                        Auditorium auditorium = auditoriumService.getAuditoriumById(auditoriumId);
                        assignedEvent.setAuditorium(auditorium);
                        Event event = getById(resultSet.getInt("event_id"));
                        assignedEvent.setEvent(event);
                    }
                    assignedEvents.put(assignedId, assignedEvent);
                }
                return assignedEvents.values();
            }
        });
    }

    public void setAuditoriumService(AuditoriumServiceJdbc auditoriumService) {
        this.auditoriumService = auditoriumService;
    }

    public AuditoriumServiceJdbc getAuditoriumService() {
        return auditoriumService;
    }

    public void destroy() {
        template.execute(DROP_TABLE);
        template.execute(DROP_ASSIGNED_EVENTS_TABLE);
    }

    public void init() {
        template.execute(CREATE_TABLE);
        template.execute(CREATE_ASSIGNED_EVENTS_TABLE);
    }

    private static class EventMapper implements RowMapper<Event> {
        @Override
        public Event mapRow(ResultSet resultSet, int i) throws SQLException {
            Event event = new Event();
            event.setId(resultSet.getInt("id"));
            event.setName(resultSet.getString("name"));
            event.setPrice(resultSet.getInt("price"));
            event.setRating(EventRating.valueOf(resultSet.getString("rating")));
            return event;
        }
    }

    private class AssignedEventMapper implements RowMapper<AssignedEvent> {
        @Override
        public AssignedEvent mapRow(ResultSet resultSet, int i) throws SQLException {
            AssignedEvent assignedEvent = new AssignedEvent();
            assignedEvent.setId(resultSet.getInt("assigned_id"));
            Event event = getById(resultSet.getInt("event_id"));
            assignedEvent.setEvent(event);
            int auditoriumId = resultSet.getInt("auditorium_id");
            Auditorium auditorium = auditoriumService.getAuditoriumById(auditoriumId);
            assignedEvent.setAuditorium(auditorium);
            assignedEvent.setDate(new Date(resultSet.getDate("date_time").getTime()));
            return assignedEvent;
        }
    }
}
