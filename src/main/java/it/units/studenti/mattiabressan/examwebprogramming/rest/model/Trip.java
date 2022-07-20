package it.units.studenti.mattiabressan.examwebprogramming.rest.model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;


import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
//import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="trip")
public class Trip {
    @Id
    @Column(name="id")
    @GeneratedValue(generator="increment")
    //@GenericGenerator(name="increment", strategy = "increment")
    private final int id;
    @Column(name="name")
    private final String name;

    public Trip(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
