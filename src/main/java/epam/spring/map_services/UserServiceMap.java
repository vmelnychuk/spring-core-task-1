package epam.spring.map_services;

import epam.spring.beans.Ticket;
import epam.spring.beans.User;
import epam.spring.services.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class UserServiceMap implements UserService {
    private Map<Integer, User> users;

    public UserServiceMap(Map<Integer, User> users) {
        this.users = users;
    }

    /**
     * Register user in data base
     * @param user
     * @return userId or -1 if user with email already exists
     */
    public int register(User user) {
        if (getUserByEmail(user.getEmail()) == null) {
            int userId = users.size() + 1;
            user.setId(userId);
            users.put(userId, user);
            return userId;
        }
        return -1;
    }

    public void remove(User user) {
        remove(user.getId());
    }

    public void remove(int id) {
        users.remove(id);
    }

    public User getById(int id) {
        return users.get(id);
    }

    public User getUserByEmail(String email) {
        for(Map.Entry<Integer, User> entry : users.entrySet()) {
            if(entry.getValue().getEmail().equals(email)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Collection<User> getUsersByName(String firstName, String lastName) {
        Collection<User> foundUsers = new ArrayList<User>();
        for(User user : users.values()) {
            if(user.getFirstName().equals(firstName)
                    && user.getLastName().equals(lastName)) {
                foundUsers.add(user);
            }
        }
        return foundUsers;
    }

    public Collection<Ticket> getBookedTickets(User user) {
        User storedUser = users.get(user.getId());
        return storedUser.getBookedTickets();
    }

    @Override
    public Collection<User> getAll() {
        return users.values();
    }
}
