package epam.spring.beans;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Calendar birthday;
    Collection<Ticket> bookedTickets;

    public User() {
    }

    public User(String firstName, String lastName, String email, String password, String birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = parseDate(birthday);
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

    public Calendar getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = parseDate(birthday);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    /**
     * Parses string in format yyyy-mm-dd e.g. 2015-10-26 to Calendar object
     * @param date
     * format yyyy-mm-dd
     * @return calendar object
     */
    public Calendar parseDate(String date) {
        Calendar calendar = Calendar.getInstance();
        Date day = null;
        try {
            day = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(day);
        return calendar;
    }
}
