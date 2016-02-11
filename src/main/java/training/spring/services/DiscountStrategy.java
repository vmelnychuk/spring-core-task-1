package training.spring.services;

import java.util.Date;

import training.spring.beans.Event;
import training.spring.beans.User;


public interface DiscountStrategy {
    int calculateDiscount(User user, Event event, Date date);
}
