package FlyingDogAirlines.PresentationLayer;

import FlyingDogAirlines.ApplicationLayer.Controller.LogInViewController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LogInView {

    private Scene logInScene;
    private BorderPane root;
    // left side of the screen
    private Image logoImage;
    public static ImageView logoImageView;
    public static Label alreadyAUser, logInUserLabel, logInPassLabel;
    public static TextField logInUserTF;
    public static PasswordField logInPassF;
    public static Button logInButton;
    public static VBox leftVBox = new VBox(14);
    // right side of the screen
    public static Label haveAccount, registerLabel, fNameLabel, lNameLabel, userLabel, passLabel, confPassLabel;
    public static TextField fNameTF, lNameTF, userTF;
    public static PasswordField passF, confPassF;
    public static Button signUpButton;
    public static VBox rightVBox = new VBox(28);

    public Scene setLogInScene(){
        //--------------------------------------------------------------------------------------
        // ----------------------------------LEFT SIDE------------------------------------------
        //--------------------------------------------------------------------------------------


        logoImage = new Image("logo1.png");
        logoImageView = new ImageView(logoImage);
        logoImageView.setFitHeight(260);
        logoImageView.setFitWidth(260);
        alreadyAUser = new Label("Already a user ?");
        alreadyAUser.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        logInUserLabel = new Label("Username: ");
        logInPassLabel = new Label("Password:  ");
        logInUserTF = new TextField();
        logInPassF = new PasswordField();
        logInButton = new Button("Log in");

        // set the leftVBox
        LogInViewController.setLeftVBox();

        //--------------------------------------------------------------------------------------
        // ----------------------------------RIGHT SIDE-----------------------------------------
        //--------------------------------------------------------------------------------------

        haveAccount = new Label("Don't have an account yet ?");
        haveAccount.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        registerLabel = new Label("Register below:");
        registerLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        fNameLabel = new Label("First Name            ");
        lNameLabel = new Label("Last Name            ");
        userLabel = new Label("Username             ");
        passLabel = new Label("Password              ");
        confPassLabel = new Label("Confirm Password");
        fNameTF = new TextField();
        lNameTF = new TextField();
        userTF = new TextField();
        passF = new PasswordField();
        confPassF = new PasswordField();
        signUpButton = new Button("Sign Up");

        // set the rightVBox
        LogInViewController.setRightVBox();

        // set the BorderPane
        root = new BorderPane();
        HBox centerHBox = new HBox(80,leftVBox,rightVBox);
        root.setCenter(centerHBox);
        Region topReg = new Region();
        topReg.setPrefHeight(50);
        VBox topVBox = new VBox();
        topVBox.getChildren().addAll(MainMenuBar.getLogInMenuBar(),topReg);
        root.setTop(topVBox);
        Region leftReg = new Region();
        leftReg.setPrefWidth(50);
        root.setLeft(leftReg);

        // set the Scene and its CSS style
        logInScene = new Scene(root,800,540, Color.TRANSPARENT);
        logInScene.getStylesheets().add("chili.css");

        // employee logs in
        logInButton.setOnAction(e -> LogInViewController.checkLogIn());

        // creates new employee
        signUpButton.setOnAction(e -> LogInViewController.validateNewUser());

        logInButton.setDefaultButton(true);

        return logInScene;
    }
}
