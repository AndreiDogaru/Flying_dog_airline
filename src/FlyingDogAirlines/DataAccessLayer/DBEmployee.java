package FlyingDogAirlines.DataAccessLayer;

import FlyingDogAirlines.ApplicationLayer.DataType.Employee;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBEmployee implements DBInterface{
    //gets connection from database class
    private static Connection conn = Database.getConn();
    private static Statement stmt = null;

    @Override
    public ArrayList readAll() {
        ArrayList<Employee> employees = new ArrayList<>();
        employees.clear();
        try {
            String sql = "SELECT * FROM employee";

            stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                Employee employee = new Employee();
                employee.setId(rs.getInt("id_employee"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setUsername(rs.getString("username"));
                employee.setPassword(rs.getString("password"));

                employees.add(employee);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return employees;
    }



    @Override
    public void insertOrUpdate(int id, Object employee) {
        Employee e = (Employee) employee;

        Connection conn = Database.getConn();
        try {
            stmt = conn.createStatement();

            String sql = "insert into employee values\n" +
                    "(default,\""+e.getFirstName()+"\",\""+e.getLastName()+
                    "\",\""+e.getUsername()+"\",\""+e.getPassword()+"\")";

            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
