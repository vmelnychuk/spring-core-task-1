package epam.spring.beans;

import org.joda.time.DateTime;

import java.util.Collection;

public class User {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private DateTime birthday;
    Collection<Ticket> bookedTickets;

    public User(String firstName, String lastName, String email, DateTime birthday, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Ticket> getBookedTickets() {
        return bookedTickets;
    }

    public void setBookedTickets(Collection<Ticket> bookedTickets) {
        this.bookedTickets = bookedTickets;
    }

    public DateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(DateTime birthday) {
        this.birthday = birthday;
    }

    /**
     * email field {@link User#getEmail()} is id of user
     * @param o is an other user to compare
     * @return true if emails are equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return email.equals(user.email);

    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", bookedTickets=" + bookedTickets +
                '}';
    }

    public void addTicket(Ticket ticket) {
        bookedTickets.add(ticket);
    }
}
