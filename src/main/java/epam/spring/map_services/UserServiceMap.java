package epam.spring.map_services;

import epam.spring.beans.Ticket;
import epam.spring.beans.User;
import epam.spring.services.UserService;

import java.util.*;

public class UserServiceMap implements UserService {
    private Map<Integer, User> users;
    private int userCount;

    public UserServiceMap() {
        this.users = new HashMap<Integer, User>();
        this.userCount = 0;
    }

    public int register(User user) {
        users.put(++userCount, user);
        return userCount;
    }

    public void remove(User user) {
        Iterator iterator = users.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Integer, User> entry = (Map.Entry) iterator.next();
            if(entry.getValue().equals(user)) {
                iterator.remove();
                break;
            }
        }

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
        for(Map.Entry<Integer, User> entry : users.entrySet()) {
            if(entry.getValue().getFirstName().equals(firstName)
                    || entry.getValue().getFirstName().equals(lastName)) {
                foundUsers.add(entry.getValue());
            }
        }
        return foundUsers;
    }

    public Collection<Ticket> getBookedTickets(User user) {
        for(Map.Entry<Integer, User> entry : users.entrySet()) {
            if(entry.getValue().equals(user)){
                return entry.getValue().getBookedTickets();
            }
        }
        return null;
    }
}
