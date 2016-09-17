package FlyingDogAirlines.ApplicationLayer.Controller;

import FlyingDogAirlines.ApplicationLayer.DataType.FlightSchedule;
import FlyingDogAirlines.DataAccessLayer.DBFlightSchedule;
import FlyingDogAirlines.DataAccessLayer.Database;
import FlyingDogAirlines.FlyingDogAirlines;
import FlyingDogAirlines.PresentationLayer.BookingView;
import FlyingDogAirlines.PresentationLayer.CheckOutView;
import FlyingDogAirlines.PresentationLayer.RefundCancelView;
import FlyingDogAirlines.PresentationLayer.SearchView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookingViewController {

    private SearchView searchView = new SearchView();
    private CheckOutView checkOutView = new CheckOutView();

    // the selected schedule from the tableView
    public static FlightSchedule selectedSchedule = new FlightSchedule();

    // counters for the number of tickets each class has left for the selected flight
    public static int firstClass = 0 , businessClass = 0, economyClass = 0;

    // goes to previous scene
    public void changeToSearchScene(){
        // go back to the search scene
        FlyingDogAirlines.window.setScene(searchView.setSearchScene());
        FlyingDogAirlines.makeSceneMovable();
    }

    public static void setLabelText(){
        // set text of the labels using our ArrayList
        for(int i=0;i<FlyingDogAirlines.planes.size();i++){
            if(FlyingDogAirlines.planes.get(i).getId_plane() == BookingView.scheduleTableView.
                    getSelectionModel().getSelectedItem().getPlaneId()){
                BookingView.brandLabel.setText("     Brand: "+FlyingDogAirlines.planes.get(i).getBrand());
                BookingView.modelLabel.setText("     Model: "+FlyingDogAirlines.planes.get(i).getModel());
            }
        }
    }

    // fills the tableView with schedules that match the criteria from the previous scene
    public ObservableList<FlightSchedule> populateSchedulesList(){

       DBFlightSchedule dbFlightSchedule = new DBFlightSchedule();

        return dbFlightSchedule.getSchedulesBySearch(SearchView.departureCity.getSelectionModel().getSelectedItem(),
                SearchView.arrivalCity.getSelectionModel().getSelectedItem(),
                SearchView.departureDate.getValue());

    }

    // goes to next scene
    public void changeToCheckOutView(){
        // check if there is a flight schedule selected before changing the scene
        if(BookingView.scheduleTableView.getSelectionModel().isEmpty()){
            // if you didn't select any schedule
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please select a flight schedule !");
            alert.showAndWait();
        }else {
            // save the data of the selected schedule in an object for further use
            selectedSchedule = BookingView.scheduleTableView.getSelectionModel().getSelectedItem();

            // change the scene to checkOutScene
            FlyingDogAirlines.window.setScene(checkOutView.setCheckOutScene());
            FlyingDogAirlines.makeSceneMovable();
        }
    }

    // sets the number of remaining tickets for each class in 3 different labels
    public static void setRemainingTickets(){
        // make the labels look nice again
        if(firstClass > 99)
            BookingView.firstTickets.setText(BookingView.firstTickets.getText().
                    substring(0,BookingView.firstTickets.getText().length()-3) + "0");
        else if(firstClass > 9)
            BookingView.firstTickets.setText(BookingView.firstTickets.getText().
                    substring(0,BookingView.firstTickets.getText().length()-2) + "0");
        else
            BookingView.firstTickets.setText(BookingView.firstTickets.getText().
                    substring(0,BookingView.firstTickets.getText().length()-1) + "0");

        if(businessClass > 99)
            BookingView.businessTickets.setText(BookingView.businessTickets.getText().
                    substring(0,BookingView.businessTickets.getText().length()-3)+"0");
        else if(businessClass > 9)
            BookingView.businessTickets.setText(BookingView.businessTickets.getText().
                    substring(0,BookingView.businessTickets.getText().length()-2)+"0");
        else
            BookingView.businessTickets.setText(BookingView.businessTickets.getText().
                    substring(0,BookingView.businessTickets.getText().length()-1)+"0");

        if(economyClass > 99)
            BookingView.economyTickets.setText(BookingView.economyTickets.getText().
                    substring(0,BookingView.economyTickets.getText().length()-3)+"0");
        else if(economyClass > 9)
            BookingView.economyTickets.setText(BookingView.economyTickets.getText().
                    substring(0,BookingView.economyTickets.getText().length()-2)+"0");
        else
            BookingView.economyTickets.setText(BookingView.economyTickets.getText().
                    substring(0,BookingView.economyTickets.getText().length()-1)+"0");

        //  before setting the remaining number of tickets for each cabinClass,
        // we need to make sure the number is 0
        firstClass = 0;
        businessClass = 0;
        economyClass = 0;
        

        Connection con = Database.getConn();

        try {
            Statement stm = con.createStatement();

            String sql ="SELECT \n" +
                    "    cabin, COUNT(cabin)\n" +
                    "FROM\n" +
                    "    seat\n" +
                    "WHERE\n" +
                    "    id_flight_schedule = "+BookingView.scheduleTableView.getSelectionModel().getSelectedItem().getScheduleId()
                    +" AND status = 'available'\n" +
                    "GROUP BY cabin;";

            ResultSet rs = stm.executeQuery(sql);
            while(rs.next()){
                if(rs.getString("cabin").equals("first")) {
                    firstClass = rs.getInt("COUNT(cabin)");
                    BookingView.firstTickets.setText(BookingView.firstTickets.getText().
                            substring(0,BookingView.firstTickets.getText().length()-1) + firstClass);
                }else if(rs.getString("cabin").equals("business")) {
                    businessClass = rs.getInt("COUNT(cabin)");
                    BookingView.businessTickets.setText(BookingView.businessTickets.getText().
                            substring(0,BookingView.businessTickets.getText().length()-1) + businessClass);
                }else{
                    economyClass = rs.getInt("COUNT(cabin)");
                    BookingView.economyTickets.setText(BookingView.economyTickets.getText().
                            substring(0,BookingView.economyTickets.getText().length()-1) + economyClass);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // disable any radio buttons for a cabinClass with 0 seats available
        checkRadioButtons();
    }

    // if we have 0 remaining tickets for a class, then the respective radio button must be disabled
    private static void checkRadioButtons(){
        if(firstClass == 0)
            BookingView.firstClass.setDisable(true);
        else
            BookingView.firstClass.setDisable(false);

        if(businessClass == 0)
            BookingView.businessClass.setDisable(true);
        else
            BookingView.businessClass.setDisable(false);

        if(economyClass == 0)
            BookingView.economyClass.setDisable(true);
        else
            BookingView.economyClass.setDisable(false);
    }

    // disable the "book now" button if there are no seats available
    public static void checkSeatsAvailability(){
        if(BookingView.firstClass.isDisable() && BookingView.businessClass.isDisable() &&
                BookingView.economyClass.isDisable())
            BookingView.bookNow.setDisable(true);
    }


    public void changeToSeatScene() {
        //Passing Flightschedule from booking view to refundview
        RefundCancelView.showRefundPopUp();
        RefundCancelViewController.fs = selectedSchedule;
    }

}
