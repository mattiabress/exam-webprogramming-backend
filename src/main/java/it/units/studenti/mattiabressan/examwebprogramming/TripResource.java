package it.units.studenti.mattiabressan.examwebprogramming;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

@Path("/trips")
public class TripResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        Trip t1 = new Trip(1, "mario");
        List<Trip> trips=new ArrayList<Trip>();
        trips.add(t1);
        trips.add(new Trip(3, "pippo"));
        Gson gson = new Gson();
        return Response.ok(gson.toJson(trips)).build();

    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("id") int id) {
        return Response.ok( new Trip(1, "mario")).build();

    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setBook(String bookJsonString){
        //Gson gson = new Gson();
        //Book book = gson.fromJson(bookJsonString, Book.class);
        //MongoDBWrapper.getInstance().addBook(book);
        //return Response.status(201).entity(gson.toJson(book)).build();
        return Response.ok("sda").build();
    }

}
