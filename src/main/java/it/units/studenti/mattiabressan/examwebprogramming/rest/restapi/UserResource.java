package it.units.studenti.mattiabressan.examwebprogramming.rest.restapi;

import com.google.gson.Gson;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.PersistenceManager;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.UserDAO;
import jakarta.persistence.EntityManager;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
}
