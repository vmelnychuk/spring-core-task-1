package epam.spring.aspects;

import epam.spring.beans.Event;
import epam.spring.beans.Ticket;
import epam.spring.beans.User;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.HashMap;
import java.util.Map;

@Aspect
public class LuckyWinnerAspect {
    public static final int PROBABILITY = 65;
    private Map<Ticket, User> luckyTickets;

    public LuckyWinnerAspect() {
    }

    public LuckyWinnerAspect(Map<Ticket, User> luckyTickets) {
        this.luckyTickets = luckyTickets;
    }

    @Pointcut("execution(* epam.spring.services.BookingService.bookTicket(..))")
    private void ticketBookedFroEvent() {}

    @AfterReturning("ticketBookedFroEvent()")
    public void countBookTicket(JoinPoint joinPoint) {
        User user = (User) joinPoint.getArgs()[0];
        Ticket ticket = (Ticket) joinPoint.getArgs()[1];
        if (checkLucky(user)) {
            ticket.setPrice(0);
            luckyTickets.put(ticket, user);
        }
    }

    public boolean checkLucky(User user) {
        int seed = 0;
        if (user != null) {
            seed = user.getEmail().charAt(0);
        }
        if(Math.random() * 100 < PROBABILITY + (seed/10)) {
            return true;
        }
        return false;
    }

    public Map<Ticket, User> getLuckyTickets() {
        return luckyTickets;
    }

    public void setLuckyTickets(Map<Ticket, User> luckyTickets) {
        this.luckyTickets = luckyTickets;
    }

    @Override
    public String toString() {
        return "LuckyWinnerAspect{" +
                "luckyTickets=" + luckyTickets +
                '}';
    }
}
