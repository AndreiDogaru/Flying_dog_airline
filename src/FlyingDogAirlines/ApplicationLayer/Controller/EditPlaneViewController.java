package FlyingDogAirlines.ApplicationLayer.Controller;

import FlyingDogAirlines.ApplicationLayer.DataType.Plane;
import FlyingDogAirlines.DataAccessLayer.DBPlane;
import FlyingDogAirlines.FlyingDogAirlines;
import FlyingDogAirlines.PresentationLayer.EditPlaneView;
import FlyingDogAirlines.PresentationLayer.FlightScheduleView;
import FlyingDogAirlines.PresentationLayer.NewPlaneView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;

public class EditPlaneViewController {

    public Plane selectedPlane = new Plane();

    public FlightScheduleView flightScheduleView = new FlightScheduleView();

    public void setSelectedPlane(Plane selectedPlane) {
        this.selectedPlane = selectedPlane;
    }

    //returns observableList with planes from database to the editPlaneView
    public ObservableList fillPlaneChoiceBox(){
        ObservableList<Plane> observableList = FXCollections.observableArrayList();
        DBPlane dbPlane = new DBPlane();
        dbPlane.readAll();

        for (int i = 0; i< FlyingDogAirlines.planes.size(); i ++)
            observableList.addAll(FlyingDogAirlines.planes.get(i));

        return observableList;
    }

    public Image changePlaneImage(String planeStats){

        String imageString = "";

        if (planeStats.toLowerCase().contains("737")){
            imageString="FDA Assets/Boeing/boeing_737.png";
        }else if(planeStats.toLowerCase().contains("747")){
            imageString="FDA Assets/Boeing/boeing_747.png";
        }else if(planeStats.toLowerCase().contains("787")){
            imageString="FDA Assets/Boeing/boeing_787.png";
        }

        if(planeStats.toLowerCase().contains("a320")){
            imageString="FDA Assets/Airbus/airbus_A320.png";
        }else if(planeStats.toLowerCase().contains("a350")){
            imageString="FDA Assets/Airbus/airbus_A350.png";
        }else if(planeStats.toLowerCase().contains("a380")){
            imageString="FDA Assets/Airbus/airbus_A380.png";
        }

        Image imagep = new Image(imageString);
        return imagep;
    }

    public void changeToFlightSchedule(Plane plane){
        FlyingDogAirlines.window.setScene(flightScheduleView.setFlightScheduleView(plane));
        FlyingDogAirlines.makeSceneMovable();
    }


    public void showSelectedPlaneData(Plane plane){

        EditPlaneView.brand.setText(plane.getBrand());
        EditPlaneView.model.setText(plane.getModel());
        EditPlaneView.id.setText(plane.getId_plane()+"");
        EditPlaneView.buisnessClassSeats2.setText(plane.getBusiness_class()+"");
        EditPlaneView.firstClassSeats2.setText(plane.getFirst_class()+"");
        EditPlaneView.economySeats2.setText(plane.getEconomy_class()+"");

    }

    //Method checks which brand of plane has been chosen
    public ObservableList<String> typeChoice(String choice){

        ObservableList<String> type = FXCollections.observableArrayList();
        if(choice.equalsIgnoreCase("Airbus")){
            type.clear();
            type.addAll("A320","A350", "A380");

        }
        if(choice.equalsIgnoreCase("Boeing")){
            type.clear();
            type.addAll("737","747", "787");
        }
        return type;
    }

    public void setBrandModelSeats(String brand, String model){
        selectedPlane.setBrand(brand);
        selectedPlane.setModel(model);
        if(model!=null) {
            selectedPlane.setMaxCapacity();
        }

    }

    public boolean validateSeatConfig(){
        int firstClass = 0;
        int bclass=0;
        int eclass=0;
        boolean flag = true;

        //checks is there are more assigned seats than the max capacity, then trows an alert
        if (!NewPlaneView.fClassField.getText().isEmpty()) {
            firstClass = Integer.parseInt(NewPlaneView.fClassField.getText());
        }
        if (!NewPlaneView.bClassField.getText().isEmpty()) {
            bclass = Integer.parseInt(NewPlaneView.bClassField.getText());
        }
        if (!NewPlaneView.fClassField.getText().isEmpty()) {
            eclass = Integer.parseInt(NewPlaneView.eClassField.getText());
        }
        if(NewPlaneView.brandChoice.getSelectionModel().getSelectedItem()==null
                ||NewPlaneView.modelChoice.getSelectionModel().getSelectedItem()==null
                ||NewPlaneView.fClassField.getText()==null||NewPlaneView.bClassField.getText()==null
                ||NewPlaneView.eClassField.getText()==null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("All fields must be filled out before saving!");
            flag = false;
            alert.showAndWait();
        }
        int seatsAssigned = firstClass+bclass+eclass;
        if(seatsAssigned>Integer.parseInt(NewPlaneView.maxCapacityLabel.getText())){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Seats Assigned can not exceed the max capacity!");
            flag = false;
            alert.showAndWait();
        }

        if(firstClass<0 || bclass<0 || eclass<0){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Seats assigned cannot be negative!");
            flag = false;
            alert.showAndWait();
        }
        return flag;

    }

    public int remainingSeats() {

        int fClassAssigned = 0;
        int bClassAssigned = 0;
        int eClassAssigned = 0;

        if (!NewPlaneView.fClassField.getText().equals(""))
            fClassAssigned = Integer.parseInt(NewPlaneView.fClassField.getText());
        if (!NewPlaneView.bClassField.getText().equals(""))
            bClassAssigned = Integer.parseInt(NewPlaneView.bClassField.getText());
        if (!NewPlaneView.eClassField.getText().equals(""))
            eClassAssigned = Integer.parseInt(NewPlaneView.eClassField.getText());


        int remainingSeats = selectedPlane.getMax_capacity() - fClassAssigned - bClassAssigned - eClassAssigned;


        return remainingSeats;
    }

    public void savePlaneData(){

        if (selectedPlane == null) {
            selectedPlane = new Plane();
            selectedPlane.setBrand(NewPlaneView.brandChoice.getSelectionModel().getSelectedItem());
            selectedPlane.setModel(NewPlaneView.modelChoice.getSelectionModel().getSelectedItem());
            selectedPlane.setMaxCapacity();

            if (!NewPlaneView.fClassField.getText().equals(""))
                selectedPlane.setFirst_class(
                        Integer.parseInt(NewPlaneView.fClassField.getText()));
            if (!NewPlaneView.bClassField.getText().equals(""))
                selectedPlane.setBusiness_class(
                        Integer.parseInt(NewPlaneView.bClassField.getText()));
            if (!NewPlaneView.eClassField.getText().equals(""))
                selectedPlane.setEconomy_class(
                        Integer.parseInt(NewPlaneView.eClassField.getText()));
            selectedPlane.setId_plane(-1);
        }

        // save the newly made plane to the DB and also to our ArrayList
        DBPlane dbPlane = new DBPlane();
        dbPlane.insertOrUpdate(selectedPlane.getId_plane(), selectedPlane);

        FlyingDogAirlines.planes.add(selectedPlane);
    }



}
