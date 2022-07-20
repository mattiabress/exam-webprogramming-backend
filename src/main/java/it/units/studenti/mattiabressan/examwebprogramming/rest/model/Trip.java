package it.units.studenti.mattiabressan.examwebprogramming.rest.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="trip")
public class Trip {
    @Id
    @Column(name="id")
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
