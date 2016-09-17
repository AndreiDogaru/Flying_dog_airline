package FlyingDogAirlines.DataAccessLayer;

import FlyingDogAirlines.ApplicationLayer.DataType.Debug;
import FlyingDogAirlines.ApplicationLayer.DataType.Plane;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBPlane implements DBInterface{
    //gets connection from database class
    private static Connection conn = Database.getConn();
    private static Statement stmt = null;

    @Override
    public ArrayList<Plane> readAll() {
        ArrayList<Plane> planes = new ArrayList<>();
        planes.clear();
        try {
            String sql = "SELECT * FROM plane";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                Plane plane = new Plane();
                plane.setId_plane(rs.getInt("id_plane"));
                plane.setBrand(rs.getString("brand"));
                plane.setModel(rs.getString("model"));
                plane.setFirst_class(rs.getInt("first_class"));
                plane.setBusiness_class(rs.getInt("business_class"));
                plane.setEconomy_class(rs.getInt("economy_class"));
                plane.setMax_capacity(rs.getInt("max_capacity"));
                planes.add(plane);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return planes;
    }


    @Override
    public void insertOrUpdate(int id, Object obj) {

        Plane p = (Plane) obj;
        Debug.print("Plane Passed in to Insert Method: "+p);
        String sql;

        try {
            if (id == -1) {
                sql = String.format("INSERT INTO plane VALUES(DEFAULT, '%s', '%s', '%d', '%d', '%d', '%d')"
                        , p.getBrand(), p.getModel(), p.getFirst_class(), p.getBusiness_class(), p.getEconomy_class(),
                        p.getMax_capacity());
            } else {
                sql = String.format("UPDATE plane " +
                                "SET brand = '%s', model = '%s', first_class = '%d', business_class = '%d', max_capacity = '%d'" +
                                "WHERE id_plane = %d",
                       p.getId_plane(), p.getBrand(), p.getModel(), p.getFirst_class(), p.getBusiness_class(), p.getEconomy_class(),
                        p.getMax_capacity());
            }
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e){
            Debug.print("Error inserting / updating Plane into DB");
            e.printStackTrace();
        }
        Debug.print("Plane added Successfully into DB");
    }
}
