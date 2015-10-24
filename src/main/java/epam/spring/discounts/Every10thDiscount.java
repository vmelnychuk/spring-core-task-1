package epam.spring.discounts;

import epam.spring.beans.Event;
import epam.spring.beans.User;
import epam.spring.services.DiscountStrategy;
import org.joda.time.DateTime;

import java.util.Date;

public class Every10thDiscount implements DiscountStrategy {
    private int discountPercent;

    public Every10thDiscount(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int calculateDiscount(User user, Event event, DateTime date) {
        int discount = 0;
        if (user.getBookedTickets().size() % 10 == 0) discount = discountPercent;
        return discount;
    }
}
