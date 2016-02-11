package training.spring.discounts;

import training.spring.beans.Event;
import training.spring.beans.User;
import training.spring.services.DiscountStrategy;

import java.util.Date;

public class Every10thDiscount implements DiscountStrategy {
    private int discountPercent;

    public Every10thDiscount(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int calculateDiscount(User user, Event event, Date date) {
        int discount = 0;
        if (user != null && user.getBookedTickets() != null) {
            int boughtTickets = user.getBookedTickets().size();
            if (boughtTickets > 0 && boughtTickets % 10 == 0) discount = discountPercent;
        }
        return discount;
    }
}
