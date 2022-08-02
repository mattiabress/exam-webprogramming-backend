package it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao;

import it.units.studenti.mattiabressan.examwebprogramming.rest.model.Trip;

import java.util.List;
import java.util.Optional;

public interface TripDAO {
    public Optional<Trip> findById(Integer id);
    public List<Trip> findAll();
    public List<Trip> findAllByUserId(Integer userId);
    public Optional<Trip> createTrip(Trip trip);

}
