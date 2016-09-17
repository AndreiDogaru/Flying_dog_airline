package FlyingDogAirlines;

import FlyingDogAirlines.ApplicationLayer.DataType.*;
import FlyingDogAirlines.DataAccessLayer.*;
import FlyingDogAirlines.PresentationLayer.LogInView;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class FlyingDogAirlines extends Application {

    public static Stage window;

    public static ArrayList<Plane> planes;
    public static ArrayList<Seat> seats;
    public static ArrayList<FlightSchedule> schedules;
    public static ArrayList<Passenger> passengers;
    public static ArrayList<Employee> employees;

    // variables that help us drag the scene around
    private static double xOffset = 0;
    private static double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;

        // connect to Database
        Database DB = new Database();
        DB.connectDatabase();

        // fill all ArrayLists with data from the Database
        fillArrayLists();

        // make an instantiation of the class SearchView
        LogInView logInView = new LogInView();

        window.setTitle("Flying Dog Airlines");
        window.initStyle(StageStyle.TRANSPARENT);
        window.setScene(logInView.setLogInScene());
        makeSceneMovable();
        window.show();
    }

    public static void fillArrayLists(){

        DBPlane dbPlane = new DBPlane();
        DBSeat dbSeat = new DBSeat();
        DBFlightSchedule dbFlightSchedule = new DBFlightSchedule();
        DBPassenger dbPassenger = new DBPassenger();
        DBEmployee dbEmployee = new DBEmployee();

        planes = dbPlane.readAll();
        seats = dbSeat.readAll();
        schedules = dbFlightSchedule.readAll();
        passengers = dbPassenger.readAll();
        employees = dbEmployee.readAll();
    }

    // every time the scene is changed, we need to make it movable so the user can drag it around the screen
    public static void makeSceneMovable(){

        window.getScene().setOnMousePressed(e -> {
            xOffset = FlyingDogAirlines.window.getX() - e.getScreenX();
            yOffset = FlyingDogAirlines.window.getY() - e.getScreenY();
        });
        window.getScene().setOnMouseDragged(e -> {
            FlyingDogAirlines.window.setX(e.getScreenX() + xOffset);
            FlyingDogAirlines.window.setY(e.getScreenY() + yOffset);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
