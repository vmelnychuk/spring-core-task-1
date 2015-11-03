package epam.spring.aspects;

import epam.spring.beans.User;
import junit.framework.TestCase;
import org.junit.Before;

import java.util.Date;

public class LuckyWinnerAspectTest extends TestCase {
    private LuckyWinnerAspect luckyWinnerAspect = new LuckyWinnerAspect();
    public void testCheckLucky() throws Exception {
        int length = 100;
        boolean[] array = new boolean[length];
        for(int i = 0; i < array.length; i++) {
            array[i] = luckyWinnerAspect.checkLucky(null);
        }
        int trueCountNullUser = 0;
        for(boolean luck : array) {
            if(luck) trueCountNullUser++;
        }
        assertTrue(trueCountNullUser > LuckyWinnerAspect.PROBABILITY / 2);

        int trueCountWithUser = 0;
        User user = new User("a", "a", "z", "a", new Date());
        for(int i = 0; i < array.length; i++) {
            array[i] = luckyWinnerAspect.checkLucky(user);
        }
        for(boolean luck : array) {
            if(luck) trueCountWithUser++;
        }
        assertTrue(trueCountWithUser > trueCountNullUser);
    }
}