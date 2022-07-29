package it.units.studenti.mattiabressan.examwebprogramming.rest.model;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class User implements JsonSerializable {

    // global unique identifier
    private String id = null;
    private String email = null;
    private String firstname = null;
    private String lastname = null;

    private String username=null;

    public User() {}

    public User( String email, String firstname, String lastname, String username ) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username=username;
    }

    public User( String id, String email, String firstname, String lastname ,String username) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username=username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", firstname=" + firstname + ", lastname=" + lastname + "]";
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put( "email", this.email );
        jsonObject.put( "firstname", this.firstname );
        jsonObject.put( "lastname", this.lastname );
        return jsonObject;
    }

    public String getUsername() {
        return username;
    }
}
