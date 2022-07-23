package it.units.studenti.mattiabressan.examwebprogramming.rest.model;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "trip")
@NamedQueries({
        @NamedQuery(name = "Trip.findByName",
                query = "SELECT b FROM Trip b WHERE b.name = :name"),
        @NamedQuery(name = "Trip.findAll",
                query = "SELECT b FROM Trip b")
})
public class Trip {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    private int id;
    @Column(name = "trip_date")
    private Date tripDate;
    @Column(name = "name")
    private String name;
    @Column(name = "vehicle")
    private String vehicle;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

//    public User getUser() {
//        return user;
//    }

//    public void setUser(User user) {
//        this.user = user;
//    }


//    @Type(type = "json")


//    @Column(name = "route",columnDefinition = "json")
//    private String route;
//    @Type(type = "json")
//    @Column(name = "main_stages",columnDefinition = "json")
//    private String mainStages;

    public Trip() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return id + " " + name;
    }
}
