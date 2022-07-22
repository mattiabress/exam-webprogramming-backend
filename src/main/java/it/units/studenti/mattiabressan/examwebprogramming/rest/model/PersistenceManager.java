package it.units.studenti.mattiabressan.examwebprogramming.rest.model;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
public class PersistenceManager {

    private static EntityManagerFactory factory;

    public static EntityManager getEntityManager() {
        if (factory == null) {
            EntityManagerFactory factory =
                    Persistence.createEntityManagerFactory("jpatutorial");
        }
        EntityManager entityManager = factory.createEntityManager();
        return entityManager;
    }


    public static EntityManager beginTransaction() {
        EntityManager entityManager = getEntityManager();
        entityManager.getTransaction().begin();
        return entityManager;
    }

    public static void commitTransaction(EntityManager em) {
        em.close();
    }

}