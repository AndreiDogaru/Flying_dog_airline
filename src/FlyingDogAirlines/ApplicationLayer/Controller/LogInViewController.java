package FlyingDogAirlines.ApplicationLayer.Controller;

import FlyingDogAirlines.ApplicationLayer.DataType.Employee;
import FlyingDogAirlines.DataAccessLayer.DBEmployee;
import FlyingDogAirlines.DataAccessLayer.Database;
import FlyingDogAirlines.FlyingDogAirlines;
import FlyingDogAirlines.PresentationLayer.LogInView;
import FlyingDogAirlines.PresentationLayer.SearchView;
import javafx.scene.control.Alert;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogInViewController {

    static SearchView searchView = new SearchView();
    static int userId = 0;

    public static void setLeftVBox(){
        // clear all children from the leftVBox
        LogInView.leftVBox.getChildren().remove(0,LogInView.leftVBox.getChildren().size());

        // make new HBoxes with the content we need and add them to the leftVBox
        HBox userHBox = new HBox(15,LogInView.logInUserLabel,LogInView.logInUserTF);
        HBox passHBox = new HBox(15,LogInView.logInPassLabel,LogInView.logInPassF);
        Region regButton = new Region();
        regButton.setPrefWidth(89);
        HBox buttonHBox = new HBox(regButton,LogInView.logInButton);

        LogInView.leftVBox.getChildren().addAll(LogInView.logoImageView,LogInView.alreadyAUser,
                userHBox,passHBox,buttonHBox);
    }

    public static void setRightVBox(){
        // clear all children from the leftVBox
        LogInView.rightVBox.getChildren().remove(0,LogInView.rightVBox.getChildren().size());

        // make new HBoxes with the content we need and add them to the leftVBox
        HBox fNameHBox = new HBox(15,LogInView.fNameLabel,LogInView.fNameTF);
        HBox lNameHBox = new HBox(15,LogInView.lNameLabel,LogInView.lNameTF);
        HBox userHBox = new HBox(15,LogInView.userLabel,LogInView.userTF);
        HBox passHBox = new HBox(15,LogInView.passLabel,LogInView.passF);
        HBox confPassHBox = new HBox(15,LogInView.confPassLabel,LogInView.confPassF);
        Region regButton = new Region();
        regButton.setPrefWidth(135);
        HBox buttonHBox = new HBox(regButton,LogInView.signUpButton);

        LogInView.rightVBox.getChildren().addAll(LogInView.haveAccount,LogInView.registerLabel,fNameHBox,lNameHBox,
                userHBox,passHBox, confPassHBox,buttonHBox);
    }

    // after pressing the log in button -> check if the user actually exist in our database
    public static void checkLogIn(){

        Connection con = Database.getConn();

        try {
            Statement stm = con.createStatement();

            String sql = "SELECT \n" +
                    "    *\n" +
                    "FROM\n" +
                    "    employee\n" +
                    "WHERE\n" +
                    "    username = '"+LogInView.logInUserTF.getText()+"' " +
                    "AND password = '"+LogInView.logInPassF.getText()+"'";

            ResultSet rs  = stm.executeQuery(sql);
            if(rs.next()){
                // store the user's id
                userId = rs.getInt("id_employee");

                // show a dialog that the log in was successful
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Log In successful !");
                alert.showAndWait();
                // go to searchScene
                FlyingDogAirlines.window.setScene(searchView.setSearchScene());
                FlyingDogAirlines.makeSceneMovable();
            }else{
                // show a dialog that the log in failed
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Log In failed !");
                alert.showAndWait();
            }
            // and empty the fields
            LogInView.logInUserTF.setText("");
            LogInView.logInPassF.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // before creating a new user, we must see if the user entered all data correct
    public static void validateNewUser(){
        if(LogInView.fNameTF.getText().equals("") || LogInView.lNameTF.getText().equals("") ||
                LogInView.userTF.getText().equals("") || LogInView.passF.getText().equals("") ||
                LogInView.confPassF.getText().equals("")){
            // if any of the fields is left empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please fill all fields !");
            alert.showAndWait();
        }else if(!LogInView.passF.getText().equals(LogInView.confPassF.getText())){
            // if confirmPassword if different than password
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please make sure you entered the password correctly !");
            alert.showAndWait();

            // empty the password fields
            LogInView.passF.setText("");
            LogInView.confPassF.setText("");
        }else {
            // show a dialog that the passenger was created successfully
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("User created successfully !");
            alert.showAndWait();

            // everything is fine, ready to create the new passenger
            createNewUser();

            // empty all fields
            LogInView.fNameTF.setText("");
            LogInView.lNameTF.setText("");
            LogInView.userTF.setText("");
            LogInView.passF.setText("");
            LogInView.confPassF.setText("");
        }
    }

    // add the newly created user/employee to the database and arrayList
    private static void createNewUser(){
        // create a passenger using the data from the labels
        Employee employee = new Employee();
        employee.setId(FlyingDogAirlines.employees.size()+1);
        employee.setFirstName(LogInView.fNameTF.getText());
        employee.setLastName(LogInView.lNameTF.getText());
        employee.setUsername(LogInView.userTF.getText());
        employee.setPassword(LogInView.confPassF.getText());

        // add the passenger first to the database and then to our ArrayList
        DBEmployee dbEmployee = new DBEmployee();
        dbEmployee.insertOrUpdate(-1, employee);
        FlyingDogAirlines.employees.add(employee);

    }
}
