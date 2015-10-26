package epam.spring.services;

import epam.spring.beans.Ticket;
import epam.spring.beans.User;

import java.util.Collection;

public interface UserService {
    int register(User user);
    void remove(User user);
    void remove(int id);
    User getById(int id);
    User getUserByEmail(String email);
    Collection<User> getUsersByName(String firstName, String lastName);
    Collection<Ticket> getBookedTickets(User user);
}
