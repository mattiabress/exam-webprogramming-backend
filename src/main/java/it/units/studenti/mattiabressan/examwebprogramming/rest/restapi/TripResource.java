package it.units.studenti.mattiabressan.examwebprogramming.rest.restapi;


import com.google.gson.Gson;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.PersistenceManager;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.Trip;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.TripDAO;

import jakarta.persistence.EntityManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/trips")
public class TripResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrips() {
        Gson gson = new Gson();
        EntityManager em = PersistenceManager.getEntityManager();
        TripDAO tripDAO = new TripDAO(em);
        List<Trip> trips =tripDAO.findAll();
        return Response.ok(gson.toJson(trips)).build();
    }
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrip(@PathParam("id") int id) {
        EntityManager em = PersistenceManager.getEntityManager();
        TripDAO tripDAO = new TripDAO(em);
        // Search for a trip by ID
        Optional<Trip> trip = tripDAO.findById(id);
        if(trip.isPresent())
            return Response.ok(trip.get()).build();
        return Response.status(404).build(); //not found
    }
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTrip(String tripJsonString) { //create new trip
        System.out.println(tripJsonString);
        Gson gson = new Gson();
        Trip trip = gson.fromJson(tripJsonString, Trip.class); //check if is correct
        EntityManager em = PersistenceManager.getEntityManager();
        TripDAO tripDAO=new TripDAO(em);
        tripDAO.save(trip);
        return Response.status(201).entity(gson.toJson(trip)).build();
    }
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setTrip(String tripJsonString) { //create new trip
        System.out.println(tripJsonString);
        Gson gson = new Gson();
        Trip trip = gson.fromJson(tripJsonString, Trip.class); //check if is correct
        EntityManager em = PersistenceManager.getEntityManager();
        TripDAO tripDAO=new TripDAO(em);
        tripDAO.save(trip);
        return Response.status(201).entity(gson.toJson(trip)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteTrip(String tripJsonString) { //create new trip
        System.out.println(tripJsonString);
        Gson gson = new Gson();
        Trip trip = gson.fromJson(tripJsonString, Trip.class); //check if is correct
        EntityManager em = PersistenceManager.getEntityManager();
        TripDAO tripDAO=new TripDAO(em);
        tripDAO.save(trip);
        return Response.status(201).entity(gson.toJson(trip)).build();
    }

}
