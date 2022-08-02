package it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.mysql;

import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.TripDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.UserDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.UserDAOFactory;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.Trip;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.User;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLTripDAO implements TripDAO {
    public final static Logger logger = Logger.getLogger(MySQLTripDAO.class);
    private Connection connection = null;


    private static final String INSERT_TRIP="INSERT INTO USER(email,username,firstname,lastname,password,role) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_ALL_TRIP="SELECT `ID`,`NAME`,`VEHICLE`,`PATCH`,`MAIN_STAGES`,`ID_USER` FROM `trip`";
    private static final String SELECT_ALL_TRIP_BY_USERID="SELECT `ID`,`NAME`,`VEHICLE`,`PATCH`,`MAIN_STAGES`,`ID_USER` FROM `trip`";
    private static final String CREATE_TRIP_TABLE="CREATE TABLE `webprogramming`.`trip` ( `ID` INT NOT NULL AUTO_INCREMENT , `NAME` VARCHAR(30) NOT NULL , `VEHICLE` VARCHAR(25) NOT NULL , `PATCH` JSON NOT NULL , `MAIN_STAGES` JSON NOT NULL , `ID_USER` INT NOT NULL , PRIMARY KEY (`ID`)) ENGINE = InnoDB;";


    public MySQLTripDAO(it.units.studenti.mattiabressan.examwebprogramming.rest.database.connection.Connection connection) {
        this.connection = (Connection) connection.get();
    }

    @Override
    public Optional<Trip> findById(Integer id) {
        return Optional.empty();
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
                String vehicle = rs.getString("VEHICLE");
                String patch = rs.getString("PATCH");
                String mainStages = rs.getString("MAIN_STAGES");
                Integer userId = rs.getInt("ID_USER");
                UserDAO userDAO= UserDAOFactory.getUserDAO();
                Optional<User> optionalUser = userDAO.findById(userId);
                User user= optionalUser.isPresent() ? optionalUser.get(): null;

                trips.add(new Trip( tripId,name,vehicle,patch,mainStages,user));
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
                String vehicle = rs.getString("VEHICLE");
                String patch = rs.getString("PATCH");
                String mainStages = rs.getString("MAIN_STAGES");
                UserDAO userDAO= UserDAOFactory.getUserDAO();
                Optional<User> optionalUser = userDAO.findById(userId);
                User user= optionalUser.isPresent() ? optionalUser.get(): null;
                trips.add(new Trip( tripId,name,vehicle,patch,mainStages,user));
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
        return Optional.empty();
    }


}
