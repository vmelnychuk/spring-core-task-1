package epam.spring.beans;

import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testParseDate() throws Exception {
        User user = new User();
        Calendar calendar = user.parseDate("2015-10-26");
        assertEquals(2015, calendar.get(Calendar.YEAR));
        assertEquals(9, calendar.get(Calendar.MONTH));
        assertEquals(26, calendar.get(Calendar.DATE));
    }

    @Test
    public void testEquals() throws Exception {
        User user1 = new User();
        user1.setEmail("a@mail.com");

        User user2 = new User();
        user2.setEmail("a@mail.com");

        assertTrue(user1.equals(user2));
    }
}