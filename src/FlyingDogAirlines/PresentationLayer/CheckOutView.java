package FlyingDogAirlines.PresentationLayer;

import FlyingDogAirlines.ApplicationLayer.Controller.BookingViewController;
import FlyingDogAirlines.ApplicationLayer.Controller.CheckOutViewController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class CheckOutView {

    private Scene checkOutScene;
    private BorderPane root;
    public static Button createAccountButton, logInButton, actualCreate, actualLogIn,
            backToOriginalScene, backToBooking, payNow;
    public static Label newUserLabel, existingUserLabel, fNameLabel, lNameLabel, userLabel, passLabel, confPassLabel,
            brandLabel, modelLabel, aircraftLabel, selectedCabinLabel, departureCity, arrivalCity,
            departureTime, arrivalTime, passengerLabel;
    public static TextField fNameTField, lNameTField, userTField;
    public static PasswordField passField, confPassField;
    public static VBox leftVBox = new VBox(10);

    public Scene setCheckOutScene(){
        //--------------------------------------------------------------------------------------
        // ----------------------------------LEFT SIDE------------------------------------------
        //--------------------------------------------------------------------------------------

        // first controls which will appear on the left side
        createAccountButton = new Button("Create Account");
        createAccountButton.setPrefWidth(150);
        logInButton = new Button("Log In");
        logInButton.setPrefWidth(150);
        newUserLabel = new Label("New Passenger ?     ");
        existingUserLabel = new Label("Existing Passenger ?");

        // this button changes the leftVBox back to its original form
        backToOriginalScene = new Button("Back");

        // controls that will appear on the left side depending on which button was pressed
        fNameLabel = new Label("First Name            ");
        lNameLabel = new Label("Last Name            ");
        userLabel = new Label("Username             ");
        passLabel = new Label("Password              ");
        confPassLabel = new Label("Confirm Password");
        fNameTField = new TextField();
        lNameTField = new TextField();
        userTField = new TextField();
        passField = new PasswordField();
        confPassField = new PasswordField();
        actualCreate = new Button("Create");
        actualLogIn = new Button("Log In");
        passengerLabel = new Label("Passenger logged in: ");

        // set the leftVBox
        CheckOutViewController.originalLeftVBox();

        // actions that perform changes to the left side of the scene
        logInButton.setOnAction(e -> CheckOutViewController.logInScreen());
        createAccountButton.setOnAction(e -> CheckOutViewController.createAccountScreen());
        backToOriginalScene.setOnAction(e -> CheckOutViewController.originalLeftVBox());
        //      -> logs in a passenger
        actualLogIn.setOnAction(e -> CheckOutViewController.checkLogIn());
        //      -> creates a passenger
        actualCreate.setOnAction(e -> CheckOutViewController.validateNewPassenger());

        //--------------------------------------------------------------------------------------
        // ----------------------------------RIGHT SIDE-----------------------------------------
        //--------------------------------------------------------------------------------------

        // controls for the left side of the scene
        backToBooking = new Button("Back to Booking");
        backToBooking.setPrefWidth(150);
        payNow = new Button("Pay Now");
        payNow.setPrefWidth(150);
        aircraftLabel = new Label("Aircraft:");
        brandLabel = new Label(BookingView.brandLabel.getText().substring(5));
        modelLabel = new Label(BookingView.modelLabel.getText().substring(5));
        departureCity = new Label("Departure City:   "+ BookingViewController.selectedSchedule.getDeparture_city());
        departureTime = new Label("Departure Time: "+ BookingViewController.selectedSchedule.getArrival_dateTimeString());
        arrivalCity = new Label("Arrival City:         "+ BookingViewController.selectedSchedule.getArrival_city());
        arrivalTime = new Label("Arrival Time:       "+ BookingViewController.selectedSchedule.getArrival_dateTimeString());
        // toString() returns a rather difficult string from which we only need the selected radioButton name
        String cabin = BookingView.cabinToggleGroup.selectedToggleProperty().getValue().toString();
        // check what does the string contain and assign the required value to the label
        if(cabin.substring(46,cabin.length()-1).contains("irst"))
            selectedCabinLabel = new Label("Selected Cabin: First Class");
        else if(cabin.substring(46,cabin.length()-1).contains("nomy"))
            selectedCabinLabel = new Label("Selected Cabin: Economy Class");
        else
            selectedCabinLabel = new Label("Selected Cabin: Business Class");


        Region regBeforeAircraft = new Region();
        regBeforeAircraft.setPrefHeight(20);
        Region regAfterAircraft = new Region();
        regAfterAircraft.setPrefHeight(20);
        Region regBetweenLabelsButtons = new Region();
        regBetweenLabelsButtons.setPrefHeight(80);
        HBox buttonsHBox = new HBox(10,backToBooking,payNow);

        // set the rightVBox
        VBox rightVBox = new VBox(10,departureCity,departureTime,arrivalCity,arrivalTime,regBeforeAircraft,aircraftLabel,
                brandLabel,modelLabel,regAfterAircraft,selectedCabinLabel,regBetweenLabelsButtons,buttonsHBox);

        // make an instantiation of the Controller class
        CheckOutViewController checkOutViewController = new CheckOutViewController();

        // actions made by the right buttons
        backToBooking.setOnAction(e -> checkOutViewController.changeToBookingScene());
        payNow.setOnAction(e -> CheckOutViewController.payNow());

        // set the BorderPane
        root = new BorderPane();
        //root.setPadding(new Insets(15));
        Region leftReg = new Region();
        leftReg.setPrefWidth(20);
        HBox leftHBox = new HBox(leftReg,leftVBox);
        root.setLeft(leftHBox);
        root.setRight(rightVBox);
        Region topRegion = new Region();
        topRegion.setPrefHeight(20);
        VBox topVBox = new VBox();
        topVBox.getChildren().addAll(MainMenuBar.getMenuBar(),topRegion);
        root.setTop(topVBox);
        Region bottomReg = new Region();
        bottomReg.setPrefHeight(20);
        root.setBottom(bottomReg);

        // set the Scene and its CSS style
        checkOutScene = new Scene(root,800,400,Color.TRANSPARENT);
        checkOutScene.getStylesheets().add("chili.css");

        return checkOutScene;
    }






}
