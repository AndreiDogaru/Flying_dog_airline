package FlyingDogAirlines.PresentationLayer;

import FlyingDogAirlines.ApplicationLayer.Controller.RefundCancelViewController;
import FlyingDogAirlines.ApplicationLayer.DataType.Seat;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class RefundCancelView {


    private static Stage window;
    private static Scene cancelRefundScene;
    private static BorderPane root, topPane, bottomPane;
    private static SplitPane splitPane;
    private static TableView<Seat> seatTableView;
    private static TableColumn<Seat, Integer> seatIDCol, passengerIDCol;
    private static TableColumn<Seat, String> statusCol, cabinCol;
    private static TableColumn<Seat, Double> priceCol;
    private static Label seatIdLabel, passengerIdLabel, cabinLabel, statusLabel,
            priceLabel, seatIdValueLabel, passengerIdValueLabel, cabinValueLabel,
            statusValueLabel, priceValueLabel;
    private static RefundCancelViewController controller;

    private static Button backToBooking, viewSelected, cancelBookingButton;

    public static void showRefundPopUp()
    {
        window = new Stage();
        controller = new RefundCancelViewController();

        root = new BorderPane();
        root.setCenter(setSplitPane());

        cancelRefundScene = new Scene(root,550, 500);

        addListeners();

        window.setScene(cancelRefundScene);
        window.show();
    }


    private static SplitPane setSplitPane()
    {

        viewSelected= new Button("View Details");
        backToBooking = new Button("Back to Booking");
        cancelBookingButton = new Button("Cancel Booking");

        topPane = new BorderPane();
        topPane.setCenter(getSeatTableView());
        topPane.setBottom(viewSelected);

        HBox buttonRow = new HBox();
        buttonRow.getChildren().addAll(backToBooking, cancelBookingButton);
        buttonRow.alignmentProperty().setValue(Pos.CENTER_RIGHT);

        bottomPane = new BorderPane();
        bottomPane.setCenter(seatDataFields());
        bottomPane.setBottom(buttonRow);
        bottomPane.setVisible(true);

        splitPane = new SplitPane();
        splitPane.getItems().addAll(topPane, bottomPane);
        splitPane.setOrientation(Orientation.VERTICAL);

        return splitPane;
    }

    private static TableView<Seat> getSeatTableView()
    {
        seatTableView = new TableView(controller.getSeatList());
        seatIDCol = new TableColumn<>("Seat Number");
        cabinCol = new TableColumn<>("Cabin");
        priceCol = new TableColumn<>("Price");
        statusCol = new TableColumn<>("Availability");
        passengerIDCol = new TableColumn<>("Passenger ID");

        seatIDCol.setCellValueFactory(cell -> cell.getValue().seatIdProperty().asObject());
        passengerIDCol.setCellValueFactory(cell -> cell.getValue().passengerIdProperty().asObject());
        cabinCol.setCellValueFactory(cell -> cell.getValue().cabinProperty());
        statusCol.setCellValueFactory(cell -> cell.getValue().statusProperty());
        priceCol.setCellValueFactory(cell -> cell.getValue().priceProperty().asObject());

        seatTableView.getColumns().addAll(seatIDCol, passengerIDCol, cabinCol, statusCol, priceCol);

        return seatTableView;
    }

    private static GridPane seatDataFields()
    {
        GridPane gp = new GridPane();
        seatIdLabel = new Label("Seat Number: ");
        seatIdValueLabel = new Label();

        passengerIdLabel = new Label("Passenger ID: ");
        passengerIdValueLabel = new Label();

        cabinLabel = new Label("Cabin:        ");
        cabinValueLabel = new Label();

        priceLabel = new Label("Price:        ");
        priceValueLabel = new Label();

        statusLabel = new Label("Status:       ");
        statusValueLabel = new Label();

        gp.addColumn(0, seatIdLabel, passengerIdLabel, cabinLabel, priceLabel, statusLabel);
        gp.addColumn(1, seatIdValueLabel, passengerIdValueLabel, cabinValueLabel, priceValueLabel, statusValueLabel);

        gp.setAlignment(Pos.CENTER);

        return gp;
    }

    private static void addListeners()
    {
        backToBooking.setOnAction(event -> window.close());

        viewSelected.setOnAction(event -> {
            // add details for that refund
            addDetailsForTheSelectedSeat();
            });

        seatTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    bottomPane.setVisible(true);
                    cancelBookingButton.setOnAction(event -> {
                        controller.refundSelectedTicket(newValue);

                        RefundCancelView.window.close();
                        RefundCancelView.showRefundPopUp();

                        getSeatTableView().getItems().clear();
                        getSeatTableView();

                    });
                });
    }

    public static void addDetailsForTheSelectedSeat(){
        // first empty all the labels
        seatIdValueLabel.setText("");
        passengerIdValueLabel.setText("");
        cabinValueLabel.setText("");
        priceValueLabel.setText("");
        statusValueLabel.setText("");

        // then assign the labels with the selected seat details
        seatIdValueLabel.setText(""+seatTableView.getSelectionModel().getSelectedItem().getSeatId());
        passengerIdValueLabel.setText(""+seatTableView.getSelectionModel().getSelectedItem().getPassengerId());
        cabinValueLabel.setText(""+seatTableView.getSelectionModel().getSelectedItem().getCabin());
        priceValueLabel.setText(""+seatTableView.getSelectionModel().getSelectedItem().getPrice());
        statusValueLabel.setText(""+seatTableView.getSelectionModel().getSelectedItem().getStatus());
    }
}