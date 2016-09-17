package FlyingDogAirlines.ApplicationLayer.DataType;

public class Passenger extends Person{


    // Null Constructor
    public Passenger(){}

    // Secondary Constructor (Overloading the Class)
    public Passenger(int passengerID, String firstName,
                     String lastName, String username, String password)
    {
        super(passengerID, firstName, lastName, username, password);

    }


    public String toString()
    {
        return String.format("Name: %s, %s, \n", firstNameProperty().getValue(),
                lastNameProperty().getValue());
    }
}
