package epam.spring.aspects;


import epam.spring.beans.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Aspect
public class DiscountAspect {
    private Map<Class<?>, Integer> discountsCounter;
    private Map<List<String>, Integer> discountPerUser;

    public DiscountAspect(Map<Class<?>, Integer> discountsCounter, Map<List<String>, Integer> discountPerUser) {
        this.discountsCounter = discountsCounter;
        this.discountPerUser = discountPerUser;
    }

    @Pointcut("execution(* *.calculateDiscount(..))")
    private void discountCall() {}

    @AfterReturning(
            pointcut="discountCall()",
            returning="discountValue")
    public void countDiscount(JoinPoint point, int discountValue) {
        if (discountValue > 0) {
            Class<?> clazz = point.getTarget().getClass();
            if (!discountsCounter.containsKey(clazz)) {
                discountsCounter.put(clazz, 0);
            }
            discountsCounter.put(clazz, discountsCounter.get(clazz) + 1);

            User user = (User) point.getArgs()[0];
            List<String> discountPerUserKey = Collections.unmodifiableList(Arrays.asList(user.getEmail(), clazz.toString()));
            if (!discountPerUser.containsKey(discountPerUserKey)) {
                discountPerUser.put(discountPerUserKey, 0);
            }
            discountPerUser.put(discountPerUserKey, discountPerUser.get(discountPerUserKey) + 1);

        }
    }

    @Override
    public String toString() {
        return "DiscountAspect{" +
                "discountsCounter=" + discountsCounter +
                ", discountPerUser=" + discountPerUser +
                '}';
    }
}
