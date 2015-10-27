package epam.spring.map_services;

import epam.spring.beans.Auditorium;
import epam.spring.services.AuditoriumService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class AuditoriumServiceMap implements AuditoriumService {
    private Map<Integer, Auditorium> auditoriums;

    public Collection<Auditorium> getAuditoriums() {
        return auditoriums.values();
    }

    public Auditorium getAuditoriumByName(String name) {
        for(Map.Entry<Integer, Auditorium> entry : auditoriums.entrySet()) {
            if(entry.getValue().getName().equals(name)) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Auditorium getAuditoriumById(int id) {
        return auditoriums.get(id);
    }

    public AuditoriumServiceMap(Map<Integer, Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    public int getSeatsNumber(Auditorium auditorium) {
        Auditorium foundAuditorium = findAuditorium(auditorium);
        return foundAuditorium.getNumberOfSeats();
    }

    public Collection<Integer> getVipSeats(Auditorium auditorium) {
        Auditorium foundAuditorium = findAuditorium(auditorium);
        return foundAuditorium.getVipSeats();
    }

    private Auditorium findAuditorium(Auditorium auditorium) {
        for(Map.Entry<Integer, Auditorium> entry : auditoriums.entrySet()) {
            if(entry.getValue().equals(auditorium)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
