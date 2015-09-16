package org.sc.cbm.e193.beach.pojo;

public class Victim {
    private enum Type {BATHER, SURFER, OTHER};
    private enum Skill {NOT_DETERMINED, NO_SWIMMING_SKILL, LOW_SWIMMING_SKILL, HIGH_SWIMMING_SKILL}
    private enum DrugsUse {ALCOHOL, OTHERS_DRUGS, NONE, NOT_DETERMINED}
    private enum Behavior {KEPT_CALM, SPIRALED_OUT, FAINTING}
    private enum RelatedInjuries {NONE, THERMAL_SHOCK, CUT, RESPIRATORY_FAILURE, CRAMP, OTHERS}
    private enum Approach {MET_INSTRUCTIONS, TRIED_GRAB};
    public enum Familiarity {OCCASIONAL_VISITOR, VACATIONER, DWELLER};
    public enum DrowningDegree {ONE, TWO, THREE, FOUR, FIVE, SIX};

    private String name;
    private int age;
    //true is male, false is not
    private boolean male;
    //true is foreign, false is not
    private boolean foreign;
    private String country;
    private String state;
    private String city;
    private String address;
    private Type type;
    private Skill skill;
    private DrugsUse drugsUse;
    private Behavior behavior;
    private RelatedInjuries relatedInjuries;
    private Approach approach;
    private Familiarity familiarity;
    private DrowningDegree drowningDegree;

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public DrugsUse getDrugsUse() {
        return drugsUse;
    }

    public void setDrugsUse(DrugsUse drugsUse) {
        this.drugsUse = drugsUse;
    }

    public Behavior getBehavior() {
        return behavior;
    }

    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    public RelatedInjuries getRelatedInjuries() {
        return relatedInjuries;
    }

    public void setRelatedInjuries(RelatedInjuries relatedInjuries) {
        this.relatedInjuries = relatedInjuries;
    }

    public Approach getApproach() {
        return approach;
    }

    public void setApproach(Approach approach) {
        this.approach = approach;
    }

    public Familiarity getFamiliarity() {
        return familiarity;
    }

    public void setFamiliarity(Familiarity familiarity) {
        this.familiarity = familiarity;
    }

    public DrowningDegree getDrowningDegree() {
        return drowningDegree;
    }

    public void setDrowningDegree(DrowningDegree drowningDegree) {
        this.drowningDegree = drowningDegree;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public boolean isForeign() {
        return foreign;
    }

    public void setForeign(boolean foreign) {
        this.foreign = foreign;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
