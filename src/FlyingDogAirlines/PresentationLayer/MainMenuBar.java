package FlyingDogAirlines.PresentationLayer;

import FlyingDogAirlines.ApplicationLayer.Controller.CSVFileWriter;
import FlyingDogAirlines.FlyingDogAirlines;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MainMenuBar {

    static LogInView logInView = new LogInView();

    static MenuBar menuBar = new MenuBar();
    static Menu fileMenu = new Menu("File"),
                settingsMenu = new Menu("Settings");
    static MenuItem logOut = new MenuItem("Log Out"),
                    exit = new MenuItem("Exit"),
                    dbSnapShot = new MenuItem("DB SnapShot");


    // returns a generic menuBar
     static MenuBar getMenuBar(){
        // first we remove all menus from the previous scene
        menuBar.getMenus().removeAll(fileMenu,settingsMenu);
        fileMenu.getItems().removeAll(logOut,exit);
        settingsMenu.getItems().remove(dbSnapShot);

        menuBar.getMenus().addAll(fileMenu,settingsMenu);

        fileMenu.getItems().addAll(logOut,exit);

        settingsMenu.getItems().addAll(dbSnapShot);

        menuActions();

        return menuBar;
    }

    // returns a specific menuBar for the logIn screen where you don't have the possibility to log out
    static MenuBar getLogInMenuBar(){
        // first we remove all menus from the previous scene
        menuBar.getMenus().removeAll(fileMenu,settingsMenu);
        fileMenu.getItems().removeAll(logOut,exit);

        settingsMenu.getItems().removeAll(dbSnapShot);

        menuBar.getMenus().addAll(fileMenu,settingsMenu);

        fileMenu.getItems().addAll(exit);

        settingsMenu.getItems().addAll(dbSnapShot);

        menuActions();

        return menuBar;
    }

    // trigger some actions by pressing a menuItem
    private static void menuActions(){

        dbSnapShot.setOnAction(event -> {
            CSVFileWriter.writeCsvFileForPlane();
            CSVFileWriter.writeCsvFileForFlightSchedule();
        });

        logOut.setOnAction(e -> {
            FlyingDogAirlines.window.setScene(logInView.setLogInScene());
            FlyingDogAirlines.makeSceneMovable();
        });

        exit.setOnAction(e -> FlyingDogAirlines.window.close());
    }
}
