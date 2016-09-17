package FlyingDogAirlines.ApplicationLayer.Controller;

import FlyingDogAirlines.FlyingDogAirlines;
import FlyingDogAirlines.PresentationLayer.BookingView;
import FlyingDogAirlines.PresentationLayer.EditPlaneView;
import FlyingDogAirlines.PresentationLayer.SearchView;
import javafx.scene.control.Alert;

public class SearchViewController {

    public void fillChoiceBoxes(){
        // the flags are used to check if we have duplicates in our Lists
        boolean departureFlag = true;
        boolean arrivalFlag = true;

        for(int i = 0; i< FlyingDogAirlines.schedules.size(); i++) {
            //  this for loop goes through every departure city and checks if the city at index i has already
            // occurred in the List
            for(int j=0;j<i;j++){
                if(FlyingDogAirlines.schedules.get(i).getDeparture_city().equals
                        (FlyingDogAirlines.schedules.get(j).getDeparture_city())){
                    // if it occurred, we make the flag false and break the for loop
                    departureFlag = false;
                    break;
                }
            }

            //  this for loop goes through every arrival city and checks if the city at index i has already
            // occurred in the List
            for(int j=0;j<i;j++){
                if(FlyingDogAirlines.schedules.get(i).getArrival_city().equals
                        (FlyingDogAirlines.schedules.get(j).getArrival_city())){
                    // if it occurred, we make the flag false and break the for loop
                    arrivalFlag = false;
                    break;
                }
            }

            // if the flags are still true, we add the cities to the respective choiceBoxes
            if(departureFlag == true){
                SearchView.departureCity.getItems().add(FlyingDogAirlines.schedules.get(i).getDeparture_city());
            }
            if(arrivalFlag == true){
                SearchView.arrivalCity.getItems().add(FlyingDogAirlines.schedules.get(i).getArrival_city());
            }
            departureFlag = true; arrivalFlag = true;
        }
    }

    public void changeToBookingScene(){

        if(SearchView.departureCity.getSelectionModel().isEmpty() || SearchView.arrivalCity.getSelectionModel().isEmpty()
                || SearchView.departureDate.getValue() == null){
            // if any of the fields is empty  -> show an alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Fill out every field !");
            alert.showAndWait();
        }else if(SearchView.departureCity.getSelectionModel().getSelectedItem().equals
                (SearchView.arrivalCity.getSelectionModel().getSelectedItem())){
            // if the user selected the same city for both departure and arrival -> show alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Please select different cities for departure and arrival !");
            alert.showAndWait();

        }else {
            // if everything is ok -> assign the city names to some strings and change to next scene
            SearchView.departureCityName = SearchView.departureCity.getSelectionModel().getSelectedItem();
            SearchView.arrivalCityName = SearchView.arrivalCity.getSelectionModel().getSelectedItem();

            BookingView bookingView = new BookingView();
            FlyingDogAirlines.window.setScene(bookingView.setBookingScene());
            FlyingDogAirlines.makeSceneMovable();
        }
    }

    public void changeToEditPlaneScene(){
        EditPlaneView planeView = new EditPlaneView();
        FlyingDogAirlines.window.setScene(planeView.setPlaneOverviewScene());
        FlyingDogAirlines.makeSceneMovable();
    }
}
