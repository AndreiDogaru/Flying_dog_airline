package FlyingDogAirlines.PresentationLayer;

import FlyingDogAirlines.ApplicationLayer.Controller.BookingViewController;
import FlyingDogAirlines.ApplicationLayer.Controller.RefundCancelViewController;
import FlyingDogAirlines.ApplicationLayer.DataType.FlightSchedule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class BookingView {

    private Scene bookingScene;
    private BorderPane root;
    public static TableView<FlightSchedule> scheduleTableView;
    private TableColumn<FlightSchedule, Integer> scheduleIdColumn;
    private TableColumn<FlightSchedule, Integer> planeIdColumn;
    private TableColumn<FlightSchedule, String> departureDateColumn;
    private TableColumn<FlightSchedule, String> arrivalDateColumn;
    private Label departureCity, arrivalCity, departureCityValue, arrivalCityValue, aircraftLabel, selectCabinLabel;
    public static Label remainingTickets, firstTickets, businessTickets, economyTickets;
    public static Label brandLabel, modelLabel;
    private Button backToSearch, showSeats;
    public static Button bookNow;
    public static ToggleGroup cabinToggleGroup = new ToggleGroup();
    public static RadioButton firstClass = new RadioButton("First Class"),
            businessClass = new RadioButton("Business Class"),
            economyClass = new RadioButton("Economy Class");

    public ObservableList<FlightSchedule> selectedScheduls;

    public Scene setBookingScene() {

        departureCity = new Label("     Departure City: ");
        arrivalCity = new Label("     Arrival City: ");
        departureCityValue = new Label();
        departureCityValue.setText(SearchView.departureCityName);
        arrivalCityValue = new Label();
        arrivalCityValue.setText(SearchView.arrivalCityName);
        backToSearch = new Button("Back to search");
        bookNow = new Button("Book now");
        aircraftLabel = new Label("     Aircraft: ");
        brandLabel = new Label("     Brand: ");
        modelLabel = new Label("     Model: ");
        selectCabinLabel = new Label("Select Cabin: ");
        remainingTickets = new Label("     Remaining Tickets: ");
        firstTickets = new Label("     First Class: 0");
        businessTickets = new Label("     Business Class: 0");
        economyTickets = new Label("     Economy Class: 0");

        showSeats = new Button("View Seats");


        // make an instantiation of the Controller class
        BookingViewController bookingViewController = new BookingViewController();

        selectedScheduls = FXCollections.observableArrayList(bookingViewController.populateSchedulesList());

        scheduleTableView = new TableView<>();
        scheduleTableView.setPrefHeight(200);
        scheduleTableView.itemsProperty().setValue(selectedScheduls);

        scheduleIdColumn = new TableColumn<>("Schedule Id");
        scheduleIdColumn.setMinWidth(120);
        planeIdColumn = new TableColumn<>("Plane Id");
        planeIdColumn.setMinWidth(120);
        departureDateColumn = new TableColumn<>("Departure Date");
        departureDateColumn.setMinWidth(160);
        arrivalDateColumn = new TableColumn<>("Arrival Date");
        arrivalDateColumn.setMinWidth(160);

        //Attach Action Listeners
        scheduleIdColumn.setCellValueFactory(new PropertyValueFactory<>("scheduleId"));
        planeIdColumn.setCellValueFactory(new PropertyValueFactory<>("planeId"));
        departureDateColumn.setCellValueFactory(cell -> cell.getValue().departure_dateTimeStringProperty());
        arrivalDateColumn.setCellValueFactory(cell -> cell.getValue().arrival_dateTimeStringProperty());
        scheduleTableView.getColumns().addAll(scheduleIdColumn, planeIdColumn, departureDateColumn, arrivalDateColumn);

        Region regBetweenTableLabels = new Region();
        regBetweenTableLabels.setPrefHeight(20);
        VBox tableVBox = new VBox(scheduleTableView, regBetweenTableLabels);

        HBox departureHBox = new HBox(10, departureCity, departureCityValue);
        HBox arrivalHBox = new HBox(10, arrivalCity, arrivalCityValue);
        VBox cityVBox = new VBox(5, departureHBox, arrivalHBox);

        Region regBetweenCityAircraft = new Region();
        regBetweenCityAircraft.setPrefHeight(10);
        VBox aircraftVBox = new VBox(5, regBetweenCityAircraft, aircraftLabel, brandLabel, modelLabel);

        Region regBetweenAircraftTickets = new Region();
        regBetweenAircraftTickets.setPrefHeight(10);
        VBox ticketsVBox = new VBox(5,regBetweenAircraftTickets,remainingTickets,firstTickets,businessTickets,economyTickets);

        VBox leftVBox = new VBox(cityVBox, aircraftVBox,ticketsVBox);

        firstClass.setToggleGroup(cabinToggleGroup);
        firstClass.setDisable(true);
        businessClass.setToggleGroup(cabinToggleGroup);
        businessClass.setDisable(true);
        economyClass.setToggleGroup(cabinToggleGroup);
        economyClass.setDisable(true);
        cabinToggleGroup.selectToggle(firstClass);
        VBox toggleGroupVBox = new VBox(5,selectCabinLabel,firstClass,businessClass,economyClass);

        HBox centerHBox = new HBox(250,leftVBox,toggleGroupVBox);

        Region regButton = new Region();
        regButton.setPrefWidth(330);
        Region regBeforeShowSeats = new Region();
        regBeforeShowSeats.setPrefWidth(20);
        HBox buttonsHBox = new HBox(10, regBeforeShowSeats, showSeats, regButton, backToSearch, bookNow);

        // set the BorderPane
        root = new BorderPane();
        VBox topVBox = new VBox();
        topVBox.getChildren().addAll(MainMenuBar.getMenuBar(),tableVBox);
        root.setTop(topVBox);
        root.setCenter(centerHBox);
        Region bottomReg = new Region();
        bottomReg.setPrefHeight(20);
        VBox bottomVBox = new VBox(buttonsHBox,bottomReg);
        root.setBottom(bottomVBox);

        // set the Scene and its CSS style
        bookingScene = new Scene(root, 700, 600, Color.TRANSPARENT);
        bookingScene.getStylesheets().add("chili.css");

        // when a flight schedule in the tableView is selected
        //      -> first we set all the brand and model labels on the scene with the specific info
        //      -> then we set the tickets labels with the remaining number of tickets for each cabinClass
        //      -> finally we disable the "book now" button if there are no seats available
        scheduleTableView.setOnMouseClicked(e -> {
            BookingViewController.setLabelText();
            BookingViewController.setRemainingTickets();
            BookingViewController.checkSeatsAvailability();
        });

        scheduleTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            BookingViewController.selectedSchedule = newValue;
            RefundCancelViewController.fs = newValue;
            System.out.println(RefundCancelViewController.fs);
        });

        // go back to the previous scene
        backToSearch.setOnAction(e -> bookingViewController.changeToSearchScene());

        // Show all seats and their status for a given flight schedule
        showSeats.setOnAction(e -> bookingViewController.changeToSeatScene());

        // go to next scene
        bookNow.setOnAction(e -> bookingViewController.changeToCheckOutView());

        return bookingScene;
    }
}
