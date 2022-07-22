package it.units.studenti.mattiabressan.examwebprogramming.rest.restapi;


import com.google.gson.Gson;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.PersistenceManager;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.Trip;
import jakarta.persistence.EntityManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
@Path("/trips")
public class TripResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrips() {
        Gson gson = new Gson();
        EntityManager em = PersistenceManager.getEntityManager();
        List<Trip> trips = em.createQuery("SELECT b FROM Trip b ",Trip.class).getResultList();
        return Response.ok(gson.toJson(trips)).build();

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrip(@PathParam("id") int id) {
        EntityManager em = PersistenceManager.getEntityManager();
        List<Trip> trip = em.createQuery("SELECT b FROM Trip b WHERE b.id=1 ",Trip.class).getResultList();
        return Response.ok(trip).build();
    }
/*
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setTrip(String bookJsonString) {
        //Gson gson = new Gson();
        //Book book = gson.fromJson(bookJsonString, Book.class);
        //MongoDBWrapper.getInstance().addBook(book);
        //return Response.status(201).entity(gson.toJson(book)).build();
        return Response.ok("sda").build();
    }
*/
}
