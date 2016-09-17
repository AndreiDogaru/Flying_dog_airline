package FlyingDogAirlines.DataAccessLayer;

import FlyingDogAirlines.ApplicationLayer.DataType.Debug;
import FlyingDogAirlines.ApplicationLayer.DataType.Seat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBSeat implements DBInterface {
    //gets connection from database class
    private static Connection conn = Database.getConn();
    private static Statement stmt = null;

    @Override
    public ArrayList readAll() {
        ArrayList<Seat> seats = new ArrayList<>();
        seats.clear();
        try {
            String sql = "SELECT * FROM seat";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                Seat seat = new Seat();
                seat.setSeatId(rs.getInt("id_seat"));
                seat.setScheduleId(rs.getInt("id_flight_schedule"));
                seat.setPassengerId(rs.getInt("id_passenger"));
                seat.setStatus(rs.getString("status"));
                seat.setPrice(rs.getDouble("price"));
                seat.setCabin(rs.getString("cabin"));
                seats.add(seat);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return seats;
    }

    public void update(int id_seat, int id_passenger, String status) {

        Connection conn = Database.getConn();

        try {
            stmt = conn.createStatement();

            String sql = "SET foreign_key_checks = 0";

            stmt.executeUpdate(sql);

            sql = "UPDATE seat \n" +
                    "SET \n" +
                    "    id_passenger = '"+id_passenger+"'\n" +
                    "WHERE\n" +
                    "    id_seat = "+id_seat+";";

            stmt.executeUpdate(sql);

            sql = "UPDATE seat \n" +
                    "SET \n" +
                    "    status = '"+status+"'\n" +
                    "WHERE\n" +
                    "    id_seat = "+id_seat+";";

            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void insertOrUpdate(int id, Object obj) {
        Seat s = (Seat) obj;

        String sql = "";

        try {
            if (id == -1) {
                sql = String.format("INSERT INTO seat (id_seat, id_flight_schedule, status, price, cabin) " +
                        "VALUES(DEFAULT, '%d', '%s', '%f', '%s')", s.getScheduleId(), s.getStatus(), s.getPrice(),
                        s.getCabin());

            }
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e){
            Debug.print("Error inserting / updating Seat into DB");
            e.printStackTrace();
        }
        Debug.print("Seat added Successfully into DB");

    }
}
