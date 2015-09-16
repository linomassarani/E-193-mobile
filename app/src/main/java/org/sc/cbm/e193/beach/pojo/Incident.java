package org.sc.cbm.e193.beach.pojo;

import java.sql.Date;
import java.util.List;

public class Incident {
    public enum Type {HAUL, RECOVERED_DROWNING, DEAD_DROWNING, INJURY_CUT, INSOLATION, SUNRAYS_BURN, VESSEL_ADRIFT, BOAT_SINKING};
    public enum Service {ACTIVATED, DISABLED, ABSENT};
    public enum Location {BEFORE_SURF_ZONE, ON_SURF_ZONE, AFTER_SURF_ZONE, SHORE, NO_SURF_ZONE};
    public enum LocalSignaling {RED, RED_WITH_TAPE, BOARD, OTHER_SIGNALING, NO_SIGNALING};
    public enum RelatedHazards {RETURN_CURRENT, LONGITUDINAL_CURRENT, RIVERS_LAKE_MOUTH, NEXT_TO_HARD_STRUCTURES, NEXT_TO_ROCK_SHORE, OTHERS, NONE};
    public enum Conduction {HELICOPTER, CBMSC_VEHICLE, OTHERS_AGENCIES_AMBULANCES, OTHERS_VEHICLES, NOT_CONDUCTED};
    public enum Sky {CLEAN, WITH_CLOUDS, CLOUDY, RAINY};
    public enum WindStrength {NONE, LOW, MODERATED, STRENGTH, VERY_STRENGTH};
    public enum WindDirection {EAST, WEST, NORTHWEST, NORTHEAST, NORTH, SOUTHEAST, SOUTHWEST, SOUTH};
    public enum WaveHeight {TO_HALF, HALF_TO_ONE, ONE_TO_ONE_HALF, ONE_HALF_TO_TWO, ABOVE_TWO};
    public enum Surf {BOX, SLIDING, NONE};
    public enum CurrentType {NONE, RETURNING_CURRENT, LONGITUDINAL_TO_RIGHT, LONGITUDINAL_TO_LEFT};
    public enum CurrentStrength {WEAK, MODERATED, STRENGTH, NONE};
    public enum BeachShape {FLAT, INTERMEDIATE, FALL_BEACH};
    public enum PeoplePerKm {TO_500, FROM_501_TO_1000, FROM_1001_TO_1500, FROM_1501_TO_2000,
        FROM_2001_TO_2500, FROM_2501_TO_3000, FROM_3001_TO_3500, ABOVE_3501};
    public enum UsedEquipments {LIFEBELT, BOARD, WATER_BIKE, LAUNCH, FLOAT, HELICOPTER, OTHERS}

    private Date date;
    private String city;
    private String beach;
    private String lifeguardPost;
    private Victim victim;
    private List<GVC> gvcs;
    private List<GVM> gvms;
    private String historic;
    private List<String> picturesURLs;
    private boolean isSaltWater;
    private boolean atLeft;
    private int lifeguardPostDistance;
    private Type type;
    private Service service;
    private List<UsedEquipments> usedEquipments;
    private boolean isInsidePatrolArea;
    private HazardFlag.Color hazardFlagColor;
    private int waterTemp;
    private Location location;
    private LocalSignaling localSignaling;
    private RelatedHazards relatedHazards;
    private Conduction conduction;
    private Sky sky;
    private WindStrength windStrength;
    private WindDirection windDirection;
    private WaveHeight waveHeight;
    private Surf surf;
    private CurrentType currentType;
    private CurrentStrength currentStrength;
    private BeachShape beachShape;
    private PeoplePerKm peoplePerKm;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Victim getVictim() {
        return victim;
    }

    public void setVictim(Victim victim) {
        this.victim = victim;
    }

    public List<GVC> getGvcs() {
        return gvcs;
    }

    public void setGvcs(List<GVC> gvcs) {
        this.gvcs = gvcs;
    }

    public List<GVM> getGvms() {
        return gvms;
    }

    public void setGvms(List<GVM> gvms) {
        this.gvms = gvms;
    }

    public String getHistoric() {
        return historic;
    }

    public void setHistoric(String historic) {
        this.historic = historic;
    }

    public List<String> getPicturesURLs() {
        return picturesURLs;
    }

    public void setPicturesURLs(List<String> picturesURLs) {
        this.picturesURLs = picturesURLs;
    }

    public boolean isSaltWater() {
        return isSaltWater;
    }

    public void setSaltWater(boolean isSaltWater) {
        this.isSaltWater = isSaltWater;
    }

    public boolean isAtLeft() {
        return atLeft;
    }

    public void setAtLeft(boolean atLeft) {
        this.atLeft = atLeft;
    }

    public int getLifeguardPostDistance() {
        return lifeguardPostDistance;
    }

    public void setLifeguardPostDistance(int lifeguardPostDistance) {
        this.lifeguardPostDistance = lifeguardPostDistance;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public List<UsedEquipments> getUsedEquipments() {
        return usedEquipments;
    }

    public void setUsedEquipments(List<UsedEquipments> usedEquipments) {
        this.usedEquipments = usedEquipments;
    }

    public boolean isInsidePatrolArea() {
        return isInsidePatrolArea;
    }

    public void setInsidePatrolArea(boolean isInsidePatrolArea) {
        this.isInsidePatrolArea = isInsidePatrolArea;
    }

    public HazardFlag.Color getHazardFlagColor() {
        return hazardFlagColor;
    }

    public void setHazardFlagColor(HazardFlag.Color hazardFlagColor) {
        this.hazardFlagColor = hazardFlagColor;
    }

    public int getWaterTemp() {
        return waterTemp;
    }

    public void setWaterTemp(int waterTemp) {
        this.waterTemp = waterTemp;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalSignaling getLocalSignaling() {
        return localSignaling;
    }

    public void setLocalSignaling(LocalSignaling localSignaling) {
        this.localSignaling = localSignaling;
    }
}
