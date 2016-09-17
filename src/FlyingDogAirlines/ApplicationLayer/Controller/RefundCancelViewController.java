package FlyingDogAirlines.ApplicationLayer.Controller;

import FlyingDogAirlines.ApplicationLayer.DataType.FlightSchedule;
import FlyingDogAirlines.ApplicationLayer.DataType.Seat;
import FlyingDogAirlines.DataAccessLayer.DBSeat;
import FlyingDogAirlines.FlyingDogAirlines;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class RefundCancelViewController {

    public  static FlightSchedule fs;

    public ObservableList getSeatList() {

        ObservableList <Seat> seatList = FXCollections.observableArrayList();

        for (Seat s : FlyingDogAirlines.seats){
            if(fs.getScheduleId() == s.getScheduleId()){
                seatList.add(s);
            }
        }
        return seatList;
    }

    public void refundSelectedTicket(Seat s) {
        if (s.getStatus().contains("confirmed")){
            if (s.getCabin().contains("economy") && !validateRefund()){
                // Refund is not processed because it is < 14 days til departure
                // For the economy Ticket
                System.out.println("Thanks for your money");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Refunds are only valid when Ticket is " +
                        "cancelled 14 Days prior to Departure");
                alert.showAndWait();
            } else {
                s.setStatus("available");
                s.setPassengerId(0);

                // Refund is successful alert box
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Refund is Successful");
                alert.showAndWait();
            }
        }

        // Updates Seat in the Seat DB Table
        DBSeat dbSeat = new DBSeat();
        dbSeat.update(s.getSeatId(), 0, "available");

        // Update Seat in Global Seat Array List
        for(int i=0;i<FlyingDogAirlines.seats.size();i++){
            if(FlyingDogAirlines.seats.get(i).getSeatId() == s.getSeatId()){
                FlyingDogAirlines.seats.get(i).setPassengerId(0);
                FlyingDogAirlines.seats.get(i).setStatus("available");
            }

        }
    }

    private boolean validateRefund() {
        boolean flag = true;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String departDateString = fs.departure_dateTimeStringProperty().getValue();

        try {
            Date dateOfDeparture = dateFormat.parse(departDateString);
            Date now = Date.from(Instant.now());

            if (getDateDiff(now, dateOfDeparture,  TimeUnit.DAYS) <= 14) {
                flag = false;
            }

            System.out.println("computeDiff() Result " +
                    computeDiff(now, dateOfDeparture));
            System.out.println("getDateDiff() Result" +
                    getDateDiff(now, dateOfDeparture,  TimeUnit.DAYS));

        } catch (ParseException e){
            e.printStackTrace();
        }

        return flag;
    }

    private static Map<TimeUnit,Long> computeDiff(Date date1, Date date2) {
        // Gets the Time in Milliseconds between the two dates
        long diffInMillies = date2.getTime() - date1.getTime();

        // Creates a List of Time Units (Days, Hours, Minutes, Seconds,
        // Milliseconds, MicroSeconds, NanoSeconds)
        List<TimeUnit> units = new ArrayList<>(EnumSet.allOf(TimeUnit.class));

        // Arranges Unit List from Days --> Nano seconds
        Collections.reverse(units);

        // Creates a Map for the Time Unit and a value of unit which is an INT
        Map<TimeUnit,Long> result = new LinkedHashMap<>();
        long milliesRest = diffInMillies;

        // Get the Value for each unit in the Unit List by
        // calculating the INT value for the appropriate unit
        for ( TimeUnit unit : units ) {
            long diff = unit.convert(milliesRest, TimeUnit.MILLISECONDS);
            long diffInMilliesForUnit = unit.toMillis(diff);
            milliesRest = milliesRest - diffInMilliesForUnit;

            // Adds the UNIT (DAY, HOUR, MIN etc) and its INT value to the Map
            result.put(unit, diff);
        }
        return result;
    }

    // Simplified Method from the computeDiff method
    private  static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }
}
