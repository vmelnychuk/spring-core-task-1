package epam.spring.map_services;

import epam.spring.beans.Event;
import epam.spring.beans.User;
import epam.spring.services.DiscountService;
import epam.spring.services.DiscountStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiscountServiceMap implements DiscountService {
    private static List<DiscountStrategy> discountStrategies = new ArrayList<DiscountStrategy>();
    public int getDiscount(User user, Event event, Date date) {
        int discount = 0;
        for(DiscountStrategy discountStrategy : discountStrategies) {
            int newDiscount = discountStrategy.calculateDiscount(user, event, date);
            if (discount < newDiscount) {
                discount = newDiscount;
            }
        }
        return discount;
    }
}
