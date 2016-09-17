package FlyingDogAirlines.DataAccessLayer;

import FlyingDogAirlines.ApplicationLayer.DataType.Debug;
import FlyingDogAirlines.ApplicationLayer.DataType.FlightSchedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class DBFlightSchedule implements DBInterface {
    //gets connection from database class
    private static Connection conn = Database.getConn();
    private static Statement stmt = null;

    @Override
    public ArrayList readAll() {
        ArrayList<FlightSchedule> schedules = new ArrayList<>();
        schedules.clear();
        try {
            String sql = "SELECT * FROM flight_schedule";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                schedules.add(getSchedule(rs));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return schedules;
    }

    @Override
    public void insertOrUpdate(int id, Object obj) {
        FlightSchedule fs = (FlightSchedule) obj;

        Debug.print("Scheduled Passed in to Insert Method: "+fs);
        String sql;

        try {
            if (id == -1) {
               sql = String.format("INSERT INTO flight_schedule VALUES(DEFAULT, '%d', '%s', '%s', '%s', '%s')"
                        , fs.getPlaneId(), fs.getDeparture_dateTimeString(), fs.getDeparture_city(),
                        fs.getArrival_dateTimeString(), fs.getArrival_city());
            } else {
                sql = String.format("UPDATE flight_schedule " +
                        "SET departure_date = '%s', departure_city = '%s', arrival_date = '%s', arrival_city = '%s'" +
                        "WHERE id_flight_schedule = %d",
                        fs.getDeparture_dateTimeString(), fs.getDeparture_city(),
                        fs.getArrival_dateTimeString(), fs.getArrival_city(), fs.getScheduleId());
            }
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e){
            Debug.print("Error inserting / updating Schedule into DB");
            e.printStackTrace();
        }
        Debug.print("Schedule added Successfully into DB");

    }

    public ObservableList<FlightSchedule> getSchedulesBySearch(String departureCity, String arrivalCity, LocalDate date){
        Connection con = Database.getConn();

        ObservableList<FlightSchedule> schedules = FXCollections.observableArrayList();

        try {
            Statement stm = con.createStatement();

            String sql = "SELECT \n" +
                    "    *\n" +
                    "FROM\n" +
                    "    flight_schedule\n" +
                    "WHERE\n" +
                    "    departure_city = '"+departureCity+"'\n" +
                    "        AND arrival_city = '"+arrivalCity+"'\n" +
                    "        AND departure_date REGEXP '^"+date+"'";

            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){
                // create a new flightSchedule obj and add it to the list
                schedules.add(getSchedule(rs)
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schedules;
    }

    private FlightSchedule getSchedule(ResultSet rs){
        FlightSchedule schedule = new FlightSchedule();

        try {
            schedule.setScheduleId(rs.getInt("id_flight_schedule"));
            schedule.setPlaneId(rs.getInt("id_plane"));
            schedule.setDeparture_dateTimeString(rs.getString("departure_date"));
            schedule.setDeparture_city(rs.getString("departure_city"));
            schedule.setArrival_dateTimeString(rs.getString("arrival_date"));
            schedule.setArrival_city(rs.getString("arrival_city"));
            schedule.setDepartureDate(rs.getDate("departure_date"));
            schedule.setArrivalDate(rs.getDate("arrival_date"));
        } catch (SQLException e){
            e.printStackTrace();
        }
        return schedule;
    }
}
