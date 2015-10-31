package epam.spring.aspects;

import epam.spring.beans.Event;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Map;

@Aspect
public class CounterAspect {
    private Map<Event, Integer> counter;

    public CounterAspect(Map<Event, Integer> counter) {
        this.counter = counter;
    }

    @Pointcut("execution(* *.getByName(..))")
    private void eventByName() {}

    @Before("eventByName()")
    public void eventByNameCall(JoinPoint joinPoint) {
        System.out.println("event by name " + joinPoint.getStaticPart().getSignature().toString());
        System.out.println("event by name " + joinPoint.getSignature().toString());
        System.out.println("event by name " + joinPoint.getTarget().getClass().getSimpleName());
    }

    @AfterReturning(
            pointcut="eventByName()",
            returning="eventObject")
    public void countAfterReturning(Object eventObject) {
        Event event = (Event) eventObject;
        if(!counter.containsKey(event)) {
            counter.put(event, 0);
        }
        counter.put(event, counter.get(event)+1);
        System.out.println("count " + event.toString());
    }
}
