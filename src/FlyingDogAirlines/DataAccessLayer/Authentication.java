package FlyingDogAirlines.DataAccessLayer;

/**
 * Created by Andrei on 25/04/2016.
 */
public class Authentication {

    public static String DB_URL = "jdbc:mysql://localhost/flying_dog_airline";
    public static String DB_USER = "root";
    public static String DB_PASS = "mysql";

    public static String getDbUrl() {
        return DB_URL;
    }

    public static String getDbUser() {
        return DB_USER;
    }

    public static String getDbPass() {
        return DB_PASS;
    }
}
