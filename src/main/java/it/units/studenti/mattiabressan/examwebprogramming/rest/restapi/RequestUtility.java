package it.units.studenti.mattiabressan.examwebprogramming.rest.restapi;

import it.units.studenti.mattiabressan.examwebprogramming.rest.filter.AuthenticationFilter;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.HttpHeaders;
import java.util.List;

public class RequestUtility {
    public static int getIdFromHeaders( HttpHeaders headers) {
        // get the email we set in AuthenticationFilter
        List<String> id = headers.getRequestHeader( AuthenticationFilter.HEADER_PROPERTY_ID );

        if( id == null || id.size() != 1 )
            throw new NotAuthorizedException("Unauthorized!");
        return Integer.parseInt(id.get(0));
    }
    public static String getRoleFromHeaders( HttpHeaders headers) {
        // get the email we set in AuthenticationFilter
        List<String> role = headers.getRequestHeader( AuthenticationFilter.HEADER_PROPERTY_ROLE );
        if( role == null || role.size() != 1 )
            throw new NotAuthorizedException("Unauthorized!");
        return role.get(0);
    }
}
