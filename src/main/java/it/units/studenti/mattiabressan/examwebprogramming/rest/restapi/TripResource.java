package it.units.studenti.mattiabressan.examwebprogramming.rest.restapi;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.PersistenceManager;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.Trip;
//import it.units.studenti.mattiabressan.examwebprogramming.rest.model.TripDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
//import java.util.ArrayList;
import java.util.List;
@Path("/trips")
public class TripResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrips() {
    /*
        System.out.println("I am here");
        Trip t1 = new Trip(1, "mario");

        /*List<Trip> trips=new ArrayList<Trip>();
        trips.add(t1);
        trips.add(new Trip(3, "pippo"));

        //entityManager.persist(phone);
            */
        Gson gson = new Gson();
        EntityManager entityManager = PersistenceManager.getEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Trip> criteriaQuery = criteriaBuilder.createQuery(Trip.class);
        List<Trip> trips = entityManager.createQuery(criteriaQuery).getResultList();
        for (Trip trip : trips) {
            System.out.println(trip);
        }

        return Response.ok(gson.toJson(trips)).build();

    }
/*
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrip(@PathParam("id") int id) {
        return Response.ok(new Trip(1, "mario")).build();

    }

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
