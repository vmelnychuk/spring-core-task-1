package epam.spring.discounts;

import epam.spring.beans.User;
import epam.spring.services.DiscountStrategy;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

public class BirthdayDiscountTest {
    private DiscountStrategy discountStrategy;

    @Before
    public void setUp() throws Exception {
        discountStrategy = new BirthdayDiscount(10);
    }

    @Test
    public void testCalculateDiscount() throws Exception {
        User user = new User("Peter", "P", "p@mail.com", "2000-03-29", "password");
        int discount10 = discountStrategy.calculateDiscount(user, null, new SimpleDateFormat("yyyy-MM-dd").parse("2015-03-29"));
        assertEquals(10, discount10);
        int discount0 = discountStrategy.calculateDiscount(user, null, new SimpleDateFormat("yyyy-MM-dd").parse("2015-04-29"));
        assertEquals(0, discount0);
    }

}