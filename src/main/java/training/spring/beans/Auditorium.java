package training.spring.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

public class Auditorium {
    private int id;
    private String name;
    private int numberOfSeats;
    private Collection<Integer> vipSeats;
    /**
     * additional price for VIP seat, count in percents
     */
    private int vipAdditionalPrice = 100;

    public Auditorium() {
    }

    public Auditorium(String name, int numberOfSeats, Collection<Integer> vipSeats) {
        this.name = name;
        this.numberOfSeats = numberOfSeats;
        this.vipSeats = vipSeats;
    }

    public Auditorium(Properties auditoriumProperties) {
        this.name = auditoriumProperties.getProperty("name");
        this.numberOfSeats = Integer.parseInt(auditoriumProperties.getProperty("seats"));

        String[] vipSeats = auditoriumProperties.getProperty("vip").split(",");
        this.vipSeats = new ArrayList<Integer>();
        for(String vipSeat : vipSeats) {
            this.vipSeats.add(Integer.parseInt(vipSeat));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Collection<Integer> getVipSeats() {
        return vipSeats;
    }

    public void setVipSeats(Collection<Integer> vipSeats) {
        this.vipSeats = vipSeats;
    }

    public int getVipAdditionalPrice() {
        return vipAdditionalPrice;
    }

    public void setVipAdditionalPrice(int vipAdditionalPrice) {
        this.vipAdditionalPrice = vipAdditionalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auditorium that = (Auditorium) o;

        return !(name != null ? !name.equals(that.name) : that.name != null);

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Auditorium{" +
                "name='" + name + '\'' +
                ", numberOfSeats=" + numberOfSeats +
                ", vipAdditionalPrice=" + vipAdditionalPrice +
                '}';
    }
}
