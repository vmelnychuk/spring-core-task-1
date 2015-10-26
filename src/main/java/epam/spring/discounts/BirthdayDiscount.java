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

    public int calculateDiscount(User user, Event event, Date date) {
        int discount = 0;
        if (birthDayAtDate(user, date)) discount = discountPercent;
        return discount;
    }

    public boolean birthDayAtDate(User user, Date date) {
        Calendar userBirthday = user.getBirthday();
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        if (userBirthday.get(Calendar.MONTH) != dateCalendar.get(Calendar.MONTH)) return false;
        if (userBirthday.get(Calendar.DATE) != dateCalendar.get(Calendar.DATE)) return false;
        return true;
    }
}
