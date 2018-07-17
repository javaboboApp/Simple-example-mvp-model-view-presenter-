package com.javabobo.projectdemo.Models;

/**
 * Created by luis on 14/02/2018.
 */

public class CurrentLocation {
   private double lat,lon;

    public CurrentLocation(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
