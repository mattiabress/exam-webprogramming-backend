package it.units.studenti.mattiabressan.examwebprogramming.rest.restapi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/books")
public class BookResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {
        Response fdsdf = Response.ok("dsadsad").build();
        return fdsdf;

    }

}