package it.units.studenti.mattiabressan.examwebprogramming.rest.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name = "User.findAll",
                query = "SELECT b.email,b.firstname,b.lastname FROM User b"),
})
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "increment")
    private int id;
    @Column(name = "email")
    private String email;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "token")
    private String token;

//    @OneToMany(mappedBy = "user")
//    private List<Trip> trips = new ArrayList<Trip>();

    public String getRole() {
        return role;
    }

    public User() {
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
/*
    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }*/
}
