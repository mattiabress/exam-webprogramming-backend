package it.units.studenti.mattiabressan.examwebprogramming.rest.model;


import java.sql.Date;

public class Trip {
    private Integer id;
    private String name;
    private Date tripDate;
    private String vehicle;
    private Object path;
    private String mainStages;
    private User user;

    public Trip() {
    }

    public Trip(String name, Date tripDate, String vehicle, String path, String mainStages) {
        this.name = name;
        this.tripDate = tripDate;
        this.vehicle = vehicle;
        this.path = path;
        this.mainStages = mainStages;
    }

    public Trip(String name, Date tripDate, String vehicle, String path, String mainStages, User user) {
        this.name = name;
        this.tripDate = tripDate;
        this.vehicle = vehicle;
        this.path = path;
        this.mainStages = mainStages;
        this.user = user;
    }

    public Trip(Integer id, String name, Date tripDate, String vehicle, String path, String mainStages, User user) {
        this.id = id;
        this.name = name;
        this.tripDate = tripDate;
        this.vehicle = vehicle;
        this.path = path;
        this.mainStages = mainStages;
        this.user = user;
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

    public Object getPath() {
        return path;
    }

    public Date getTripDate() {
        return tripDate;
    }

    public String getMainStages() {
        return mainStages;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
