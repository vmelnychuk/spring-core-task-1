package epam.spring.services;

import epam.spring.beans.Event;
import epam.spring.beans.User;
import org.joda.time.DateTime;


public interface DiscountService {
    int getDiscount(User user, Event event, DateTime date);

}
