package FlyingDogAirlines.PresentationLayer;


import FlyingDogAirlines.ApplicationLayer.Controller.FlightScheduleViewController;
import FlyingDogAirlines.ApplicationLayer.DataType.FlightSchedule;
import FlyingDogAirlines.ApplicationLayer.DataType.Plane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.time.LocalDate;
import java.time.LocalTime;

public class FlightScheduleView {

    private FlightScheduleViewController controller;
    private Scene scheduleScene;
    private BorderPane root, topPane, bottomPane;
    private SplitPane splitPane;
    public static TableView<FlightSchedule> scheduleTableView;
    private TableColumn <FlightSchedule, String> departureCityCol, arrivalCityCol, departureTimeCol, arrivalTimeCol;


    public static ComboBox<String> departureTimeBox, arrivalTimeBox;
    public static DatePicker departureDatePicker, arrivalDatePicker;
    public static TextField departureCityField, arrivalCityField, firstClassPriceField;
    public static Button addNewFlightButton, saveButton, cancelButton;


    public Scene setFlightScheduleView(Plane p)
    {
        controller = new FlightScheduleViewController();
        controller.setSelectedPlane(p);

        root = new BorderPane();
        root.setCenter(setSplitPane());
        root.setTop(MainMenuBar.getMenuBar());
        scheduleScene = new Scene(root, 560, 500, Color.TRANSPARENT);
        scheduleScene.getStylesheets().add("chili.css");

        addListeners();

        return scheduleScene;
    }

    private SplitPane setSplitPane()
    {

        addNewFlightButton= new Button("New Flight Schedule");
        saveButton = new Button("Save");
        cancelButton = new Button("Back");

        topPane = new BorderPane();
        topPane.setCenter(getScheduleTableView());
        topPane.setBottom(addNewFlightButton);

        HBox buttonRow = new HBox();
        buttonRow.getChildren().addAll(cancelButton, saveButton);
        buttonRow.alignmentProperty().setValue(Pos.CENTER_RIGHT);

        bottomPane = new BorderPane();
        bottomPane.setCenter(flightScheduleDataFields());
        bottomPane.getCenter().setVisible(false);
        bottomPane.setBottom(buttonRow);

        saveButton.setVisible(false);

        splitPane = new SplitPane();
        splitPane.getItems().addAll(topPane, bottomPane);
        splitPane.setOrientation(Orientation.VERTICAL);


        return splitPane;
    }

    private TableView<FlightSchedule> getScheduleTableView()
    {
        scheduleTableView = new TableView(controller.getFlightScheduleList());
        departureCityCol = new TableColumn<>("Departure City");
        departureCityCol.setMinWidth(120);
        departureTimeCol = new TableColumn<>("Departure Time");
        departureTimeCol.setMinWidth(160);
        arrivalCityCol = new TableColumn<>("Arrival City");
        arrivalCityCol.setMinWidth(120);
        arrivalTimeCol = new TableColumn<>("Arrival Time");
        arrivalTimeCol.setMinWidth(160);


        departureCityCol.setCellValueFactory(cell -> cell.getValue().departure_cityProperty());
        departureTimeCol.setCellValueFactory(cell -> cell.getValue().departure_dateTimeStringProperty());
        arrivalCityCol.setCellValueFactory(cell -> cell.getValue().arrival_cityProperty());
        arrivalTimeCol.setCellValueFactory(cell -> cell.getValue().arrival_dateTimeStringProperty());



        scheduleTableView.getColumns().addAll(departureCityCol, departureTimeCol, arrivalCityCol, arrivalTimeCol);

        scheduleTableView.autosize();


        return scheduleTableView;
    }

    private GridPane flightScheduleDataFields()
    {
        GridPane gp = new GridPane();
        departureCityField = new TextField();
        departureCityField.setPromptText("Departure City");

        arrivalCityField = new TextField();
        arrivalCityField.setPromptText("Arrival City");

        departureDatePicker = new DatePicker();
        departureDatePicker.setPromptText("Departure Date");

        arrivalDatePicker = new DatePicker();
        arrivalDatePicker.setPromptText("Arrival Date");

        firstClassPriceField = new TextField();
        firstClassPriceField.setPromptText("Ticket Price");

        departureTimeBox = new ComboBox<>(timepicker());
        departureTimeBox.setEditable(true);
        arrivalTimeBox = new ComboBox<>(timepicker());
        arrivalTimeBox.setEditable(true);

        gp.addRow(0, new Label("Departure"), new Label("Arrival"));
        gp.addRow(1, departureDatePicker, arrivalDatePicker);
        gp.addRow(2, departureCityField, arrivalCityField);
        gp.addRow(3, departureTimeBox, arrivalTimeBox);
        gp.addRow(4, firstClassPriceField);

        gp.setAlignment(Pos.CENTER);

        return gp;
    }

    private void addListeners()
    {
        saveButton.setOnAction(event -> {
            controller.saveData(null);
            getScheduleTableView();

        });
        cancelButton.setOnAction(event -> controller.changeToEditPlaneView());

        departureDatePicker.setOnAction(event -> {
            LocalDate date = departureDatePicker.getValue();
            arrivalDatePicker.setValue(date);
        });

        addNewFlightButton.setOnAction(event -> {
            bottomPane.getCenter().setVisible(true);
            saveButton.setVisible(true);
            departureCityField.clear();
            arrivalCityField.clear();
            departureTimeBox.getSelectionModel().select(540);
            arrivalTimeBox.getSelectionModel().select(600);
            saveButton.setOnAction(e -> {
                controller.saveData(null);
                getScheduleTableView();
            });
        });

        scheduleTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    bottomPane.getCenter().setVisible(true);
                    saveButton.setVisible(true);
                    controller.showData(newValue);
                    saveButton.setOnAction(event -> {
                        controller.saveData(newValue);
                        getScheduleTableView();});
        });
    }

    private ObservableList timepicker(){
        ObservableList <String> timeList = FXCollections.observableArrayList();

        // Nested For Loop to create all possible Times from 00:00 -> 23:59
        // For loop for Hours
        for (int i = 0; i < 24 ; i++) {
            // For loop for Minutes
            for (int j = 0; j < 4 ; j++) {
                LocalTime time = LocalTime.of(i, j*15);
                timeList.add(time.toString());
            }
        }

        return timeList;
    }

}
