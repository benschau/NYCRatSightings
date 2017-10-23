package cs2340.nycratsightings.model;

import android.os.Parcelable;
import android.os.Parcel;

public class Sighting implements Parcelable {
    private static final String TAG = "TAG";
    private String uniqueKey;
    private String creationDate;
    private String locationType;
    private String incidentZip;
    private String incidentAddress;
    private String city;
    private String borough;
    private String latitude;
    private String longitude;

    /**
     * Sole constructor for Sightings.
     */
    public Sighting(String[] entries) {
        this.uniqueKey = entries[0];
        this.creationDate = entries[1];
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

    public String getUniqueKey() {
        return uniqueKey;
    }
    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLocationType() {
        return locationType;
    }
    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getIncidentZip() {
        return incidentZip;
    }
    public void setIncidentZip(String incidentZip) {
        this.incidentZip = incidentZip;
    }

    public String getIncidentAddress() {
        return incidentAddress;
    }
    public void setIncidentAddress(String incidentAddress) {
        this.incidentAddress = incidentAddress;
    }

    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getBorough() {
        return borough;
    }
    public void setBorough(String borough) {
        this.borough = borough;
    }

    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
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
}
