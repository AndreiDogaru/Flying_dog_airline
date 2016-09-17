package FlyingDogAirlines.ApplicationLayer.Controller;

import FlyingDogAirlines.ApplicationLayer.DataType.FlightSchedule;
import FlyingDogAirlines.ApplicationLayer.DataType.Plane;
import FlyingDogAirlines.FlyingDogAirlines;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class CSVFileWriter {

    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    public static void writeCsvFileForFlightSchedule() {

        String fileName = "FlightSchedule.csv";

        //Create a new list of FlightSchedule objects

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);

            //Write the CSV file header
            fileWriter.append("Flight Schedule CSV Generated ON: "+
                    LocalDateTime.now().toString()+
                    NEW_LINE_SEPARATOR);

            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new FlightSchedule object list to the CSV file
            for (FlightSchedule flightSchedule : FlyingDogAirlines.schedules) {
                fileWriter.append(String.valueOf(flightSchedule.getScheduleId()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(flightSchedule.getPlaneId()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(flightSchedule.getDepartureDate().toString());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(flightSchedule.getDeparture_city());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(flightSchedule.getArrivalDate().toString());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(flightSchedule.getArrival_city());
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
    }

    public static void writeCsvFileForPlane() {

        String fileName = "Plane.csv";

        //Create a new list of Plane objects

        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(fileName);

            //Write the CSV file header
            fileWriter.append("Plane CSV Generated ON: "+
                    LocalDateTime.now().toString()+
                    NEW_LINE_SEPARATOR);


            //Add a new line separator after the header
            fileWriter.append(NEW_LINE_SEPARATOR);

            //Write a new FlightSchedule object list to the CSV file
            for (Plane plane : FlyingDogAirlines.planes) {
                fileWriter.append(String.valueOf(plane.getId_plane()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(plane.getBrand());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(plane.getModel());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(plane.getMax_capacity()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(plane.getFirst_class()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(plane.getBusiness_class()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(plane.getEconomy_class()));
                fileWriter.append(NEW_LINE_SEPARATOR);
            }

            System.out.println("CSV file was created successfully !!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {

            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }

        }
    }

}
