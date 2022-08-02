package it.units.studenti.mattiabressan.examwebprogramming.rest.restapi;


import com.google.gson.Gson;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.TripDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.TripDAOFactory;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.Trip;
import org.glassfish.jersey.server.ResourceConfig;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@DeclareRoles({"admin", "user", "guest"})
@Path("/trip")
public class TripResource extends ResourceConfig {

    @GET
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrips(@Context HttpHeaders headers) {
        Gson gson = new Gson();
        // return only user's trips or if he is a admin all trips
        Integer userId = RequestUtility.getIdFromHeaders(headers);
        String role = RequestUtility.getRoleFromHeaders(headers);
        TripDAO tripDAO = TripDAOFactory.getTripDAO();
        List<Trip> trips;
        if(role.equals("admin"))
            trips = tripDAO.findAll();
        else // user
            trips = tripDAO.findAllByUserId(userId);

        return Response.ok(gson.toJson(trips)).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrip(@PathParam("id") int id) {
        TripDAO tripDAO = TripDAOFactory.getTripDAO();
        // Search for a trip by ID
        Optional<Trip> trip = tripDAO.findById(id);
        if (trip.isPresent())
            return Response.ok(trip.get()).build();
        return Response.status(404).build(); //not found
    }

    /*
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTrip(String tripJsonString) { //create new trip
        System.out.println(tripJsonString);
        Gson gson = new Gson();
        Trip trip = gson.fromJson(tripJsonString, Trip.class); //check if is correct
        TripDAO tripDAO=TripDAOFactory.getTripDAO();
        tripDAO.save(trip);
        return Response.status(201).entity(gson.toJson(trip)).build();
    }*/
    /*
    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setTrip(String tripJsonString) { //create new trip
        System.out.println(tripJsonString);
        Gson gson = new Gson();
        Trip trip = gson.fromJson(tripJsonString, Trip.class); //check if is correct
        TripDAO tripDAO=TripDAOFactory.getTripDAO();
        tripDAO.save(trip);
        return Response.status(201).entity(gson.toJson(trip)).build();
    }*/
/*
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteTrip(String tripJsonString) { //create new trip
        System.out.println(tripJsonString);
        Gson gson = new Gson();
        Trip trip = gson.fromJson(tripJsonString, Trip.class); //check if is correct
        //EntityManager em = PersistenceManager.getEntityManager();
        TripDAO tripDAO=TripDAOFactory.getTripDAO();
        tripDAO.save(trip);
        return Response.status(201).entity(gson.toJson(trip)).build();
    }*/

}
