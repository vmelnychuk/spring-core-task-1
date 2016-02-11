package training.spring.jdbc_services;

import training.spring.beans.Ticket;
import training.spring.beans.User;
import training.spring.services.UserService;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class UserServiceJdbc implements UserService {
    private static final String TABLE_NAME = "users";
    private static final String COLUMNS = "id, firstName, lastName, email, password, birthday";
    private static final String CREATE_TABLE = "create table "+ TABLE_NAME +
            "(" +
            "id integer not null AUTO_INCREMENT," +
            "firstName varchar(20)," +
            "lastName varchar(20)," +
            "email varchar(20)," +
            "password varchar(20)," +
            "birthday date," +
            "primary key (id)" +
            ")";
    private static final String DROP_TABLE = "drop table " + TABLE_NAME;
    private static final String INSERT_QUERY = "insert into " + TABLE_NAME + " (" + COLUMNS + ") values (null,?,?,?,?,?)";
    private static final String GET_BY_ID = "select " + COLUMNS + " from " + TABLE_NAME + " where id = ?";
    private static final String GET_BY_EMAIL = "select " + COLUMNS + " from " + TABLE_NAME + " where email = ?";
    private static final String GET_BY_NAME = "select " + COLUMNS + " from " + TABLE_NAME + " where firstName = ? and lastName = ?";
    private static final String GET_ALL = "select " + COLUMNS + " from " + TABLE_NAME;
    private static final String DELETE_QUERY = "delete from " + TABLE_NAME + " where id = ?";

    private JdbcTemplate jdbcTemplate;

    public UserServiceJdbc(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int register(final User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getPassword());
                preparedStatement.setDate(5, new java.sql.Date(user.getBirthday().getTime()));
                return preparedStatement;
            }
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user.getId();
    }

    @Override
    public void remove(User user) {
        int usedId = user.getId();
        remove(usedId);
    }

    @Override
    public void remove(int id) {
        jdbcTemplate.update(DELETE_QUERY, new Object[]{id});
    }

    @Override
    public User getById(int id) {
        return jdbcTemplate.queryForObject(GET_BY_ID, new Object[]{id}, new UserMapper());
    }

    @Override
    public User getUserByEmail(String email) {
        return jdbcTemplate.queryForObject(GET_BY_EMAIL, new Object[]{email}, new UserMapper());
    }

    @Override
    public Collection<User> getUsersByName(String firstName, String lastName) {
        return jdbcTemplate.query(GET_BY_NAME, new Object[]{firstName, lastName}, new UserMapper());
    }

    //TODO: add implementation
    @Override
    public Collection<Ticket> getBookedTickets(User user) {
        return null;
    }

    @Override
    public Collection<User> getAll() {
        return jdbcTemplate.query(GET_ALL, new UserMapper());
    }

    public void init() {
        jdbcTemplate.execute(CREATE_TABLE);
        jdbcTemplate.execute("insert into " + TABLE_NAME + "(" + COLUMNS + ")" +
                "values (null, 'admin', 'admin', 'admin', 'admin', '1990-02-03')");
        jdbcTemplate.execute("insert into " + TABLE_NAME + " (" + COLUMNS + ")" +
                "values (null, 'user', 'user', 'user', 'user', '1991-03-04')");
    }

    public void destroy() {
        jdbcTemplate.execute(DROP_TABLE);
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setFirstName(resultSet.getString("firstName"));
            user.setLastName(resultSet.getString("lastName"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setBirthday(resultSet.getDate("birthday"));
            return user;
        }
    }
}
