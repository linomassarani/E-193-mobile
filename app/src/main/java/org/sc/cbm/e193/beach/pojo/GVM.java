package org.sc.cbm.e193.beach.pojo;

public class GVM {
    private String name;
    private String registration;
    private String rank;

    public GVM(String rank, String name, String registration) {
        this.rank = rank;
        this.name = name;
        this.registration = registration;
    }

    public GVM(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
