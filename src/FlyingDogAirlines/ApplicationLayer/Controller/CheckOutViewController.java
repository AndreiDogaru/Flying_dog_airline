package FlyingDogAirlines.ApplicationLayer.Controller;

import FlyingDogAirlines.ApplicationLayer.DataType.Passenger;
import FlyingDogAirlines.DataAccessLayer.DBPassenger;
import FlyingDogAirlines.DataAccessLayer.DBSeat;
import FlyingDogAirlines.DataAccessLayer.Database;
import FlyingDogAirlines.FlyingDogAirlines;
import FlyingDogAirlines.PresentationLayer.BookingView;
import FlyingDogAirlines.PresentationLayer.CheckOutView;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckOutViewController {

    BookingView bookingView = new BookingView();

    static String passengerFirstName = "", passengerLastName = "";
    static int passengerId = 0, seat_id = 0;

    // boolean that checks if there is any passenger logged in before making the payment
    static boolean is_passenger_loggedIn = false;
    // boolean that checks if there is any seat available
    static boolean is_seat_available = false;

    // goes back to previous scene
    public void changeToBookingScene() {

       FlyingDogAirlines.window.setScene(bookingView.setBookingScene());
       FlyingDogAirlines.makeSceneMovable();
    }

    // sets the left side of the scene back to its original form
    public static void originalLeftVBox(){
        // first clear all children from the leftVBox
        CheckOutView.leftVBox.getChildren().remove(0,CheckOutView.leftVBox.getChildren().size());

        // then make new HBoxes with the content we need and add them to the leftVBox
        HBox createAccountHBox = new HBox(15,CheckOutView.existingUserLabel,CheckOutView.logInButton);
        HBox logInHBox = new HBox(15,CheckOutView.newUserLabel,CheckOutView.createAccountButton);

        CheckOutView.leftVBox.getChildren().addAll(createAccountHBox,logInHBox);

        // finally we set the boolean back to false
        is_passenger_loggedIn = false;
    }

    // sets the left side of the scene to a Log In screen
    public static void logInScreen(){
        // first clear all children from the leftVBox
        CheckOutView.leftVBox.getChildren().remove(0,CheckOutView.leftVBox.getChildren().size());

        // refactor the size of the labels for good looking
        CheckOutView.userLabel.setText("Username");
        CheckOutView.passLabel.setText("Password ");

        // then make new HBoxes with the content we need and add them to the leftVBox
        HBox userHBox = new HBox(15,CheckOutView.userLabel,CheckOutView.userTField);
        HBox passHBox = new HBox(15,CheckOutView.passLabel,CheckOutView.passField);
        Region reg = new Region();
        reg.setPrefWidth(80);
        HBox buttonHBox = new HBox(reg,CheckOutView.actualLogIn);
        Region reg2 = new Region();
        reg2.setPrefHeight(250);

        CheckOutView.leftVBox.getChildren().addAll(userHBox,passHBox,buttonHBox,reg2,CheckOutView.backToOriginalScene);
    }

    // sets the left side of the scene to a Create an Account screen
    public static void createAccountScreen(){
        // first clear all children from the leftVBox
        CheckOutView.leftVBox.getChildren().remove(0,CheckOutView.leftVBox.getChildren().size());

        // refactor the size of the labels for good looking
        CheckOutView.userLabel.setText("Username             ");
        CheckOutView.passLabel.setText("Password              ");

        // then make new HBoxes with the content we need and add them to the leftVBox
        HBox fNameHBox = new HBox(15,CheckOutView.fNameLabel,CheckOutView.fNameTField);
        HBox lNameHBox = new HBox(15,CheckOutView.lNameLabel,CheckOutView.lNameTField);
        HBox userHBox = new HBox(15,CheckOutView.userLabel,CheckOutView.userTField);
        HBox passHBox = new HBox(15,CheckOutView.passLabel,CheckOutView.passField);
        HBox confPassHBox = new HBox(15,CheckOutView.confPassLabel,CheckOutView.confPassField);
        Region reg = new Region();
        reg.setPrefWidth(135);
        HBox buttonHBox = new HBox(reg,CheckOutView.actualCreate);
        Region reg2 = new Region();
        reg2.setPrefHeight(150);

        CheckOutView.leftVBox.getChildren().addAll(fNameHBox,lNameHBox,userHBox,passHBox,
                confPassHBox,buttonHBox,reg2,CheckOutView.backToOriginalScene);
    }

    // sets the left side of the scene to a Passenger Logged In screen
    private static void passengerLoggedIn(){
        // first clear all children from the leftVBox
        CheckOutView.leftVBox.getChildren().remove(0,CheckOutView.leftVBox.getChildren().size());

        Label fNLabel = new Label(passengerFirstName);
        Label lNLabel = new Label(passengerLastName);

        // then make a new HBox with the content we need and add it to the leftVBox
        Region reg1 = new Region();
        reg1.setPrefHeight(100);
        HBox hBox = new HBox(10,CheckOutView.passengerLabel,fNLabel,lNLabel);
        Region reg2 = new Region();
        reg2.setPrefHeight(200);
        CheckOutView.leftVBox.getChildren().addAll(reg1,hBox,reg2,CheckOutView.backToOriginalScene);

        // set the boolean true so the payment can be made
        is_passenger_loggedIn = true;
    }

    // after pressing the second log in button -> check if the user actually exist in our database
    public static void checkLogIn(){

        Connection con = Database.getConn();

        try {
            Statement stm = con.createStatement();

            stm.setMaxRows(1);

            String sql = "SELECT \n" +
                    "    *\n" +
                    "FROM\n" +
                    "    passenger\n" +
                    "WHERE\n" +
                    "    username = '"+CheckOutView.userTField.getText()+"' " +
                    "AND password = '"+CheckOutView.passField.getText()+"'";

            ResultSet rs  = stm.executeQuery(sql);

            if(rs.next()){
                // store the passenger's id
                passengerId = rs.getInt("id_passenger");
                // store the passenger's first and last name in 2 strings
                passengerFirstName = rs.getString("first_name");
                passengerLastName = rs.getString("last_name");

                // show a dialog that the log in was successful
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Log In successful !");
                alert.showAndWait();
                // change the left side of the scene so it shows something else
                passengerLoggedIn();
            }else{
                // show a dialog that the log in failed
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Log In failed !");
                alert.showAndWait();
            }
            // and empty the fields
            CheckOutView.userTField.setText("");
            CheckOutView.passField.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // add the newly created passenger to the database and arrayList
    private static void createNewPassenger(){
        // create a passenger using the data from the labels
        Passenger passenger = new Passenger();
        passenger.setId(FlyingDogAirlines.passengers.size()+1);
        passenger.setFirstName(CheckOutView.fNameTField.getText());
        passenger.setLastName(CheckOutView.lNameTField.getText());
        passenger.setUsername(CheckOutView.userTField.getText());
        passenger.setPassword(CheckOutView.passField.getText());

        // add the passenger first to the database and then to our ArrayList
        DBPassenger dbPassenger = new DBPassenger();
        dbPassenger.insertOrUpdate(-1, passenger);
        FlyingDogAirlines.passengers.add(passenger);

        // after creating a new passenger, the left side of the scene will show its original form again
        originalLeftVBox();
    }

    // before creating a new passenger, we must see if the user entered all data correct
    public static void validateNewPassenger(){
        if(CheckOutView.fNameTField.getText().equals("") || CheckOutView.lNameTField.getText().equals("") ||
                CheckOutView.userTField.getText().equals("") || CheckOutView.passField.getText().equals("") ||
                CheckOutView.confPassField.getText().equals("")){
            // if any of the fields is left empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please fill all fields !");
            alert.showAndWait();
        }else if(!CheckOutView.passField.getText().equals(CheckOutView.confPassField.getText())){
            // if confirmPassword if different than password
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please make sure you entered the password correctly !");
            alert.showAndWait();

            // empty the password fields
            CheckOutView.passField.setText("");
            CheckOutView.confPassField.setText("");
        }else {
            // show a dialog that the passenger was created successfully
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Passenger created successfully !");
            alert.showAndWait();

            // everything is fine, ready to create the new passenger
            createNewPassenger();

            // empty all fields
            CheckOutView.fNameTField.setText("");
            CheckOutView.lNameTField.setText("");
            CheckOutView.userTField.setText("");
            CheckOutView.passField.setText("");
            CheckOutView.confPassField.setText("");
        }
    }

    //  finalize the payment
    // -> show a message if there is an error
    // -> show a message and update the seat table if the payment is done
    public static void payNow(){
        getSeatNumber();
        if(!is_passenger_loggedIn){
            // show a message that no passenger was selected
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please select a passenger first before finalizing the payment !");
            alert.showAndWait();
        }else{
            if(seat_id == 0){
                // there is a problem in the payment
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("No more seats available !");
                alert.showAndWait();
            }else {
                // show a confirmation message
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Ticket is confirmed !");
                alert.setContentText("Seat number: " + seat_id);
                alert.show();

            }
        }

    }

    // returns the seat id from the ArrayList in order to show it in the confirmation message
    private static void getSeatNumber(){
        // loop through our arrayList of seats
        for (int i = 0; i < FlyingDogAirlines.seats.size(); i++) {
            // check if there is any seat available for the specific cabinClass FOR THE SELECTED SCHEDULE
            if (FlyingDogAirlines.seats.get(i).getStatus().equals("available") &&
                    FlyingDogAirlines.seats.get(i).getCabin().equals(getCabinClass()) &&
                    FlyingDogAirlines.seats.get(i).getScheduleId() ==
                    BookingView.scheduleTableView.getSelectionModel().getSelectedItem().getScheduleId()) {
                // we assign the specific seat number
                seat_id = FlyingDogAirlines.seats.get(i).getSeatId();

                //  when we find an available seat for the specific cabin we also need to update
                // the passenger id and the status of that seat for both arrayList and mysql table
                FlyingDogAirlines.seats.get(i).setStatus("confirmed");
                FlyingDogAirlines.seats.get(i).setPassengerId(passengerId);

                DBSeat dbSeat = new DBSeat();
                dbSeat.update(seat_id, passengerId, "confirmed");

                // make the boolean true if there is any available seat
                is_seat_available = true;

                // the break point is used so we only assign one seat
                break;
            }
        }

        // if we didn't find any available seat, make the seat_id = 0
        if(!is_seat_available)
            seat_id = 0;
        // reassign the boolean to false for further use
        is_seat_available = false;
    }

    // returns the cabinClass for the ticket we are validating right now
    private static String getCabinClass(){
        //  toString() returns a rather difficult string from which we only need the selected radioButton name
        // (that's why we use the substring() method inside the if statements)
        String cabin = BookingView.cabinToggleGroup.selectedToggleProperty().getValue().toString();

        if(cabin.substring(46,cabin.length()-1).contains("irst"))
            return "first";
        else if(cabin.substring(46,cabin.length()-1).contains("nomy"))
            return "economy";
        else
            return "business";
    }
}
