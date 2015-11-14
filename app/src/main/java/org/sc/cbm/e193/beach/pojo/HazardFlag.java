package org.sc.cbm.e193.beach.pojo;

import java.util.Date;

/**
 * Use LifeguardTower instead
 */
@Deprecated
public class HazardFlag {
    public enum Color {BLACK, GREEN, YELLOW, RED}

    private Date lastModified;
    private String city;
    private String beach;
    private String lifeguardPost;
    private String latitude;
    private String longitude;
    private Color color;
    //who edit last time

    public HazardFlag(Color color, Date lastModified, String city, String beach, String lifeguardPost, String latitude, String longitude, String owner) {
        this.color = color;
        this.lastModified = lastModified;
        this.city = city;
        this.beach = beach;
        this.lifeguardPost = lifeguardPost;
        this.latitude = latitude;
        this.longitude = longitude;
        this.owner = owner;
    }

    private String owner;

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public String getLastModifiedString() {
        int month = getLastModified().getMonth();
        int day = getLastModified().getDay();
        int year = getLastModified().getYear();
        int hour = getLastModified().getHours();
        int minute = getLastModified().getMinutes();

        String month_zero = "";
        String day_zero = "";
        String minute_zero = "";
        String hour_zero = "";

        if(month < 10)
            month_zero = "0";
        if(day < 10)
            day_zero = "0";
        if(hour < 10)
            hour_zero = "0";
        if(minute < 10)
            minute_zero = "0";

        return day_zero + String.valueOf(day)
                + "/" + month_zero
                + String.valueOf(month)
                + "/"
                + String.valueOf(year)
                + " às "
                + hour_zero + String.valueOf(hour)
                + ":"
                + minute_zero + String.valueOf(minute);
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBeach() {
        return beach;
    }

    public void setBeach(String beach) {
        this.beach = beach;
    }

    public String getLifeguardPost() {
        return lifeguardPost;
    }

    public void setLifeguardPost(String lifeguardPost) {
        this.lifeguardPost = lifeguardPost;
    }
}
