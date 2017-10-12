package cs2340.nycratsightings;//import com.sun.deploy.util.SystemUtils;

import java.io.BufferedReader;
import java.io.FileReader;
//import java.io.InputStream;
import java.util.ArrayList;
public class SightingData {
    ArrayList<Sighting> backingData;
    boolean instantiated = false;

    /**
     * the constructor for the sightingdata takes in a FileReader object
     * which should be instantiated to the rat sighting data
     * @param in
     */
    public SightingData(FileReader in) {
        try {
            BufferedReader br = new BufferedReader(in);
            String line;
            br.readLine(); //delet hed
            backingData = new ArrayList<Sighting>();
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                Object[] entries = {Integer.parseInt(tokens[0]),tokens[1],tokens[7],tokens[8], tokens[9],
                    tokens[16], tokens[23], Float.parseFloat(tokens[49]), Float.parseFloat(tokens[50])};
                Sighting entry = new Sighting();
                backingData.add(entry);
            }
            br.close();
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
     * @return
     */
    public ArrayList<Sighting> getBackingdata(){
        return backingData;
    }
}


