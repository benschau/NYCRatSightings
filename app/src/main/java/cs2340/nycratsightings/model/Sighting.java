package cs2340.nycratsightings.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

/** Represents a Sighting.
 * @author Lucas
 * @version 1.0
 */
@IgnoreExtraProperties
public class Sighting implements Parcelable, Comparable<Sighting> {
    public  String uniqueKey;
    public  String creationDate;
    public  String locationType;
    public  String incidentZip;
    public  String incidentAddress;
    public  String city;
    public  String borough;
    public  String latitude;
    public  String longitude;

    //to test
    public boolean nullEntries = false;

    /**
     * Constructor required for Firebase encoding into database
     */
    public Sighting() {

    }

    /**
     * Typical constructor for Sightings.
     */
    public Sighting(String[] entries) {
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] == null) {
                nullEntries = true;
                break;
            }
        }
        this.uniqueKey = entries[0];
        this.creationDate = entries[1]; // mon/day/yr time
        this.locationType = entries[2];
        this.incidentZip = entries[3];
        this.incidentAddress = entries[4];
        this.city = entries[5];
        this.borough = entries[6];
        this.latitude = entries[7];
        this.longitude = entries[8];
    }

    private Sighting(Parcel in){
        this.uniqueKey = in.readString();
        this.creationDate = in.readString();
        this.locationType = in.readString();
        this.incidentZip = in.readString();
        this.incidentAddress = in.readString();
        this.city = in.readString();
        this.borough = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
    }

    /**
     * Gets the unique key.
     * @return the unique key
     */
    public String getUniqueKey() {
        return uniqueKey;
    }

    /**
     * Gets the creation date of the sighting.
     * @return creationDate
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * Parses the creation date.
     * @return the calendar object
     */
    public Calendar parseCreationDate() {
        int end = this.creationDate.indexOf(' ');
        String date = this.creationDate.substring(0, end);
        String[] split = date.split("/");

        Calendar createDate = new GregorianCalendar(Integer.parseInt(split[2]),
                Integer.parseInt(split[0]),
                Integer.parseInt(split[1]));


        createDate.set(Calendar.HOUR, 12);
        createDate.set(Calendar.MINUTE, 0);
        createDate.set(Calendar.SECOND, 0);
        createDate.set(Calendar.MILLISECOND, 0);

        return createDate;
    }


    /**
     * Gets the location type.
     * @return the location type
     */
    public String getLocationType() {
        return locationType;
    }

    /**
     * Gets the zip.
     * @return the zip
     */
    public String getIncidentZip() {
        return incidentZip;
    }

    /**
     * Gets the address.
     * @return the address
     */
    public String getIncidentAddress() {
        return incidentAddress;
    }

    /**
     * Gets the city.
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     * @return the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the borough.
     * @return the borough
     */
    public String getBorough() {
        return borough;
    }

    /**
     * Sets the borough.
     * @return the borough
     */
    public void setBorough(String borough) {
        this.borough = borough;
    }

    /**
     * Gets the latitude.
     * @return the latitude
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * Gets the longitude.
     * @return the longitude
     */
    public String getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Sighting{" +
                "uniqueKey='" + uniqueKey + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", locationType='" + locationType + '\'' +
                ", incidentZip='" + incidentZip + '\'' +
                ", incidentAddress='" + incidentAddress + '\'' +
                ", city='" + city + '\'' +
                ", borough='" + borough + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(uniqueKey);
        out.writeString(creationDate);
        out.writeString(locationType);
        out.writeString(incidentZip);
        out.writeString(incidentAddress);
        out.writeString(city);
        out.writeString(borough);
        out.writeString(latitude);
        out.writeString(longitude);
    }

    @Override
    public int describeContents() { return 0; }

    public static final Parcelable.Creator<Sighting> CREATOR
            = new Parcelable.Creator<Sighting>() {
        public Sighting createFromParcel(Parcel in) {
            return new Sighting(in);
        }

        public Sighting[] newArray(int size) {
            return new Sighting[size];
        }
    };

    @Override
    public int compareTo(Sighting other) {
        return parseCreationDate().compareTo(other.parseCreationDate());
    }
}
