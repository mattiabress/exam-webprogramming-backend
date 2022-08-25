package it.units.studenti.mattiabressan.examwebprogramming.rest.restapi;

import it.units.studenti.mattiabressan.examwebprogramming.rest.filter.AuthenticationFilter;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * set the filter applications manually and not via web.xml
 */
public class RestApplicationConfig extends ResourceConfig {

    public RestApplicationConfig() {
        packages("it.units.studenti.mattiabressan.examwebprogramming.rest.filter");
        register(AuthenticationFilter.class);
    }
}
