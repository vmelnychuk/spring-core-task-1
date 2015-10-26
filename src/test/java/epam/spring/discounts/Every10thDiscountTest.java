package epam.spring.discounts;

import epam.spring.beans.User;
import epam.spring.services.DiscountStrategy;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class Every10thDiscountTest {
    private DiscountStrategy discountStrategy;

    @Before
    public void setUp() throws Exception {
        discountStrategy = new Every10thDiscount(50);
    }

    @Test
    public void testCalculateDiscount() throws Exception {
        int discount = discountStrategy.calculateDiscount(new User(), null, null);
        assertEquals(0, discount);
    }
}