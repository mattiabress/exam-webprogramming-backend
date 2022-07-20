package it.units;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sound.sampled.SourceDataLine;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

@Path("/books")
public class BookResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {

        //MongoDBWrapper mongo = MongoDBWrapper.getInstance();
        //List<String> books = mongo.getBooks();
        List<String> books = new ArrayList<String>();
        books.add("ciao ciao");
        StringBuilder sb = new StringBuilder("[");
        String prefix = "";
        for(String book:books){
            sb.append(prefix);
            prefix=",";
            sb.append(book);
        }
        sb.append("]");

        return Response.ok(sb.toString()).build();

    }
/*
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("id") int id) {
        //MongoDBWrapper mongo = MongoDBWrapper.getInstance();
        //return Response.ok(mongo.getBook(id)).build();
        return Response.ok("sda").build();

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
    }*/
}