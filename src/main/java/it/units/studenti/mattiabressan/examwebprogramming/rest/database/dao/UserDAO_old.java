//package it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao;
//
//import it.units.studenti.mattiabressan.examwebprogramming.rest.model.User;
//import it.units.studenti.mattiabressan.examwebprogramming.rest.model.UserSecurity;
//import jakarta.persistence.EntityManager;
//
//import java.util.List;
//import java.util.Optional;
//
//public class UserDAO_old {
//    private EntityManager entityManager;
//
//    public UserDAO_old(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//    public Optional<User> findById(Integer id) {
//        User user = entityManager.find(User.class, id);
//        return user != null ? Optional.of(user) : Optional.empty();
//    }
//
//    public List<User> findAll() {
//        return entityManager.createQuery("FROM User", User.class).getResultList();
//    }
//
//    public Optional<User> findByName(String name) {
//        User user = entityManager.createQuery("SELECT b FROM User b WHERE b.name = :name", User.class)
//                .setParameter("name", name)
//                .getSingleResult();
//        return user != null ? Optional.of(user) : Optional.empty();
//    }
//
//    public Optional<User> findByNameNamedQuery(String name) {
//        User user = entityManager.createNamedQuery("User.findByName", User.class)
//                .setParameter("name", name)
//                .getSingleResult();
//        return user != null ? Optional.of(user) : Optional.empty();
//    }
//
//    public Optional<User> save(User user) {
//        try {
//            entityManager.getTransaction().begin();
//            entityManager.persist(user);
//            entityManager.getTransaction().commit();
//            return Optional.of(user);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return Optional.empty();
//    }
//
//    public UserSecurity getUserAuthentication(int id) {
//        Optional<User> user = findById(id);
//        UserSecurity userSecurity = new UserSecurity(); //TODO sistemare
//        return userSecurity;
//    }
//}