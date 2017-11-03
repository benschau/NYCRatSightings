package cs2340.nycratsightings.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.TreeMap;

/**
 * Created by hwdge on 11/2/2017.
 */

public class Grapher {
    int monthsToTraverse;
    public TreeMap<String,Integer> map;
    Hashtable<String[],Integer> backing = new Hashtable<>();
    public static void main(String[] args){
        String[] data = {"dank","1/2/2015 11:58","thre","thre","thre","thre","thre","thre","thre"};
        String[] data2 = {"dank","10/8/2015 11:58","thre","thre","thre","thre","thre","thre","thre"};
        String[] dat3 = {"dank","10/8/2015 11:58","thre","thre","thre","thre","thre","thre","thre"};
        Sighting tester = new Sighting(data);
        Sighting tester2 = new Sighting(data2);
        Sighting tester3 = new Sighting(dat3);
        Calendar dank = tester.parseCreationDate();
        Calendar dank2 = tester2.parseCreationDate();
        ArrayList<Sighting> testSightings = new ArrayList<>();
        testSightings.add(tester);
        testSightings.add(tester2);
        testSightings.add(tester3);
        Integer[] start = {10,2015};
        Integer[] end = {1,2015};
        TreeMap<Integer[],Integer> test = new TreeMap<>();
        //test.put(start,1);

        //System.out.println(dank.getTime());

        Grapher thedank = new Grapher(start,end,testSightings);
        System.out.println(thedank.map.entrySet());

    }
    public Grapher(Integer[] start, Integer[] end, ArrayList<Sighting> data){
        //Format = [month] [date] for parameters
        map = new TreeMap<>();
        // minus one for inclusivity
        DateRange daterange = new DateRange(end[1],end[0] - 1,1,start[1],start[0],1);
        boolean sent = true;
        int i = data.size() - 1;

        while(sent){
            Sighting traverse = data.get(i);
            if(daterange.inRange(traverse.parseCreationDate())){
                System.out.println(traverse);
                if(map.containsKey(getKey(traverse))){
                    map.put(getKey(traverse),map.get(getKey(traverse)) + 1);
                } else {
                    map.put(getKey(traverse),1);
                }
            }
            if(i == 0){
                sent = false;
            }
            i--;
        }

    }
    public static String getKey(Sighting data){
        String date = data.getCreationDate();
        String[] elements = date.split(" ");
        String[] dateElements = elements[0].split("/");
        String out = dateElements[0] + " " + dateElements[2];
        return out;
    }
}
