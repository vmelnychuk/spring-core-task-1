package training.spring.services;

import java.util.Collection;

import training.spring.beans.Auditorium;

public interface AuditoriumService {
    Collection <Auditorium> getAuditoriums();
    Auditorium getAuditoriumByName(String name);
    Auditorium getAuditoriumById(int id);
    int getSeatsNumber(Auditorium auditorium);
    Collection<Integer> getVipSeats(Auditorium auditorium);
}
