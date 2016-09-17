package FlyingDogAirlines.PresentationLayer;

import FlyingDogAirlines.ApplicationLayer.Controller.SearchViewController;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class SearchView {

    private Scene searchScene;
    private BorderPane root;
    private Button searchButton, editFleetButton;
    public static  DatePicker departureDate;
    public static ChoiceBox<String> departureCity, arrivalCity;
    private Label departFromLabel;
    private Label arriveAtLabel;
    private Image departureImage;
    private Image arrivalImage;
    private ImageView departureImageView;
    private ImageView arrivalImageView;

    public static String departureCityName;
    public static String arrivalCityName;

    public Scene setSearchScene() {

        searchButton = new Button("Search");
        searchButton.setPrefWidth(150);
        editFleetButton = new Button("Edit Fleet");
        editFleetButton.setPrefWidth(150);
        departureDate = new DatePicker();
        departureCity = new ChoiceBox<>();
        departureCity.setPrefWidth(150);
        arrivalCity = new ChoiceBox<>();
        arrivalCity.setPrefWidth(150);
        departFromLabel = new Label("       Depart From");
        arriveAtLabel = new Label("Arrive At");
        departureImage = new Image("FDA Assets/takeoff.png");
        arrivalImage = new Image("FDA Assets/landing.png");
        departureImageView = new ImageView(departureImage);
        departureImageView.setFitHeight(100);
        departureImageView.setFitWidth(100);
        arrivalImageView = new ImageView(arrivalImage);
        arrivalImageView.setFitHeight(100);
        arrivalImageView.setFitWidth(100);

        Region regImage = new Region();
        regImage.setPrefSize(20,1);
        Region regBetweenImageLabel = new Region();
        regBetweenImageLabel.setPrefSize(1,12);
        Region regBetweenLabelCity = new Region();
        regBetweenLabelCity.setPrefSize(1,12);
        Region regBetweenCityDate = new Region();
        regBetweenCityDate.setPrefSize(1,30);
        Region regBetweenDateSearch = new Region();
        regBetweenDateSearch.setPrefSize(1,40);
        Region regDate = new Region();
        regDate.setPrefSize(100,1);
        Region regSearch = new Region();
        regSearch.setPrefSize(30,1);

        HBox imageHBox = new HBox(175,departureImageView, arrivalImageView);
        HBox regImageHBox = new HBox(regImage,imageHBox);
        HBox labelHBox = new HBox(200,departFromLabel,arriveAtLabel);
        HBox cityHBox = new HBox(120,departureCity, arrivalCity);
        HBox dateHBox = new HBox(regDate,departureDate);
        HBox searchHBox = new HBox(regSearch,editFleetButton, searchButton);
        searchHBox.setSpacing(25);

        VBox vBox = new VBox(regImageHBox,regBetweenImageLabel,labelHBox,regBetweenLabelCity,cityHBox,
                regBetweenCityDate,dateHBox,regBetweenDateSearch,searchHBox);

        root = new BorderPane();
        Region topRegion = new Region();
        topRegion.setPrefSize(1,60);
        Region leftRegion = new Region();
        leftRegion.setPrefSize(70,1);
        root.setCenter(vBox);
        root.setLeft(leftRegion);
        root.setTop(topRegion);
        root.setTop(MainMenuBar.getMenuBar());

        searchScene = new Scene(root,550,400, Color.TRANSPARENT);
        searchScene.getStylesheets().add("chili.css");

        // make an instantiation of the Controller class
        SearchViewController searchViewController = new SearchViewController();

        searchViewController.fillChoiceBoxes();

        searchButton.setOnAction(e -> searchViewController.changeToBookingScene());
        editFleetButton.setOnAction(event -> searchViewController.changeToEditPlaneScene());

        return searchScene;
    }
}