package training.spring.services;

import java.util.Date;

import training.spring.beans.Event;
import training.spring.beans.User;


public interface DiscountService {
    int getDiscount(User user, Event event, Date date);

}
