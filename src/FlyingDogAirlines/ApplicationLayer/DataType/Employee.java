package FlyingDogAirlines.ApplicationLayer.DataType;

public class Employee extends Person
{

    // Null Constructor
    public Employee(){}

    // Secondary Constructor (Overloading the Class)
    public Employee(int employee_id, String firstName,
                    String lastName, String username, String password)
    {
       super(employee_id, firstName, lastName, username, password);
    }


    public String toString()
    {
        return String.format("Name: %s, %s, Username: %s, Password: %s \n", firstNameProperty().getValue(),
                lastNameProperty().getValue(), usernameProperty().getValue(), passwordProperty().getValue());
    }
}
