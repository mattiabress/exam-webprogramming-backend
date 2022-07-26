package it.units.studenti.mattiabressan.examwebprogramming.rest.restapi;

import com.google.gson.Gson;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.PersistenceManager;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.UserDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.Credentials;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.User;
import it.units.studenti.mattiabressan.examwebprogramming.rest.security.PasswordSecurity;
import jakarta.persistence.EntityManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/user")
public class UserResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser() {
        Gson gson = new Gson();
        EntityManager em = PersistenceManager.getEntityManager();
        UserDAO userDAO = new UserDAO(em);
        return Response.ok(gson.toJson(userDAO.findAll())).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") int id) {
        EntityManager em = PersistenceManager.getEntityManager();
        UserDAO userDAO = new UserDAO(em);
        // Search for a trip by ID
        Optional<User> user = userDAO.findById(id);
        if (user.isPresent())
            return Response.ok(user.get()).build();
        return Response.status(404).build(); //not found
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createUser(String userJsonString) { //create new user
        Gson gson = new Gson();
        User user = gson.fromJson(userJsonString, User.class); //check if is correct
        EntityManager em = PersistenceManager.getEntityManager();
        UserDAO userDAO = new UserDAO(em);
        userDAO.save(user);
        return Response.status(201).entity(gson.toJson(user)).build();
    }

    @POST
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(String userLoginJsonString) { //create new user
        Gson gson = new Gson();
        Credentials credentials = gson.fromJson(userLoginJsonString, Credentials.class); //check if is correct
        EntityManager em = PersistenceManager.getEntityManager();
        UserDAO userDAO=new UserDAO(em);
    }

//
//    @PUT
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response setUser(String userJsonString) { //create new trip
//        System.out.println(userJsonString);
//        Gson gson = new Gson();
//        User user = gson.fromJson(userJsonString, User.class); //check if is correct
//        EntityManager em = PersistenceManager.getEntityManager();
//        UserDAO userDAO = new UserDAO(em);
//
//        return Response.status(201).entity(gson.toJson(user)).build();
//    }
//
//    @DELETE
//    @Path("/{id}")
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public Response deleteTrip(String tripJsonString) { //create new trip
//        System.out.println(tripJsonString);
//        Gson gson = new Gson();
//        Trip trip = gson.fromJson(tripJsonString, Trip.class); //check if is correct
//        EntityManager em = PersistenceManager.getEntityManager();
//        TripDAO tripDAO = new TripDAO(em);
//        tripDAO.save(trip);
//        return Response.status(201).entity(gson.toJson(trip)).build();
//    }

}
