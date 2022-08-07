package it.units.studenti.mattiabressan.examwebprogramming.rest.restapi;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.TripDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.TripDAOFactory;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.UserDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.UserDAOFactory;
import it.units.studenti.mattiabressan.examwebprogramming.rest.exception.TripNotFoundException;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.Trip;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.User;
import org.glassfish.jersey.server.ResourceConfig;

import javax.annotation.security.DeclareRoles;
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
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        // return only user's trips or if he is a admin all trips
        Integer userId = RequestUtility.getIdFromHeaders(headers);
        String role = RequestUtility.getRoleFromHeaders(headers);
        TripDAO tripDAO = TripDAOFactory.getTripDAO();
        List<Trip> trips;
        if (role.equals("admin"))
            trips = tripDAO.findAll();
        else // user
            trips = tripDAO.findAllByUserId(userId);

        return Response.ok(gson.toJson(trips)).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrip(@Context HttpHeaders headers, @PathParam("id") int id) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        TripDAO tripDAO = TripDAOFactory.getTripDAO();
        Integer userId = RequestUtility.getIdFromHeaders(headers);
        String role = RequestUtility.getRoleFromHeaders(headers);
        try {
            // Search for a trip by ID
            Optional<Trip> optionalTrip = tripDAO.findById(id);
            if (optionalTrip.isPresent()) {
                Trip trip = optionalTrip.get();
                if (role.equals("admin") || trip.getUser().getId() == userId)
                    return Response.ok(gson.toJson(optionalTrip.get()) ).build();
                else
                    return ResponseBuilder.createResponse(Response.Status.UNAUTHORIZED);
            } else
                throw new TripNotFoundException(id);

        } catch (TripNotFoundException e) {
            return ResponseBuilder.createResponse(Response.Status.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.createResponse(Response.Status.UNAUTHORIZED);
        }

    }


    @POST
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTrip(@Context HttpHeaders headers, String tripJsonString) { //create new trip
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create(); //TODO sistemare in String
        Trip trip = gson.fromJson(tripJsonString, Trip.class);
        Integer userId = RequestUtility.getIdFromHeaders(headers);
        TripDAO tripDAO = TripDAOFactory.getTripDAO();
        UserDAO userDAO = UserDAOFactory.getUserDAO();
        User user = userDAO.findById(userId).get();
        trip.setUser(user);
        //tripDAO.save(trip);
        tripDAO.createTrip(trip);
        return Response.status(201).entity(gson.toJson(trip)).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setTrip(String tripJsonString,@PathParam("id") int id) { //create new trip
        System.out.println(tripJsonString);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Trip trip = gson.fromJson(tripJsonString, Trip.class); //check if is correct
        trip.setId(id);
        TripDAO tripDAO=TripDAOFactory.getTripDAO();
        tripDAO.update(trip);
        return Response.status(201).entity(gson.toJson(trip)).build();
    }


    @DELETE
    @Path("/{id}")
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrip(@Context HttpHeaders headers,@PathParam("id") int id) { //create new trip
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        Integer userId = RequestUtility.getIdFromHeaders(headers);
        String role = RequestUtility.getRoleFromHeaders(headers);
        TripDAO tripDAO=TripDAOFactory.getTripDAO();
        Optional<Trip> optionalTrip = tripDAO.findById(id);
        if(optionalTrip.isPresent()){
            Trip trip=optionalTrip.get();
            if(role.equals("admin") || trip.getUser().getId()==userId ){
                if(tripDAO.delete(trip))
                    return Response.status(201).entity(gson.toJson(trip)).build();
                else
                    return ResponseBuilder.createResponse(Response.Status.UNAUTHORIZED);
            }

        }
        return ResponseBuilder.createResponse(Response.Status.NOT_FOUND);
    }


}
