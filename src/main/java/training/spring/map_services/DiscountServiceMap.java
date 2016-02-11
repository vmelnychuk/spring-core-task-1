package training.spring.map_services;

import training.spring.beans.Event;
import training.spring.beans.User;
import training.spring.services.DiscountService;
import training.spring.services.DiscountStrategy;

import java.util.Collection;
import java.util.Date;

public class DiscountServiceMap implements DiscountService {
    private Collection<DiscountStrategy> discountStrategies;

    public DiscountServiceMap(Collection<DiscountStrategy> discountStrategies) {
        this.discountStrategies = discountStrategies;
    }

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
