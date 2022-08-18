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
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@DeclareRoles({"admin", "user", "guest"})
@Path("/trip")
public class TripResource extends ResourceConfig {
    @GET
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrips(@Context HttpHeaders headers, @QueryParam("startdate") Date startDate, @QueryParam("enddate") Date endDate) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        try {
            // return only user's trips or if he is a admin all trips
            Integer userId = RequestUtility.getIdFromHeaders(headers);
            String role = RequestUtility.getRoleFromHeaders(headers);
            TripDAO tripDAO = TripDAOFactory.getTripDAO();
            List<Trip> trips;
            if (role.equals("admin"))
                if (startDate != null || endDate != null)
                    trips = tripDAO.findAllByDates(startDate, endDate);
                else
                    trips = tripDAO.findAll();
            else // user
                if (startDate != null || endDate != null)
                    trips = tripDAO.findAllByDates(startDate, endDate, userId);
                else
                    trips = tripDAO.findAllByUserId(userId);
            //return Response.ok(gson.toJson(trips)).build();
            return Response.status(Response.Status.OK).entity(gson.toJson(trips)).build();
        } catch (Exception e) {
            return ResponseBuilder.createResponse(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrip(@Context HttpHeaders headers, @PathParam("id") int id) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        try {
            TripDAO tripDAO = TripDAOFactory.getTripDAO();
            Integer userId = RequestUtility.getIdFromHeaders(headers);
            String role = RequestUtility.getRoleFromHeaders(headers);
            // Search for a trip by ID
            Optional<Trip> optionalTrip = tripDAO.findById(id);
            if (optionalTrip.isPresent()) {
                Trip trip = optionalTrip.get();
                if (role.equals("admin") || trip.getUser().getId() == userId)
                    // return Response.ok(gson.toJson(optionalTrip.get())).build();
                    return Response.status(Response.Status.OK).entity(gson.toJson(optionalTrip.get())).build();
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
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        try {
            Trip trip = gson.fromJson(tripJsonString, Trip.class);
            Integer userId = RequestUtility.getIdFromHeaders(headers);
            TripDAO tripDAO = TripDAOFactory.getTripDAO();
            UserDAO userDAO = UserDAOFactory.getUserDAO();
            User user = userDAO.findById(userId).get();
            trip.setUser(user);
            Optional<Trip> optionalTrip = tripDAO.createTrip(trip);
            if (optionalTrip.isPresent())
                //return Response.status(201).entity(gson.toJson(trip)).build();
                return Response.status(Response.Status.CREATED).entity(gson.toJson(optionalTrip.get())).build();
            else
                return ResponseBuilder.createResponse(Response.Status.CONFLICT);
        } catch (Exception e) {
            return ResponseBuilder.createResponse(Response.Status.INTERNAL_SERVER_ERROR);
        }

    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setTrip(String tripJsonString, @PathParam("id") int id) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        try {
            TripDAO tripDAO = TripDAOFactory.getTripDAO();
            Optional<Trip> optionalTrip = tripDAO.findById(id);
            if (optionalTrip.isPresent()) { //check if trip exists
                Trip trip = gson.fromJson(tripJsonString, Trip.class); //check if is correct
                trip.setId(optionalTrip.get().getId());
                Optional<Trip> optionalTripupdated = tripDAO.update(trip);
                if (optionalTripupdated.isPresent()) //if trip is updated correctly
                    //return Response.status(201).entity(gson.toJson(trip)).build();
                    return Response.status(Response.Status.OK).entity(gson.toJson(optionalTripupdated.get())).build();
                else
                    return ResponseBuilder.createResponse(Response.Status.CONFLICT);
            }
            return ResponseBuilder.createResponse(Response.Status.NOT_FOUND);
        } catch (Exception e) {
            return ResponseBuilder.createResponse(Response.Status.INTERNAL_SERVER_ERROR);
        }

    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrip(@Context HttpHeaders headers, @PathParam("id") int id) { //create new trip
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        //Gson gson = new GsonBuilder().registerTypeAdapterFactory()
        try {
            Integer userId = RequestUtility.getIdFromHeaders(headers);
            String role = RequestUtility.getRoleFromHeaders(headers);
            TripDAO tripDAO = TripDAOFactory.getTripDAO();
            Optional<Trip> optionalTrip = tripDAO.findById(id);
            if (optionalTrip.isPresent()) {
                Trip trip = optionalTrip.get();
                if (role.equals("admin") || trip.getUser().getId() == userId) {
                    if (tripDAO.delete(trip))
                        //return Response.status(201).entity(gson.toJson(trip)).build();
                        return Response.status(Response.Status.OK).entity(gson.toJson(trip)).build();
                    else
                        return ResponseBuilder.createResponse(Response.Status.CONFLICT);
                }
                return ResponseBuilder.createResponse(Response.Status.UNAUTHORIZED);
            }
            return ResponseBuilder.createResponse(Response.Status.NOT_FOUND);
        } catch (Exception e) {
            return ResponseBuilder.createResponse(Response.Status.INTERNAL_SERVER_ERROR);
        }


    }


}
