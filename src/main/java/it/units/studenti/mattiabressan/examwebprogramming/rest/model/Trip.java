package it.units.studenti.mattiabressan.examwebprogramming.rest.model;

import org.codehaus.jettison.json.JSONObject;

public class Trip {
    private Integer id;
    private String name;
    private String vehicle;
    private String patch; // TODO JSONObject
    private String mainStages;
    private User user;

    public Trip(){}
    public Trip(String name, String vehicle,String patch, String mainStages,User user){
        this.name=name;
        this.vehicle=vehicle;
        this.patch=patch;
        this.mainStages=mainStages;
        this.user=user;
    }
    public Trip(Integer id, String name, String vehicle,String patch, String mainStages,User user){
        this.id=id;
        this.name=name;
        this.vehicle=vehicle;
        this.patch=patch;
        this.mainStages=mainStages;
        this.user=user;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getPatch() {
        return patch;
    }

    public String getMainStages() {
        return mainStages;
    }

    public User getUser() {
        return user;
    }


}
