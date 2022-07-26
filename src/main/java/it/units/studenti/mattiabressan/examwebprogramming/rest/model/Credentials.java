package it.units.studenti.mattiabressan.examwebprogramming.rest.model;

public class Credentials {
    private String mail;
    private String password;


    public Credentials(String mail, String password) {
        this.mail = mail;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getMail() {
        return mail;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
