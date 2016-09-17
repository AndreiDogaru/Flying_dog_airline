package FlyingDogAirlines.ApplicationLayer.Controller;

import FlyingDogAirlines.ApplicationLayer.DataType.FlightSchedule;
import FlyingDogAirlines.ApplicationLayer.DataType.Plane;
import FlyingDogAirlines.ApplicationLayer.DataType.Seat;
import FlyingDogAirlines.DataAccessLayer.DBFlightSchedule;
import FlyingDogAirlines.DataAccessLayer.DBSeat;
import FlyingDogAirlines.FlyingDogAirlines;
import FlyingDogAirlines.PresentationLayer.EditPlaneView;
import FlyingDogAirlines.PresentationLayer.FlightScheduleView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;


public class FlightScheduleViewController {

    private Plane selectedPlane;

    public void setSelectedPlane(Plane selectedPlane) {
        this.selectedPlane = selectedPlane;
    }

    public ObservableList getFlightScheduleList(){
        // Clear the List of any items
        FlyingDogAirlines.schedules.clear();
        // Fill the Lists again
        // We may need to alter this later
        FlyingDogAirlines.fillArrayLists();

        ObservableList<FlightSchedule> selectedSchedules = FXCollections.observableArrayList();

        for (FlightSchedule fs: FlyingDogAirlines.schedules) {
            if (fs.getPlaneId() == selectedPlane.getId_plane())
                selectedSchedules.add(fs);
        }
        return selectedSchedules;
    }

    // Saves the details for a new and edited flight schedule
    public void saveData(FlightSchedule fs){
        if (fs == null){
            fs = new FlightSchedule();
            fs.setScheduleId(-1);
        }

        // Validate Fields
        if (FlightScheduleView.departureDatePicker == null || FlightScheduleView.arrivalDatePicker == null ||
                FlightScheduleView.departureCityField.equals("") || FlightScheduleView.arrivalCityField.equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please fill out all of the fields !");
            alert.showAndWait();

        } else {
            // Fields are !null so proceed to saving
            fs.setPlaneId(selectedPlane.getId_plane());
            fs.setDeparture_city(FlightScheduleView.departureCityField.getText());
            fs.setArrival_city(FlightScheduleView.arrivalCityField.getText());

            String departureTime = FlightScheduleView.departureTimeBox.getSelectionModel().getSelectedItem();
            String departureDate = FlightScheduleView.departureDatePicker.getValue().toString();
            fs.setDeparture_dateTimeString(departureDate + " " + departureTime);

            String arrivalTime = FlightScheduleView.arrivalTimeBox.getSelectionModel().getSelectedItem();
            String arrivalDate = FlightScheduleView.arrivalDatePicker.getValue().toString();
            fs.setArrival_dateTimeString(arrivalDate + " " + arrivalTime);

            // Add Flight Schedule to DB
            DBFlightSchedule dbFlightSchedule = new DBFlightSchedule();
            dbFlightSchedule.insertOrUpdate(fs.getScheduleId(), fs);
            FlyingDogAirlines.schedules.add(fs);
            // also add the newly made schedule to the tableView
            FlightScheduleView.scheduleTableView.getItems().add(fs);

            // Create all seats, set price etc.
            createAllSeats();
        }

    }

    // Shows the details for a selected Flight Schedule
    public void showData(FlightSchedule fs){

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");

        String departDateString = fs.departure_dateTimeStringProperty().getValue();
        String arrivalDateString = fs.arrival_dateTimeStringProperty().getValue();

        try {

            java.util.Date dateOfDeparture = formatter.parse(departDateString);
            java.util.Date dateOfArrival = formatter.parse(arrivalDateString);

            LocalDate departDate = fs.getDepartureDate().toLocalDate();
            LocalDate arriveDate = fs.getArrivalDate().toLocalDate();

            FlightScheduleView.departureDatePicker.setValue(departDate);
            FlightScheduleView.arrivalDatePicker.setValue(arriveDate);

            FlightScheduleView.departureTimeBox.getSelectionModel().select(timeFormatter.format(dateOfDeparture));
            FlightScheduleView.arrivalTimeBox.getSelectionModel().select(timeFormatter.format(dateOfArrival));

            FlightScheduleView.departureCityField.setText(fs.departure_cityProperty().getValue());
            FlightScheduleView.arrivalCityField.setText(fs.arrival_cityProperty().getValue());

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void createAllSeats(){
        Seat[] seatList = new Seat[selectedPlane.getMax_capacity()];
        double firstClassPrice = Double.parseDouble(FlightScheduleView.firstClassPriceField.getText());
        double businessClassPrice = firstClassPrice * .85;
        double economyClassPrice = firstClassPrice * .70;

        int selectedScheduleId = FlyingDogAirlines.schedules.size();

        for (int i = 0; i < selectedPlane.getFirst_class() ; i++) {
            seatList[i] = new Seat(-1, selectedScheduleId,
                    -1, Seat.seatStatus.AVAILABLE, Seat.cabin.FIRST_CLASS,
                    Double.parseDouble(FlightScheduleView.firstClassPriceField.getText()));
        }

        for (int i = selectedPlane.getFirst_class(); i < selectedPlane.getFirst_class()+selectedPlane.getBusiness_class() ; i++) {
            seatList[i] = new Seat(-1, selectedScheduleId,
                    -1, Seat.seatStatus.AVAILABLE, Seat.cabin.BUSINESS_CLASS, businessClassPrice);
        }

        for (int i = selectedPlane.getFirst_class()+selectedPlane.getBusiness_class();
             i < selectedPlane.getEconomy_class()+selectedPlane.getFirst_class()+selectedPlane.getBusiness_class(); i++) {
            seatList[i] = new Seat(-1, selectedScheduleId,
                    -1, Seat.seatStatus.AVAILABLE, Seat.cabin.ECONOMY_CLASS, economyClassPrice);
        }

        saveSeatsToDB(seatList);
    }

    private void saveSeatsToDB(Seat[] seatList){
        DBSeat dbSeat = new DBSeat();
        for (Seat s: seatList) {
            if (s != null) {
                dbSeat.insertOrUpdate(-1, s);
                FlyingDogAirlines.seats.add(s);
            }
        }
    }

    public void changeToEditPlaneView(){
        EditPlaneView editPlaneView = new EditPlaneView();
        FlyingDogAirlines.window.setScene(editPlaneView.setPlaneOverviewScene());
        FlyingDogAirlines.makeSceneMovable();
    }
}
