package it.units.studenti.mattiabressan.examwebprogramming.rest.model;


//import javax.json.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import org.codehaus.jettison.json.JSONObject;
import java.sql.Date;

public class Trip {
    private Integer id;
    private String name;
    private Date tripDate;
    private String vehicle;
    private LinkedTreeMap path;
    private User user;

    public Trip() {
    }

    public Trip(String name, Date tripDate, String vehicle, LinkedTreeMap path) {
        this.name = name;
        this.tripDate = tripDate;
        this.vehicle = vehicle;
        this.path = path;
    }

    public Trip(String name, Date tripDate, String vehicle, LinkedTreeMap path, User user) {
        this.name = name;
        this.tripDate = tripDate;
        this.vehicle = vehicle;
        this.path = path;
        this.user = user;
    }

    public Trip(Integer id, String name, Date tripDate, String vehicle, LinkedTreeMap path,  User user) {
        this.id = id;
        this.name = name;
        this.tripDate = tripDate;
        this.vehicle = vehicle;
        this.path = path;
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
