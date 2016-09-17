package FlyingDogAirlines.PresentationLayer;

import FlyingDogAirlines.ApplicationLayer.Controller.EditPlaneViewController;
import FlyingDogAirlines.ApplicationLayer.DataType.Plane;
import FlyingDogAirlines.FlyingDogAirlines;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class EditPlaneView {
    private ComboBox<Plane> selectAircraft;
    private Button newPlane, editSeats, viewAddFLights, cancelButton, saveButton, backToSearch;
    public static Label theFleet, brandLabel, brand, modelLabel, model, idLabel,
            id, seatingConfiguration, firstClass, buisnessClass, economyClass;
    public static Label firstClassSeats, businessClassSeats, firstClassSeats2, buisnessClassSeats2, economySeats2,
            economySeats;
    private GridPane theFleetGrid, newPlaneTopView, newPlaneBottomView;
    private EditPlaneViewController editPlaneViewController = new EditPlaneViewController();
    private VBox newPlaneFullView;
    static ImageView planePicture;

    public Scene setPlaneOverviewScene() {

        BorderPane root = new BorderPane();

        //sets right and left, center and right side of the EditPlaneView
        root.setRight(rightSideGrid());
        root.setLeft(leftSideGrid());
        root.setCenter(centerDevider());
        root.setTop(MainMenuBar.getMenuBar());


        newPlane.setOnAction(event -> {
            toggleNewPlaneButton();
        });

        saveButton.setOnAction(event -> {
            toggleSaveButton();
        });

        cancelButton.setOnAction(event -> {
            toggleCancelButton();
        });

        // returns a scene to the main
        Scene editPlaneViewScene = new Scene(root,750,700, Color.TRANSPARENT);
        editPlaneViewScene.getStylesheets().add("chili.css");
        return editPlaneViewScene;
    }

    //--------------------------------the right side -----------------------------------------

    private GridPane rightSideGrid() {

        planePicture = new ImageView();
        Image planeImage = new Image("FDA Assets/Airbus/airbus_A350.png");
        brandLabel = new Label("Brand:");
        modelLabel = new Label("Model:");
        idLabel = new Label("ID:");
        brand = new Label();
        model = new Label();
        id = new Label();
        brand = new Label();
        model = new Label();
        id = new Label();

        economySeats2=new Label();
        buisnessClassSeats2 = new Label();
        firstClassSeats2 = new Label();

        //seat stats in gridpane
        firstClass = new Label("First Class:");
        buisnessClass = new Label("Business Class:");
        economyClass = new Label("Economy Class:");
        firstClassSeats = new Label();
        businessClassSeats = new Label();
        economySeats = new Label();

        planePicture.setImage(planeImage);
        planePicture.preserveRatioProperty().setValue(true);
        planePicture.setFitHeight(200);
        planePicture.setFitWidth(280);


        GridPane statsGrid = new GridPane();
        statsGrid.addColumn(1, brandLabel, modelLabel, idLabel);
        statsGrid.addColumn(2, brand, model, id);
        statsGrid.setHgap(20);
        statsGrid.setPadding(new Insets(30, 30, 30, 30));

        //label between plane stats and seat stats
        seatingConfiguration = new Label("Seating Configuration");
        GridPane seatingConfigGrid = new GridPane();
        seatingConfigGrid.addColumn(1, seatingConfiguration);
        seatingConfigGrid.setHgap(20);
        seatingConfigGrid.setPadding(new Insets(30, 30, 30, 30));

        GridPane seatsGrid = new GridPane();
        seatsGrid.addColumn(1, firstClass, buisnessClass, economyClass);
        seatsGrid.addColumn(2, firstClassSeats, businessClassSeats, economySeats);
        seatsGrid.addColumn(3, firstClassSeats2, buisnessClassSeats2, economySeats2);
        seatsGrid.setHgap(20);
        seatsGrid.setPadding(new Insets(30, 30, 30, 30));

        // buttons for edit seats and view/add flight in gridpane
        editSeats = new Button("Edit Seats");
        viewAddFLights = new Button("View / Add Flights");
        Label emptyLable1 = new Label();
        GridPane buttonGrid = new GridPane();
        buttonGrid.addColumn(1, emptyLable1, viewAddFLights);
        buttonGrid.setHgap(20);
        buttonGrid.setPadding(new Insets(30, 30, 30, 30));

        //Hbox to hold the 3 above gridpanes for the right side of gui
        GridPane RightSideGrid = new GridPane();
        RightSideGrid.addColumn(1, planePicture, statsGrid, seatingConfigGrid, seatsGrid, buttonGrid);
        RightSideGrid.setMinSize(300, 500);

        return RightSideGrid;
    }
    //--------------------------------the left side -----------------------------------------

    private GridPane leftSideGrid() {
        GridPane leftSideGrid = new GridPane();
        theFleetGrid = new GridPane();
        theFleet = new Label("The Fleet");
        selectAircraft = new ComboBox<>();
        GridPane theButtonGrid = new GridPane();
        NewPlaneView newPlaneView = new NewPlaneView();
        newPlaneFullView = new VBox();
        backToSearch = new Button("Back to Search");

        //fills the choiceBox with stats from planes array in main
        selectAircraft.getItems().addAll(editPlaneViewController.fillPlaneChoiceBox());

        //image changer functionality and loads data in right view
        selectAircraft.setOnAction(event -> {

            planePicture.setImage(editPlaneViewController.changePlaneImage(selectAircraft.getSelectionModel().getSelectedItem().toString()));
            editPlaneViewController.showSelectedPlaneData(selectAircraft.getSelectionModel().getSelectedItem());
            viewAddFLights.setOnAction(e -> editPlaneViewController.changeToFlightSchedule(selectAircraft.getSelectionModel().getSelectedItem()));

        });

        backToSearch.setOnAction(event -> {
            toggleBackToSearchButton();
        });

        newPlane = new Button("New Plane");

        theFleetGrid.addColumn(1, theFleet, selectAircraft);
        theFleetGrid.setHgap(20);
        theFleetGrid.setPadding(new Insets(30, 30, 30, 30));
        Region region = new Region();
        region.setMinHeight(400);

        saveButton = new Button("Save");
        cancelButton = new Button("Cancel");

        theButtonGrid.addColumn(1, newPlane, backToSearch);
        theButtonGrid.addColumn(3, saveButton);
        theButtonGrid.addColumn(2, cancelButton);
        theButtonGrid.setHgap(20);
        theButtonGrid.setPadding(new Insets(30, 30, 30, 30));
        theButtonGrid.setVgap(20);


        //view for the newPlaneView
        newPlaneTopView = newPlaneView.NewPlaneViewTop();
        newPlaneBottomView = newPlaneView.NewPlaneViewBottom();
        newPlaneFullView = new VBox();
        Region region1 = new Region();
        region1.setPrefSize(30, 30);
        newPlaneFullView.getChildren().addAll(newPlaneTopView, region1, newPlaneBottomView);

        saveButton.setVisible(false);
        cancelButton.setVisible(false);

        leftSideGrid.addColumn(1, theFleetGrid, newPlaneFullView, theButtonGrid);
        newPlaneFullView.setVisible(false);
        leftSideGrid.setMinSize(300, 500);


        return leftSideGrid;
    }

    //-----------------------------------the center devider---------------------------------------

    private ImageView centerDevider() {
        //the center
        ImageView divider = new ImageView();
        divider.setFitHeight(600);
        divider.setFitWidth(30);
        Image dividerBlack = new Image("FDA Assets/STRIPE.png");
        divider.setImage(dividerBlack);
        return divider;
    }


    //-----------------------------------functionality for buttons and choiceBoxes-----------------------

    private void toggleNewPlaneButton(){
        buisnessClassSeats2.setText(null);
        economySeats2.setText(null);
        firstClassSeats2.setText(null);
        id.setText(null);
        model.setText(null);
        brand.setText(null);
        planePicture.setImage(null);
        theFleetGrid.setVisible(false);
        newPlaneFullView.setVisible(true);
        newPlane.setVisible(false);
        saveButton.setVisible(true);
        cancelButton.setVisible(true);
        editPlaneViewController.setSelectedPlane(null);
        editSeats.setVisible(false);
        viewAddFLights.setVisible(false);


        selectAircraft.getSelectionModel().clearSelection();

    }
    private void toggleSaveButton(){

       if (editPlaneViewController.validateSeatConfig()) {
           editPlaneViewController.savePlaneData();

        theFleetGrid.setVisible(true);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        newPlane.setVisible(true);
        newPlaneFullView.setVisible(false);
        editSeats.setVisible(true);
        viewAddFLights.setVisible(true);
           
           FlyingDogAirlines.fillArrayLists();

           selectAircraft.getItems().clear();
           selectAircraft.getItems().addAll(editPlaneViewController.fillPlaneChoiceBox());

           System.out.println(FlyingDogAirlines.planes.toString());
           System.out.println(editPlaneViewController.fillPlaneChoiceBox().toString());

           theFleetGrid.setVisible(true);
           saveButton.setVisible(false);
           cancelButton.setVisible(false);
           newPlane.setVisible(true);
           newPlaneFullView.setVisible(false);
           editSeats.setVisible(true);
           viewAddFLights.setVisible(true);

           selectAircraft.getSelectionModel().clearSelection();

           //clears the new plane section
           NewPlaneView.fClassField.clear();
           NewPlaneView.bClassField.clear();
           NewPlaneView.eClassField.clear();
           NewPlaneView.brandChoice.getSelectionModel().clearSelection();
           NewPlaneView.modelChoice.getSelectionModel().clearSelection();
           NewPlaneView.maxCapacityLabel.setText(null);
           NewPlaneView.remainingSeatsLabel.setText(null);

       }

    }
    private void toggleCancelButton(){
        NewPlaneView.fClassField.clear();
        NewPlaneView.bClassField.clear();
        NewPlaneView.eClassField.clear();
        NewPlaneView.brandChoice.getSelectionModel().clearSelection();
        NewPlaneView.modelChoice.getSelectionModel().clearSelection();
        NewPlaneView.maxCapacityLabel.setText(null);
        NewPlaneView.remainingSeatsLabel.setText(null);
        brand.setText(null);

        selectAircraft.getSelectionModel().clearSelection();

        theFleetGrid.setVisible(true);
        saveButton.setVisible(false);
        cancelButton.setVisible(false);
        newPlane.setVisible(true);
        newPlaneFullView.setVisible(false);
        editSeats.setVisible(true);
        viewAddFLights.setVisible(true);
    }

    private void toggleBackToSearchButton() {
        SearchView searchView = new SearchView();
        FlyingDogAirlines.window.setScene(searchView.setSearchScene());
        FlyingDogAirlines.makeSceneMovable();
    }
}
