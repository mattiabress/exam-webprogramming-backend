package it.units.studenti.mattiabressan.examwebprogramming.rest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "Trip.findAll",
                query = "SELECT b.email,b.firstname,b.lastname FROM Trip b"),
})
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    private int id;
    @Column(name = "email")
    private String email = null;
    @Column(name = "firstname")
    private String firstname = null;
    @Column(name = "lastname")
    private String lastname = null;
    @Column(name = "password")
    private String password = null;

    public User() {
    }

    public User(String email, String firstname, String lastname, String password) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }

    public User(int id, String email, String firstname, String lastname, String password) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }
}
