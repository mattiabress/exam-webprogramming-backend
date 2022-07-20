package it.units.studenti.mattiabressan.examwebprogramming;

import org.glassfish.jersey.server.ResourceConfig;

public class RestApplicationConfig extends ResourceConfig {

    public RestApplicationConfig() {
        packages( "com.tutorialacademy.rest.filter" );
        register( AuthenticationFilter.class );
    }
}
