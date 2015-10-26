package epam.spring.services;

import epam.spring.beans.Event;
import epam.spring.beans.User;

import java.util.Date;


public interface DiscountStrategy {
    int calculateDiscount(User user, Event event, Date date);
}
