package FlyingDogAirlines.DataAccessLayer;

import FlyingDogAirlines.ApplicationLayer.DataType.Passenger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBPassenger implements DBInterface {
    //gets connection from database class
    private static Connection conn = Database.getConn();
    private static Statement stmt = null;

    @Override
    public ArrayList readAll() {
        ArrayList<Passenger> passengers = new ArrayList<>();
        passengers.clear();
        try {
            String sql = "SELECT * FROM passenger";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                Passenger passenger = new Passenger();
                passenger.setId(rs.getInt("id_passenger"));
                passenger.setFirstName(rs.getString("first_name"));
                passenger.setLastName(rs.getString("last_name"));
                passenger.setUsername(rs.getString("username"));
                passenger.setPassword(rs.getString("password"));

                passengers.add(passenger);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return passengers;
    }


    @Override
    public void insertOrUpdate(int id, Object obj) {
       Passenger passenger = (Passenger) obj;

        Connection conn = Database.getConn();
        try {
            stmt = conn.createStatement();

            String sql = "insert into passenger values\n" +
                    "(default,\""+passenger.getFirstName()+"\",\""+passenger.getLastName()+
                    "\",\""+passenger.getUsername()+"\",\""+passenger.getPassword()+"\")";

            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
