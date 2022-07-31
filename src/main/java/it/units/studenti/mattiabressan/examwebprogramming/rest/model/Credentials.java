package it.units.studenti.mattiabressan.examwebprogramming.rest.model;

public class Credentials {
    private String username = null;
    private String password = null;

    public Credentials() {}

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
