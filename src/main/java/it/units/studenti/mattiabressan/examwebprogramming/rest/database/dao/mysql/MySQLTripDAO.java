package it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.mysql;

import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.TripDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.UserDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.UserDAOFactory;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.Trip;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.User;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

public class MySQLTripDAO implements TripDAO {
    public final static Logger logger = Logger.getLogger(MySQLTripDAO.class);
    private Connection connection = null;


    private static final String INSERT_TRIP = "INSERT INTO `trip` (`NAME`,`TRIP_DATE`, `VEHICLE`, `PATH`, `MAIN_STAGES`, `ID_USER`) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_ALL_TRIP = "SELECT `ID`,`NAME`,`TRIP_DATE`,`VEHICLE`,`PATH`,`MAIN_STAGES`,`ID_USER` FROM `trip`";
    private static final String SELECT_ALL_TRIP_BY_USERID = "SELECT `ID`,`NAME`,`TRIP_DATE`,`VEHICLE`,`PATH`,`MAIN_STAGES`,`ID_USER` FROM `trip`";
    private static final String SELECT_TRIP_BY_ID = "SELECT `ID`,`NAME`,`TRIP_DATE`,`VEHICLE`,`PATH`,`MAIN_STAGES`,`ID_USER` FROM `trip` WHERE `ID`=?";
    private static final String CREATE_TRIP_TABLE = "CREATE TABLE `webprogramming`.`trip` ( `ID` INT NOT NULL AUTO_INCREMENT , `NAME` VARCHAR(30) NOT NULL , `TRIP_DATE` DATE NOT NULL , `VEHICLE` VARCHAR(25) NOT NULL , `PATH` JSON NOT NULL , `MAIN_STAGES` JSON NOT NULL , `ID_USER` INT NOT NULL , PRIMARY KEY (`ID`)) ENGINE = InnoDB;";


    public MySQLTripDAO(it.units.studenti.mattiabressan.examwebprogramming.rest.database.connection.Connection connection) {
        this.connection = (Connection) connection.get();
    }

    @Override
    public Optional<Trip> findById(Integer id) {
        logger.debug("getTrip: " + id);

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Trip trip = null;

        try {
            stmt = connection.prepareStatement(SELECT_TRIP_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Integer tripId = rs.getInt("ID");
                String name = rs.getString("NAME");
                Date tripDate=rs.getDate("TRIP_DATE");
                String vehicle = rs.getString("VEHICLE");
                String path = rs.getString("PATH");
                String mainStages = rs.getString("MAIN_STAGES");
                Integer userId = rs.getInt("ID_USER");
                UserDAO userDAO = UserDAOFactory.getUserDAO();
                Optional<User> optionalUser = userDAO.findById(userId);
                User user = optionalUser.isPresent() ? optionalUser.get() : null;
                trip = new Trip(tripId, name,tripDate, vehicle, path, mainStages, user);
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            }
        }
        return Optional.of(trip);
    }

    @Override
    public List<Trip> findAll() {
        logger.debug("getAllTrips");
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Trip> trips = new ArrayList<Trip>();
        try {
            stmt = connection.prepareStatement(SELECT_ALL_TRIP);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Integer tripId = rs.getInt("ID");
                String name = rs.getString("NAME");
                Date tripDate=rs.getDate("TRIP_DATE");
                String vehicle = rs.getString("VEHICLE");
                String path = rs.getString("PATH");
                String mainStages = rs.getString("MAIN_STAGES");
                Integer userId = rs.getInt("ID_USER");
                UserDAO userDAO = UserDAOFactory.getUserDAO();
                Optional<User> optionalUser = userDAO.findById(userId);
                User user = optionalUser.isPresent() ? optionalUser.get() : null;

                trips.add(new Trip(tripId, name,tripDate, vehicle, path, mainStages, user));
            }
        } catch (SQLException e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            }
        }
        return trips;
    }

    @Override
    public List<Trip> findAllByUserId(Integer userId) {
        logger.debug("findAllByUserId");
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Trip> trips = new ArrayList<Trip>();
        try {
            stmt = connection.prepareStatement(SELECT_ALL_TRIP_BY_USERID);
            stmt.setInt(1, userId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Integer tripId = rs.getInt("ID");
                String name = rs.getString("NAME");
                Date tripDate=rs.getDate("TRIP_DATE");
                String vehicle = rs.getString("VEHICLE");
                String path = rs.getString("PATH");
                String mainStages = rs.getString("MAIN_STAGES");
                UserDAO userDAO = UserDAOFactory.getUserDAO();
                Optional<User> optionalUser = userDAO.findById(userId);
                User user = optionalUser.isPresent() ? optionalUser.get() : null;
                trips.add(new Trip(tripId, name,tripDate, vehicle, path, mainStages, user));
            }
        } catch (SQLException e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            }
        }
        return trips;
    }

    @Override
    public Optional<Trip> createTrip(Trip trip) {
        logger.debug("createTrip: " + trip.getName());
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(INSERT_TRIP);
            stmt.setString(1, trip.getName());
            stmt.setDate(2, trip.getTripDate());
            stmt.setString(3, trip.getVehicle());
            stmt.setObject(4, trip.getPath());
            stmt.setString(5, trip.getMainStages());
            stmt.setInt(6, trip.getUser().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            }
        }
        return Optional.of(trip);
    }

    @Override
    public boolean delete(Trip trip) {
        return false;
    }

    @Override
    public Optional<Trip> update(Trip trip) {
        
        logger.debug("update Trip: " + trip.getId());
        PreparedStatement stmt = null;
        try {
            // prepare query
            StringBuffer query = new StringBuffer();
            query.append("UPDATE TRIP SET ");

            boolean comma = false;
            List<String> prepare = new ArrayList<String>();
            if (trip.getName() != null) {
                query.append("NAME=?");
                comma = true;
                prepare.add(trip.getName());
            }

            if (trip.getVehicle() != null) {
                if (comma) query.append(",");
                query.append("VEHICLE=?");
                comma = true;
                prepare.add(trip.getVehicle());
            }/* //TODO sistemare
            if (trip.getPath() != null) {
                if (comma) query.append(",");
                query.append("PATCH=?");
                comma = true;
                prepare.add(trip.getPath());
            }*/
            if (trip.getMainStages() != null) {
                if (comma) query.append(",");
                query.append("MAIN_STAGES=?");
                comma = true;
                prepare.add(trip.getMainStages());
            }
            List<Integer> prepareInteger = new ArrayList<Integer>();
            // int value
            if (trip.getUser() != null) {
                if (comma) query.append(",");
                query.append("ID_USER=?");
                prepareInteger.add(trip.getUser().getId());
            }
            query.append(" WHERE id=?");

            stmt = connection.prepareStatement(query.toString());

            for (int i = 0; i < prepare.size(); i++) {
                stmt.setString(i + 1, prepare.get(i));
            }

            for (int i = prepare.size(); i < prepareInteger.size(); i++) {
                stmt.setInt(i+1, prepareInteger.get(i));
            }

            stmt.setInt(prepare.size()+prepareInteger.size() + 1, trip.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            }
        }

        return Optional.of(trip);
    }


}
