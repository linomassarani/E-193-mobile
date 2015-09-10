package org.sc.cbm.e193.beach.pojo;

import java.util.Date;

public class HazardFlag {
    public static final int BLACK = 1;
    public static final int GREEN = 2;
    public static final int YELLOW = 3;
    public static final int RED = 4;

    private int color;
    private Date lastModified;
    private String city;
    private String beach;
    private String lifeguardPost;
    //who edit last time
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Date getLastModified() {
        return lastModified;
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
