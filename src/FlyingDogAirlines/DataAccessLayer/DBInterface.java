package FlyingDogAirlines.DataAccessLayer;

import java.util.ArrayList;

public interface DBInterface {
     ArrayList readAll();

     // To insert set id to -1 otherwise specify the ID
     void insertOrUpdate(int id, Object obj);
}
