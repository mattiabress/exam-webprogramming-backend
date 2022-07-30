package it.units.studenti.mattiabressan.examwebprogramming.rest.restapi;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.collections.impl.lazy.parallel.set.sorted.SelectSortedSetBatch;
import org.glassfish.jersey.server.ResourceConfig;

import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.UserDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.UserDAOFactory;
import it.units.studenti.mattiabressan.examwebprogramming.rest.exception.UserNotFoundException;
import it.units.studenti.mattiabressan.examwebprogramming.rest.exception.UserExistingException;
import it.units.studenti.mattiabressan.examwebprogramming.rest.filter.AuthenticationFilter;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.Credentials;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.JsonSerializable;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.User;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.UserSecurity;
import it.units.studenti.mattiabressan.examwebprogramming.rest.security.PasswordSecurity;
import it.units.studenti.mattiabressan.examwebprogramming.rest.security.TokenSecurity;

@DeclareRoles({"admin", "user", "guest"})
@Path("/user")
public class UserResource extends ResourceConfig {

    @POST
    @Path("/signup")
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser( UserSecurity userSecurity ) {
        UserDAO userDao = UserDAOFactory.getUserDAO();
        try {
            if(userDao.findByUsername(userSecurity.getUsername()).isPresent()){
                // standard user role
                userSecurity.setRole("user");
                // store plain password
                String plainPassword = userSecurity.getPassword();
                // generate password
                userSecurity.setPassword( PasswordSecurity.generateHash( userSecurity.getPassword() ) );
                // create user
                userDao.createUser( userSecurity );
                // authenticate user
                return authenticate( new Credentials( userSecurity.getEmail(), plainPassword ) );
            }
            else
                throw new UserExistingException( userSecurity.getEmail() );
        }
        catch ( UserExistingException e ) {
            return ResponseBuilder.createResponse( Response.Status.CONFLICT, e.getMessage() );
        }
        catch ( Exception e ) {
            return ResponseBuilder.createResponse( Response.Status.INTERNAL_SERVER_ERROR );
        }
    }

    @POST
    @Path("/login")
    @PermitAll
    @Produces("application/json")
    @Consumes("application/json")
    public Response authenticate( Credentials credentials ) {
        UserDAO userDao = UserDAOFactory.getUserDAO();

        try {

            if(userDao.findByUsername(credentials.getUsername()).isPresent()){
                UserSecurity userSecurity = userDao;
            }




            String id = userDao.getUserIdByEmail( credentials.getEmail() );
            UserSecurity userSecurity = userDao.getUserAuthentication( id );

            if( PasswordSecurity.validatePassword( credentials.getPassword(), userSecurity.getPassword() ) == false ) {
                return ResponseBuilder.createResponse( Response.Status.UNAUTHORIZED );
            }

            // generate a token for the user
            String token = TokenSecurity.generateJwtToken( id );

            // write the token to the database
            UserSecurity sec = new UserSecurity( null, token );
            sec.setId( id );
            userDao.setUserAuthentication( sec );

            Map<String,Object> map = new HashMap<String,Object>();
            map.put( AuthenticationFilter.AUTHORIZATION_PROPERTY, token );

            // Return the token on the response
            return ResponseBuilder.createResponse( Response.Status.OK, map );
        }
        catch( UserNotFoundException e ) {
            return ResponseBuilder.createResponse( Response.Status.NOT_FOUND, e.getMessage() );
        }
        catch( Exception e ) {
            return ResponseBuilder.createResponse( Response.Status.UNAUTHORIZED );
        }

    }

    @GET
    @Path("/")
    @RolesAllowed({"admin","user"})
    @Produces("application/json")
    public Response get( @Context HttpHeaders headers ) {
        UserDAO userDao = UserDAOFactory.getUserDAO();

        try {
            String id = getId( headers );

            // use decoded email from jwt in header
            User user = userDao.getUser( id );

            // Return the user on the response
            return ResponseBuilder.createResponse( Response.Status.OK, user );
        }
        catch( UserNotFoundException e ) {
            return ResponseBuilder.createResponse( Response.Status.NOT_FOUND, e.getMessage() );
        }
        catch ( Exception e ) {
            return ResponseBuilder.createResponse( Response.Status.UNAUTHORIZED );
        }
    }

    @GET
    @Path("/getAll")
    @RolesAllowed({"admin"}) // only an admin user should be allowed to request all users
    @Produces("application/json")
    public Response getAll( @Context HttpHeaders headers ) {
        UserDAO userDao = UserDAOFactory.getUserDAO();

        try {
            List<JsonSerializable> usersJson = new ArrayList<JsonSerializable>();
            usersJson.addAll( (Collection<? extends JsonSerializable>) userDao.getAllUsers() );

            // Return the users on the response
            return ResponseBuilder.createResponse( Response.Status.OK, usersJson );
        }
        catch( UserNotFoundException e ) {
            return ResponseBuilder.createResponse( Response.Status.NOT_FOUND, e.getMessage() );
        }
        catch ( Exception e ) {
            return ResponseBuilder.createResponse( Response.Status.UNAUTHORIZED );
        }

    }

    @PUT
    @Path("/update")
    @RolesAllowed({"admin","user"}) // only an admin user should be allowed to request all users
    @Produces("application/json")
    public Response update( @Context HttpHeaders headers, User user ) {
        UserDAO userDao = UserDAOFactory.getUserDAO();

        try {
            String id = getId( headers );

            user.setId( id );
            userDao.updateUser( user );

            // Return the token on the response
            return ResponseBuilder.createResponse( Response.Status.OK, "User updated" );
        }
        catch( UserNotFoundException e ) {
            return ResponseBuilder.createResponse( Response.Status.NOT_FOUND, e.getMessage() );
        }
        catch ( Exception e ) {
            return ResponseBuilder.createResponse( Response.Status.UNAUTHORIZED );
        }

    }

    @DELETE
    @Path("/")
    @RolesAllowed({"admin","user"})
    @Produces("application/json")
    public Response delete( @Context HttpHeaders headers ) {
        UserDAO userDao = UserDAOFactory.getUserDAO();

        try {
            String id = getId( headers );

            userDao.deleteUser( id );

            // Return the response
            return ResponseBuilder.createResponse( Response.Status.OK, "User deleted" );
        }
        catch( UserNotFoundException e ) {
            return ResponseBuilder.createResponse( Response.Status.NOT_FOUND, e.getMessage() );
        }
        catch ( Exception e ) {
            return ResponseBuilder.createResponse( Response.Status.UNAUTHORIZED );
        }

    }

    private String getId( HttpHeaders headers) {
        // get the email we set in AuthenticationFilter
        List<String> id = headers.getRequestHeader( AuthenticationFilter.HEADER_PROPERTY_ID );

        if( id == null || id.size() != 1 )
            throw new NotAuthorizedException("Unauthorized!");

        return id.get(0);
    }

}
