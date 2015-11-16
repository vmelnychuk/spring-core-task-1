package epam.spring.jdbc_services;

import epam.spring.beans.Auditorium;
import epam.spring.services.AuditoriumService;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class AuditoriumServiceJdbc implements AuditoriumService {
    private static final String TABLE_NAME = "auditoriums";
    private static final String VIP_SEATS_TABLE = "vip_seats";
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" +
            "id integer not null AUTO_INCREMENT," +
            "name varchar(20)," +
            "seats integer," +
            "primary key (id))";
    private static final String CREATE_VIP_SEATS_TABLE = "create table " + VIP_SEATS_TABLE + "(" +
            "auditorium_id integer," +
            "vip_seat_number integer)";
    private static final String DROP_TABLE = "drop table " + TABLE_NAME;
    private static final String DROP_VIP_SEATS_TABLE = "drop table " + VIP_SEATS_TABLE;
    private static final String COLUMNS = "id, name, seats";
    private static final String VIP_SEATS_COLUMNS = "auditorium_id, vip_seat_number";
    private static final String GET_ALL = "select " + COLUMNS + " from " + TABLE_NAME;
    private static final String GET_BY_NAME = "select " + COLUMNS + " from " + TABLE_NAME + " where name = ?";
    private static final String GET_BY_ID = "select " + COLUMNS + " from " + TABLE_NAME + " where id = ?";
    private static final String GET_VIP_SEATS = "select " + VIP_SEATS_COLUMNS + " from " + VIP_SEATS_TABLE + " where auditorium_id = ?";

    private JdbcTemplate template;

    public AuditoriumServiceJdbc(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public Collection<Auditorium> getAuditoriums() {
        return template.query(GET_ALL, new AuditoriumMapper());
    }

    @Override
    public Auditorium getAuditoriumByName(String name) {
        return template.queryForObject(GET_BY_NAME, new Object[]{name}, new AuditoriumMapper());
    }

    @Override
    public Auditorium getAuditoriumById(int id) {
        return template.queryForObject(GET_BY_ID, new Object[]{id}, new AuditoriumMapper());
    }

    @Override
    public int getSeatsNumber(Auditorium auditorium) {
        Auditorium stored = template.queryForObject(GET_BY_ID, new Object[]{auditorium.getId()}, new AuditoriumMapper());
        return stored.getNumberOfSeats();
    }

    @Override
    public Collection<Integer> getVipSeats(Auditorium auditorium) {
        int id = auditorium.getId();
        return template.query(GET_VIP_SEATS, new Object[]{id}, new VipSeatsMapper());
    }

    public void init() {
        template.execute(CREATE_TABLE);
        template.execute(CREATE_VIP_SEATS_TABLE);

        template.execute("insert into " + TABLE_NAME + " (" + COLUMNS + ")" +
                "values (null, 'Big', 200)");
        template.execute("insert into " + TABLE_NAME + " (" + COLUMNS + ")" +
                "values (null, 'Medium', 150)");
        template.execute("insert into " + TABLE_NAME + " (" + COLUMNS + ")" +
                "values (null, 'Small', 80)");

        for(int i = 1; i <= 10; i++) {
            template.execute("insert into " + VIP_SEATS_TABLE + " (" + VIP_SEATS_COLUMNS + ")" +
                    "values (1, " + i + ")");
            template.execute("insert into " + VIP_SEATS_TABLE + " (" + VIP_SEATS_COLUMNS + ")" +
                    "values (2, " + i + ")");
            template.execute("insert into " + VIP_SEATS_TABLE + " (" + VIP_SEATS_COLUMNS + ")" +
                    "values (3, " + i + ")");
        }
    }

    public void destroy() {
        template.execute(DROP_TABLE);
        template.execute(DROP_VIP_SEATS_TABLE);
    }

    private static class AuditoriumMapper implements RowMapper<Auditorium> {

        @Override
        public Auditorium mapRow(ResultSet resultSet, int i) throws SQLException {
            Auditorium auditorium = new Auditorium();
            auditorium.setId(resultSet.getInt("id"));
            auditorium.setName(resultSet.getString("name"));
            auditorium.setNumberOfSeats(resultSet.getInt("seats"));
            return auditorium;
        }
    }

    private static class VipSeatsMapper implements RowMapper<Integer> {
        @Override
        public Integer mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getInt("vip_seat_number");
        }
    }
}
