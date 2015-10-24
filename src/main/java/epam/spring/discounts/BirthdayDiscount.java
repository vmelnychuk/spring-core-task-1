package epam.spring.discounts;

import epam.spring.beans.Event;
import epam.spring.beans.User;
import epam.spring.services.DiscountStrategy;

import java.util.Calendar;
import java.util.Date;

public class BirthdayDiscount implements DiscountStrategy {
    public int calculateDiscount(User user, Event event, Date date) {
        int discount = 0;
        if (birthDayToday(user, date)) discount = 5;
        return discount;
    }

    private boolean birthDayToday(User user, Date date) {
        Date userBirthday = user.getBirthday();
        Calendar userCalendar = Calendar.getInstance();
        Calendar dateCalendar = Calendar.getInstance();
        userCalendar.setTime(userBirthday);
        dateCalendar.setTime(date);
        if (userCalendar.get(Calendar.MONTH) != dateCalendar.get(Calendar.MONTH)) return false;
        if (userCalendar.get(Calendar.DATE) != dateCalendar.get(Calendar.DATE)) return false;
        return true;
    }
}
