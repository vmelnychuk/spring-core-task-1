package epam.spring.beans;

import org.joda.time.DateTime;

import java.util.Date;

public class Event {
    private String name;
    private int price;
    private EventRating rating;
    private Auditorium auditorium;
    private DateTime date;

    public Event() {
    }

    public Event(String name, int price, EventRating rating) {
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public EventRating getRating() {
        return rating;
    }

    public void setRating(EventRating rating) {
        this.rating = rating;
    }
    
    public Auditorium getAuditorium() {
        return auditorium;
    }

    public void setAuditorium(Auditorium auditorium) {
        this.auditorium = auditorium;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Event event = (Event) o;

        if (price != event.price) return false;
        if (name != null ? !name.equals(event.name) : event.name != null) return false;
        if (rating != event.rating) return false;
        if (auditorium != null ? !auditorium.equals(event.auditorium) : event.auditorium != null) return false;
        return !(date != null ? !date.equals(event.date) : event.date != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + price;
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (auditorium != null ? auditorium.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Event{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                ", auditorium=" + auditorium +
                ", date=" + date +
                '}';
    }
}
