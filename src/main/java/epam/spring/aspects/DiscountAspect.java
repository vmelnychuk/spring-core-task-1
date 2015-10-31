package epam.spring.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Map;

@Aspect
public class DiscountAspect {
    private Map<Class<?>, Integer> discountsCounter;

    public DiscountAspect(Map<Class<?>, Integer> discountsCounter) {
        this.discountsCounter = discountsCounter;
    }

    @Pointcut("execution(* *.calculateDiscount(..))")
    private void discountCall() {}

    @AfterReturning(
            pointcut="discountCall()")
    public void countDiscount(JoinPoint point) {
        Class<?> clazz = point.getTarget().getClass();
        if(!discountsCounter.containsKey(clazz)) {
            discountsCounter.put(clazz, 0);
        }
        discountsCounter.put(clazz, discountsCounter.get(clazz)+1);
    }
}
