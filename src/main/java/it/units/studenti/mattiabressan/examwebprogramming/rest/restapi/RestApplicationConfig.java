package it.units.studenti.mattiabressan.examwebprogramming.rest.restapi;

import org.glassfish.jersey.server.ResourceConfig;

import it.units.studenti.mattiabressan.examwebprogramming.rest.filter.AuthenticationFilter;

/**
 *  set the filter applications manually and not via web.xml
 */
public class RestApplicationConfig extends ResourceConfig {

    public RestApplicationConfig() {
        packages( "it.units.studenti.mattiabressan.examwebprogramming.rest.filter" );
        register( AuthenticationFilter.class );
    }
}
