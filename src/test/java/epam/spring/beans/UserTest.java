package epam.spring.beans;

import org.junit.Test;


import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void testEquals() throws Exception {
        User user1 = new User();
        user1.setEmail("a@mail.com");

        User user2 = new User();
        user2.setEmail("a@mail.com");

        assertTrue(user1.equals(user2));
    }
}