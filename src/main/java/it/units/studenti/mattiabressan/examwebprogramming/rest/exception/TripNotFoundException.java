package it.units.studenti.mattiabressan.examwebprogramming.rest.exception;

public class TripNotFoundException extends RuntimeException{
    private static final long serialVersionUID = -8078059893066017473L;

    public TripNotFoundException(int tripId ) {
        super("Trip not found: " + tripId );
    }
}
