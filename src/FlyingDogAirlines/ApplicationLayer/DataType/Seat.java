package FlyingDogAirlines.ApplicationLayer.DataType;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Seat {
    // Attributes
    private SimpleIntegerProperty seatId;
    private SimpleIntegerProperty scheduleId;
    private SimpleIntegerProperty passengerId;
    private SimpleStringProperty  cabin;
    private SimpleStringProperty  status;
    private SimpleDoubleProperty price;

    // ENUM for Status and Cabin
    public enum seatStatus { AVAILABLE, CONFIRMED }
    public enum cabin {FIRST_CLASS, BUSINESS_CLASS, ECONOMY_CLASS}

    // Empty Constructor
    public Seat(){}

    // Overloaded Constructor
    public Seat(int seatId, int scheduleId, int passengerId, seatStatus status, cabin cabin, double price){
        this.seatId = new SimpleIntegerProperty(seatId);
        this.scheduleId = new SimpleIntegerProperty(scheduleId);
        this.passengerId = new SimpleIntegerProperty(passengerId);
        setSeatStatus(status);
        setCabin(cabin);
        this.price = new SimpleDoubleProperty(price);
    }


    // SETTERS

    private void setCabin(cabin cabin) {
        switch (cabin){
            case FIRST_CLASS:
                setCabin("first");
                break;
            case BUSINESS_CLASS:
                setCabin("business");
                break;
            case ECONOMY_CLASS:
                setCabin("economy");
        }
    }

    private void setSeatStatus(seatStatus status) {
        switch (status){
            case AVAILABLE:
                setStatus("available");
                break;
            case CONFIRMED:
                setStatus("confirmed");
                break;
        }
    }

    public void setSeatId(int seatId) {
        this.seatId = new SimpleIntegerProperty(seatId);
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = new SimpleIntegerProperty(scheduleId);
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = new SimpleIntegerProperty(passengerId);
    }

    public void setCabin(String cabin) {
        this.cabin = new SimpleStringProperty(cabin);
    }

    public void setStatus(String status) {
        this.status = new SimpleStringProperty(status);
    }

    public void setPrice(double price) {
        this.price = new SimpleDoubleProperty(price);
    }

    // GETTERS

    public int getSeatId() {
        return seatId.get();
    }

    public SimpleIntegerProperty seatIdProperty() {
        return seatId;
    }

    public int getScheduleId() {
        return scheduleId.get();
    }

    public SimpleIntegerProperty scheduleIdProperty() {
        return scheduleId;
    }

    public int getPassengerId() {
        return passengerId.get();
    }

    public SimpleIntegerProperty passengerIdProperty() {
        return passengerId;
    }

    public String getCabin() {
        return cabin.get();
    }

    public SimpleStringProperty cabinProperty() {
        return cabin;
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public double getPrice() {
        return price.get();
    }

    public SimpleDoubleProperty priceProperty() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("Seat ID %s, Schedule ID %S, Passenger ID %s, Cabin: %s, Status: %s, Price: %s \n",
                seatIdProperty().getValue(), scheduleIdProperty().getValue(), passengerIdProperty().getValue(),
                cabinProperty().getValue(), statusProperty().getValue(), priceProperty().getValue());
    }
}
