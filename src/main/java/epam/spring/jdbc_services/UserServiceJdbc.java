package epam.spring.jdbc_services;

import epam.spring.beans.Ticket;
import epam.spring.beans.User;
import epam.spring.services.UserService;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Collection;

public class UserServiceJdbc implements UserService {
    private JdbcTemplate jdbcTemplate;

    @Override
    public int register(User user) {
        jdbcTemplate.update("INSERT INTO users (email)", user.getEmail());
        return 0;
    }

    @Override
    public void remove(User user) {

    }

    @Override
    public void remove(int id) {

    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return null;
    }

    @Override
    public Collection<User> getUsersByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Collection<Ticket> getBookedTickets(User user) {
        return null;
    }

    @Override
    public Collection<User> getAll() {
        return null;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
