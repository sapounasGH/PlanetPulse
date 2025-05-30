package com.example.planetpulse;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.GeoPoint;

public class drasiCLASS {
    Timestamp dateTime;
    GeoPoint mapsPlace;

    String name;
    String place;

    public drasiCLASS(Timestamp dateTime, GeoPoint mapsPlace, String name, String place) {
        this.dateTime = dateTime;
        this.mapsPlace= mapsPlace;
        this.name = name;
        this.place = place;
    }

    @Override
    public String toString() {
        return "drasiCLASS{" +
                "dateTime='" + dateTime + '\'' +
                ", coordinates='" + mapsPlace + '\'' +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                '}';
    }

    public drasiCLASS() {
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public GeoPoint getmapsPlace() {
        return mapsPlace;
    }

    public void setmapsPlace(GeoPoint coordinates) {
        this.mapsPlace = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
