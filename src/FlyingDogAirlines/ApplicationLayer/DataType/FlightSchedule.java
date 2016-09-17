package FlyingDogAirlines.ApplicationLayer.DataType;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.sql.Date;

public class FlightSchedule {
    // Attributes
    private SimpleIntegerProperty scheduleId;
    private SimpleIntegerProperty planeId;
    private SimpleStringProperty departure_dateTimeString;
    private SimpleStringProperty departure_city;
    private SimpleStringProperty arrival_dateTimeString;
    private SimpleStringProperty arrival_city;

    private Date departureDate;
    private Date arrivalDate;

    // Null Constructor
    public FlightSchedule(){}

    // Overloaded Constructor
    public FlightSchedule(int scheduleId, int planeId, String departure_dateTimeString, String departure_city,
                          String arrival_dateTimeString, String arrival_city){
        this.scheduleId = new SimpleIntegerProperty(scheduleId);
        this.planeId = new SimpleIntegerProperty(planeId);
        this.departure_dateTimeString = new SimpleStringProperty(departure_dateTimeString);
        this.departure_city = new SimpleStringProperty(departure_city);
        this.arrival_dateTimeString = new SimpleStringProperty(arrival_dateTimeString);
        this.arrival_city = new SimpleStringProperty(arrival_city);
    }

    // Setters

    public void setScheduleId(int scheduleId) {
        this.scheduleId = new SimpleIntegerProperty(scheduleId);
    }

    public void setPlaneId(int planeId) {
        this.planeId = new SimpleIntegerProperty(planeId);
    }

    public void setDeparture_dateTimeString(String departure_dateTimeString) {
        this.departure_dateTimeString = new SimpleStringProperty(departure_dateTimeString);
    }

    public void setDeparture_city(String departure_city) {
        this.departure_city = new SimpleStringProperty(departure_city);
    }

    public void setArrival_dateTimeString(String arrival_dateTimeString) {
        this.arrival_dateTimeString = new SimpleStringProperty(arrival_dateTimeString);
    }

    public void setArrival_city(String arrival_city) {
        this.arrival_city = new SimpleStringProperty(arrival_city);
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    // Getters

    public int getPlaneId() {
        return planeId.get();
    }

    public SimpleIntegerProperty planeIdProperty() {
        return planeId;
    }

    public int getScheduleId() {
        return scheduleId.get();
    }

    public SimpleIntegerProperty scheduleIdProperty() {
        return scheduleId;
    }

    public String getDeparture_dateTimeString() {
        return departure_dateTimeString.get();
    }

    public SimpleStringProperty departure_dateTimeStringProperty() {
        return departure_dateTimeString;
    }

    public String getArrival_dateTimeString() {
        return arrival_dateTimeString.get();
    }

    public SimpleStringProperty arrival_dateTimeStringProperty() {
        return arrival_dateTimeString;
    }

    public String getDeparture_city() {
        return departure_city.get();
    }

    public SimpleStringProperty departure_cityProperty() {
        return departure_city;
    }

    public String getArrival_city() {
        return arrival_city.get();
    }

    public SimpleStringProperty arrival_cityProperty() {
        return arrival_city;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    @Override
    public String toString(){
        return String.format("Schedule ID %s, Plane ID %s, Departure dateString %s, Departure city %s, " +
                "Arrival dateString %s, Arrival city %s , \n",scheduleIdProperty().getValue(), planeIdProperty().getValue(),
                departure_dateTimeStringProperty().getValue(), departure_cityProperty().getValue(),
                arrival_dateTimeStringProperty().getValue(), arrival_cityProperty().getValue());
    }
}
