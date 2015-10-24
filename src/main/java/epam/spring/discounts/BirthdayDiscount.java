package epam.spring.discounts;

import epam.spring.beans.Event;
import epam.spring.beans.User;
import epam.spring.services.DiscountStrategy;
import org.joda.time.DateTime;

import java.util.Calendar;
import java.util.Date;

public class BirthdayDiscount implements DiscountStrategy {
    private int discountPercent;
    public BirthdayDiscount(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int calculateDiscount(User user, Event event, DateTime date) {
        int discount = 0;
        if (birthDayToday(user, date)) discount = discountPercent;
        return discount;
    }

    private boolean birthDayToday(User user, DateTime date) {
        DateTime userBirthday = user.getBirthday();
        if (userBirthday.getMonthOfYear() != date.getMonthOfYear()) return false;
        if (userBirthday.getDayOfMonth() != date.getDayOfMonth()) return false;
        return true;
    }
}
