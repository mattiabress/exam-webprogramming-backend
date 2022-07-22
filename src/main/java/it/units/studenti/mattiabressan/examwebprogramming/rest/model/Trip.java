package it.units.studenti.mattiabressan.examwebprogramming.rest.model;


import jakarta.persistence.*;

//import org.hibernate.annotations.GenericGenerator;
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
    //@GenericGenerator(name="increment", strategy = "increment")
    private int id;
    @Column(name = "name")
    private String name;

    public Trip() {

    }

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

    @Override
    public String toString() {
        return id + " " + name;
    }
}
