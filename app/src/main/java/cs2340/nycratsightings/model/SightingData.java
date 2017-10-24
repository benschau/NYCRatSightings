package cs2340.nycratsightings.model;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class manages the list of sightings in the model
 */
public class SightingData {
    ArrayList<Sighting> backingData = new ArrayList<Sighting>();

    /**
     * Read sighting data from csv file and add it to backing array
     *
     * @param reader file to read from
     */
    public void readCSV(BufferedReader reader) {
        try {
            String line;
            reader.readLine(); //skip over header
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                String[] relevantData = {tokens[0],tokens[1],tokens[7],tokens[8], tokens[9],
                        tokens[16], tokens[23], tokens[49], tokens[50]};
                Sighting sighting = new Sighting(relevantData);
                backingData.add(sighting);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Read sighting data from internal storage. This is needed because new sightings are added to
     * internal storage when a user adds a sighting.
     * The method reads each line, retrieves the sighting's comma separated attributes, creates a
     * new sighting object using these attributes, and adds the new sighting to the sightings array.
     *
     * @param reader the file to read from
     */
    public void readInternalStorage(BufferedReader reader) {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                Sighting sighting = new Sighting(tokens);
                backingData.add(sighting);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String toString(){
        for(Sighting E: backingData){
            System.out.println(E);
        }
        return "";
    }

    /**
     * returns the sighting object at that index
     * @param index
     * @return sighting
     */
    public Sighting getSightingAtIndex(int index){
        return backingData.get(index);
    }

    /**
     * gets the entire backing arraylist
     * @return backingData
     */
    public ArrayList<Sighting> getBackingData(){
        return backingData;
    }
}


