package it.units.studenti.mattiabressan.examwebprogramming.rest.filter;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextListener;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.PersistenceManager;
public class PersistenceListener implements ServletContextListener {
    public void contextInitialized(ServletContext context) {
        PersistenceManager.getEntityManager();
    }
}
