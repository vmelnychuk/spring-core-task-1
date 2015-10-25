package epam.spring.services;

import epam.spring.beans.Auditorium;

import java.util.Collection;

public interface AuditoriumService {
    Collection <Auditorium> getAuditoriums();
    Auditorium getAuditoriumByName(String name);
    int getSeatsNumber(Auditorium auditorium);
    Collection<Integer> getVipSeats(Auditorium auditorium);
}
