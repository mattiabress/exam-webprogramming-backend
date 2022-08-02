//package it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao;
//
//import jakarta.persistence.EntityManager;
//
//import java.util.List;
//import java.util.Optional;
//
//
//public class TripDAO_old {
//    private EntityManager entityManager;
//
//    public TripDAO_old(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//    public Optional<Trip> findById(Integer id) {
//        Trip trip = entityManager.find(Trip.class, id);
//        return trip != null ? Optional.of(trip) : Optional.empty();
//    }
//    public List<Trip> findAll() {
//        return entityManager.createQuery("FROM Trip",Trip.class).getResultList();
//    }
//    public Optional<Trip> findByName(String name) {
//        Trip trip = entityManager.createQuery("SELECT b FROM Trip b WHERE b.name = :name", Trip.class)
//                .setParameter("name", name)
//                .getSingleResult();
//        return trip != null ? Optional.of(trip) : Optional.empty();
//    }
//    public Optional<Trip> findByNameNamedQuery(String name) {
//        Trip trip = entityManager.createNamedQuery("Trip.findByName", Trip.class)
//                .setParameter("name", name)
//                .getSingleResult();
//        return trip != null ? Optional.of(trip) : Optional.empty();
//    }
//    public Optional<Trip> save(Trip trip) {
//        try {
//            entityManager.getTransaction().begin();
//            entityManager.persist(trip);
//            entityManager.getTransaction().commit();
//            return Optional.of(trip);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return Optional.empty();
//    }
//}
