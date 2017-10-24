package cs2340.nycratsightings.model;

import java.io.BufferedReader;
import java.util.ArrayList;

/**
 * This class manages the list of sightings in the model
 */
public class SightingData {
    ArrayList<Sighting> backingData = new ArrayList<Sighting>();

    /**
     * Read sighting data from csv file and add it to backing array
     *
     * @param in file to read from
     */
    public void readCSV(BufferedReader in) {
        try {
            String line;
            in.readLine(); //skip over header
            while ((line = in.readLine()) != null) {
                String[] tokens = line.split(",");
                String[] entries = {tokens[0],tokens[1],tokens[7],tokens[8], tokens[9],
                    tokens[16], tokens[23], tokens[49], tokens[50]};
                Sighting entry = new Sighting(entries);
                backingData.add(entry);
            }
            in.close();
        } catch (Exception e) {
            System.out.println(e);
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


