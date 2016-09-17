package FlyingDogAirlines.ApplicationLayer.DataType;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

public class Plane {
    //attributes
    private SimpleIntegerProperty id_plane;
    private SimpleStringProperty brand;
    private SimpleStringProperty model;
    private SimpleIntegerProperty first_class;
    private SimpleIntegerProperty business_class;
    private SimpleIntegerProperty economy_class;


    private SimpleIntegerProperty max_capacity;

    private Seat[] seats;

    //null constructor
    public Plane(){}

    //constructor
    public Plane(int id_plane, String brand, String model, int first_class, int business_class, int economy_class){
        this.id_plane=new SimpleIntegerProperty(id_plane);
        this.brand=new SimpleStringProperty(brand);
        this.model=new SimpleStringProperty(model);
        this.first_class=new SimpleIntegerProperty(first_class);
        this.business_class=new SimpleIntegerProperty(business_class);
        this.economy_class=new SimpleIntegerProperty(economy_class);
        setMaxCapacity();


        seats=new Seat[getMax_capacity()+1];
    }

    //constructor
    public Plane( String brand, String model){
        this.brand=new SimpleStringProperty(brand);
        this.model=new SimpleStringProperty(model);

        setMaxCapacity();


        seats=new Seat[getMax_capacity()+1];
    }

    //setters

    public void setEconomy_class(int economy_class) {
        this.economy_class=new SimpleIntegerProperty(economy_class);
    }

    public void setBusiness_class(int business_class) {
        this.business_class = new SimpleIntegerProperty(business_class);
    }

    public void setFirst_class(int first_class) {
        this.first_class= new SimpleIntegerProperty(first_class);
    }

    public void setModel(String model) {
        this.model= new SimpleStringProperty(model);
    }

    public void setBrand(String brand) {
        this.brand = new SimpleStringProperty(brand);
    }

    public void setId_plane(int id_plane) {
        this.id_plane = new SimpleIntegerProperty(id_plane);
    }

    public void setMax_capacity(int max_capacity) {
        this.max_capacity = new SimpleIntegerProperty(max_capacity);
    }

    public void setSeats(Seat[] seats) {
        this.seats = seats;
    }

    public void setMaxCapacity(){
        if(getModel().equalsIgnoreCase("737")||getModel().equalsIgnoreCase("a320")){
            setMax_capacity(100);
        }else if(getModel().equalsIgnoreCase("787")||getModel().equalsIgnoreCase("a350")){
            setMax_capacity(200);
        }else if(getModel().equalsIgnoreCase("747")){
            setMax_capacity(300);
        }else if(getModel().equalsIgnoreCase("a380")){
            setMax_capacity(600);
        }

    }

    //getters
    public int getEconomy_class() {
        return economy_class.get();
    }

    public SimpleIntegerProperty economy_classProperty() {
        return economy_class;
    }

    public int getBusiness_class() {
        return business_class.get();
    }

    public SimpleIntegerProperty business_classProperty() {
        return business_class;
    }

    public int getFirst_class() {
        return first_class.get();
    }

    public SimpleIntegerProperty first_classProperty() {
        return first_class;
    }

    public String getModel() {
        return model.get();
    }

    public SimpleStringProperty modelProperty() {
        return model;
    }

    public String getBrand() {
        return brand.get();
    }

    public SimpleStringProperty brandProperty() {
        return brand;
    }

    public int getId_plane() {
        return id_plane.get();
    }

    public SimpleIntegerProperty id_planeProperty() {
        return id_plane;
    }

    public int getMax_capacity() {
        return max_capacity.get();
    }

    public Seat[] getSeats() {
        return seats;
    }

    public SimpleIntegerProperty max_capacityProperty() {
        return max_capacity;
    }


    public String toString(){
        return String.format("ID: %s Brand: %s, Model: %s\n",
                id_planeProperty().getValue(), brandProperty().getValue(), modelProperty().getValue());
    }

}
