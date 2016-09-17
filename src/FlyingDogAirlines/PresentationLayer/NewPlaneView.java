package FlyingDogAirlines.PresentationLayer;

import FlyingDogAirlines.ApplicationLayer.Controller.EditPlaneViewController;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

/**
 * Created by Mac, Saxo on 23/05/2016.
 */
public class NewPlaneView {

    public static ChoiceBox<String> brandChoice, modelChoice;
    public static TextField fClassField, bClassField, eClassField;
    private GridPane newPlaneTop, newPlaneBottom;
    private Label brand, model, fClass, bClass, eClass, seatConfig, maxCapacity, remainingSeats;
    public static Label maxCapacityLabel;
    static Label remainingSeatsLabel;
    private EditPlaneViewController editPlaneViewController = new EditPlaneViewController();

    NewPlaneView() {
    }


     GridPane NewPlaneViewTop(){

        brand = new Label("Brand:");
        model = new Label("Model:");
        maxCapacityLabel = new Label();



        brandChoice = new ChoiceBox<>();
        brandChoice.getItems().addAll("Boeing", "Airbus");
        brandChoice.setOnAction(event -> {
            modelChoice.getItems().clear();
            if(brandChoice.getSelectionModel().getSelectedItem()!=null) {
                modelChoice.getItems().addAll(editPlaneViewController.typeChoice(
                        brandChoice.getSelectionModel().getSelectedItem()));
            }


            EditPlaneView.brand.setText(brandChoice.getSelectionModel().getSelectedItem());

        });

        modelChoice = new ChoiceBox<>();
        modelChoice.setOnAction(event ->{
            //sets the image of the chosen plane model
            if(modelChoice.getSelectionModel().getSelectedItem()==null){
                EditPlaneView.planePicture.setImage(null);
            }else if (modelChoice.getSelectionModel().getSelectedItem()!=null) {
                EditPlaneView.planePicture.setImage(editPlaneViewController.changePlaneImage(modelChoice.getSelectionModel().getSelectedItem()));
            }

            //sets the maxCapacity
            editPlaneViewController.setBrandModelSeats(brandChoice.getSelectionModel().getSelectedItem(),
                    modelChoice.getSelectionModel().getSelectedItem());

            maxCapacityLabel.setText(editPlaneViewController.selectedPlane.getMax_capacity()+"");
            remainingSeatsLabel.setText(editPlaneViewController.remainingSeats()+"");
            EditPlaneView.model.setText(modelChoice.getSelectionModel().getSelectedItem());

        });


        newPlaneTop = new GridPane();

        newPlaneTop.setHgap(10);
        newPlaneTop.setVgap(10);

        newPlaneTop.addColumn(1,brand, model);
        newPlaneTop.addColumn(2,brandChoice,modelChoice);

        return newPlaneTop;


    }

     GridPane NewPlaneViewBottom(){

        fClassField = new TextField();
        bClassField = new TextField();
        eClassField = new TextField();

        fClass = new Label("First Class:");
        bClass = new Label("Business Class:");
        eClass = new Label("Economy Class:");

        remainingSeats = new Label("Unassigned Seats");
        remainingSeatsLabel = new Label();
        maxCapacity = new Label("Max Capacity");

        seatConfig = new Label("Seat Configuration");

        Region filler = new Region();

        filler.setPrefSize(20,20);


        newPlaneBottom = new GridPane();

        newPlaneBottom.setHgap(10);
        newPlaneBottom.setVgap(10);

        newPlaneBottom.addColumn(1, seatConfig,fClass,bClass,eClass, maxCapacity, remainingSeats);
        newPlaneBottom.addColumn(2, filler,fClassField,bClassField,eClassField, maxCapacityLabel, remainingSeatsLabel);

        seatBindings();

        return newPlaneBottom;
    }

    private void seatBindings(){

        fClassField.textProperty().addListener(observable -> remainingSeatsLabel.setText(editPlaneViewController.remainingSeats()+""));
        bClassField.textProperty().addListener(observable -> remainingSeatsLabel.setText(editPlaneViewController.remainingSeats()+""));
        eClassField.textProperty().addListener(observable -> remainingSeatsLabel.setText(editPlaneViewController.remainingSeats()+""));

        EditPlaneView.firstClassSeats.textProperty().bind(fClassField.textProperty());
        EditPlaneView.businessClassSeats.textProperty().bind(bClassField.textProperty());
        EditPlaneView.economySeats.textProperty().bind(eClassField.textProperty());
    }
}
