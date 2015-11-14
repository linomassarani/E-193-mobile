package org.sc.cbm.e193.beach.pojo;


import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class LifeguardTower {
    public enum Weather {CLEAN, CLOUDY, RAINY}
    public enum WindIntensity {LOW, MEDIUM, HIGH}
    public enum WindDirection {SOUTH, SOUTHEAST, SOUTHWEST, NORTH, NORTHWEST, NORTHEAST, EAST, WEST}
    public enum JellyFishAlert {ACTIVATED, DEACTIVATED}
    public enum HazardFlag {BLACK, GREEN, YELLOW, RED}

    private String city;
    private String beach;
    private LatLng latLng;
    private Drawable sea;
    private Drawable sand;
    private int lifeguardsNum;

    private Date lastModified;
    private HazardFlag hazardFlag;
    private Weather weather;
    private JellyFishAlert jellyFishAlert;

    public LifeguardTower(String city, String beach, LatLng latLng, Drawable sea, Drawable sand, int lifeguardsNum, Date lastModified, HazardFlag hazardFlag, Weather weather, JellyFishAlert jellyFishAlert) {
        this.city = city;
        this.beach = beach;
        this.latLng = latLng;
        this.sea = sea;
        this.sand = sand;
        this.lifeguardsNum = lifeguardsNum;
        this.lastModified = lastModified;
        this.hazardFlag = hazardFlag;
        this.weather = weather;
        this.jellyFishAlert = jellyFishAlert;
    }

    public static String windIntensityToPTBR(WindIntensity wi) {
        switch (wi) {
            case LOW:
                return "fraca";
            case MEDIUM:
                return "média";
            case HIGH:
                return "forte";
        }
        return null;
    }

    public static String windDirectionToPTBR(WindDirection wd) {
        switch (wd) {
            case SOUTH:
                return "sul";
            case SOUTHEAST:
                return "sudeste";
            case SOUTHWEST:
                return "sudoeste";
            case NORTH:
                return "norte";
            case NORTHWEST:
                return "noroeste";
            case NORTHEAST:
                return "nordeste";
            case EAST:
                return "leste";
            case WEST:
                return "oeste";
        }
        return null;
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

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public Drawable getSea() {
        return sea;
    }

    public void setSea(Drawable sea) {
        this.sea = sea;
    }

    public Drawable getSand() {
        return sand;
    }

    public void setSand(Drawable sand) {
        this.sand = sand;
    }

    public int getLifeguardsNum() {
        return lifeguardsNum;
    }

    public void setLifeguardsNum(int lifeguardsNum) {
        this.lifeguardsNum = lifeguardsNum;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public HazardFlag getHazardFlag() {
        return hazardFlag;
    }

    public void setHazardFlag(HazardFlag hazardFlag) {
        this.hazardFlag = hazardFlag;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public JellyFishAlert getJellyFishAlert() {
        return jellyFishAlert;
    }

    public void setJellyFishAlert(JellyFishAlert jellyFishAlert) {
        this.jellyFishAlert = jellyFishAlert;
    }
}
