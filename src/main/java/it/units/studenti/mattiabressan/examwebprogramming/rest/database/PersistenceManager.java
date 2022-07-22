package it.units.studenti.mattiabressan.examwebprogramming.rest.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
public class PersistenceManager {

    private static EntityManagerFactory factory;
    public static EntityManager getEntityManager() {
        if (factory == null) {
            factory =
                    Persistence.createEntityManagerFactory("jpatutorial");
        }
        EntityManager entityManager = factory.createEntityManager();
        return entityManager;
    }
}