package training.spring.discounts;

import training.spring.beans.Event;
import training.spring.beans.User;
import training.spring.services.DiscountStrategy;

import java.util.Calendar;
import java.util.Date;

public class BirthdayDiscount implements DiscountStrategy {
    private int discountPercent;
    public BirthdayDiscount(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int calculateDiscount(User user, Event event, Date date) {
        int discount = 0;
        if (user == null || user.getBirthday() == null) return discount;
        if (birthDayAtDate(user, date)) discount = discountPercent;
        return discount;
    }

    public boolean birthDayAtDate(User user, Date date) {
        Date userBirthday = user.getBirthday();
        Calendar dateCalendar = Calendar.getInstance();
        dateCalendar.setTime(date);
        Calendar userBirthdayCalendar = Calendar.getInstance();
        userBirthdayCalendar.setTime(userBirthday);
        if (userBirthdayCalendar.get(Calendar.MONTH) != dateCalendar.get(Calendar.MONTH)) return false;
        if (userBirthdayCalendar.get(Calendar.DATE) != dateCalendar.get(Calendar.DATE)) return false;
        return true;
    }
}
