package it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.mysql;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.TripDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.UserDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.UserDAOFactory;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.Trip;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MySQLTripDAO implements TripDAO {
    public final static Logger logger = Logger.getLogger(MySQLTripDAO.class);
    private Connection connection = null;
    private static final String INSERT_TRIP = "INSERT INTO `trip` (`NAME`,`TRIP_DATE`, `VEHICLE`, `PATH`,  `ID_USER`) VALUES (?,?,?,?,?)";
    private static final String SELECT_ALL_TRIP = "SELECT `ID`,`NAME`,`TRIP_DATE`,`VEHICLE`,`PATH`,`ID_USER` FROM `trip`";
    private static final String SELECT_ALL_TRIP_BY_USERID = "SELECT `ID`,`NAME`,`TRIP_DATE`,`VEHICLE`,`PATH`,`ID_USER` FROM `trip` WHERE `ID_USER`=?";
    private static final String SELECT_TRIP_BY_ID = "SELECT `ID`,`NAME`,`TRIP_DATE`,`VEHICLE`,`PATH`,`ID_USER` FROM `trip` WHERE `ID`=?";
    private static final String CREATE_TRIP_TABLE = "CREATE TABLE `webprogramming`.`trip` ( `ID` INT NOT NULL AUTO_INCREMENT , `NAME` VARCHAR(30) NOT NULL , `TRIP_DATE` DATE NOT NULL , `VEHICLE` VARCHAR(25) NOT NULL , `PATH` JSON NOT NULL ,`ID_USER` INT NOT NULL , PRIMARY KEY (`ID`)) ENGINE = InnoDB;";
    private static final String DELETE_TRIP_BY_ID= "DELETE FROM `trip` WHERE `trip`.`ID` = ?";

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
                Integer userId = rs.getInt("ID_USER");

                UserDAO userDAO = UserDAOFactory.getUserDAO();
                Optional<User> optionalUser = userDAO.findById(userId);
                User user = optionalUser.isPresent() ? optionalUser.get() : null;

                Gson gson = new Gson();
                LinkedTreeMap pathanalyzed = gson.fromJson(path,LinkedTreeMap.class);
                trip = new Trip(tripId, name,tripDate, vehicle, pathanalyzed, user);
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
                Integer userId = rs.getInt("ID_USER");
                UserDAO userDAO = UserDAOFactory.getUserDAO();
                Optional<User> optionalUser = userDAO.findById(userId);
                User user = optionalUser.isPresent() ? optionalUser.get() : null;


                Gson gson = new Gson();
                LinkedTreeMap pathanalyzed = gson.fromJson(path,LinkedTreeMap.class);

                trips.add(new Trip(tripId, name,tripDate, vehicle, pathanalyzed,user));
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
        logger.debug("findAllByUserId:"+userId);
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
                UserDAO userDAO = UserDAOFactory.getUserDAO();
                Optional<User> optionalUser = userDAO.findById(userId);
                User user = optionalUser.isPresent() ? optionalUser.get() : null;


                Gson gson = new Gson();
                LinkedTreeMap pathanalyzed = gson.fromJson(path,LinkedTreeMap.class);


                trips.add(new Trip(tripId, name,tripDate, vehicle, pathanalyzed, user));
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
    public List<Trip> findAllByDates(Date startDate, Date endDate) {
        logger.debug("findAllByDates: startDate"+startDate+" endDate"+endDate);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Trip> trips = new ArrayList<Trip>();
        try {
            String query="";
            List<Date> prepare = new ArrayList<Date>();
            if(startDate==null && endDate==null){
               return trips;
            }
            if(startDate!=null){
                query="SELECT * FROM `trip` WHERE `TRIP_DATE` >= ?";
                prepare.add(startDate);
            }
            if(endDate!=null){
                query="SELECT * FROM `trip` WHERE `TRIP_DATE`<= ? ";
                prepare.add(endDate);
            }
            if(startDate!=null && endDate!=null)
                query="SELECT * FROM `trip` WHERE `TRIP_DATE` BETWEEN ? AND ? ";

            stmt = connection.prepareStatement(query);
            for (int i = 0; i < prepare.size(); i++) {
                stmt.setDate(i + 1, prepare.get(i));
            }

            rs = stmt.executeQuery();
            while (rs.next()) {
                Integer tripId = rs.getInt("ID");
                String name = rs.getString("NAME");
                Date tripDate=rs.getDate("TRIP_DATE");
                String vehicle = rs.getString("VEHICLE");
                String path = rs.getString("PATH");
                UserDAO userDAO = UserDAOFactory.getUserDAO();
                Optional<User> optionalUser = userDAO.findById(rs.getInt("ID_USER"));
                User user = optionalUser.isPresent() ? optionalUser.get() : null;
                Gson gson = new Gson();
                LinkedTreeMap pathanalyzed = gson.fromJson(path,LinkedTreeMap.class);
                trips.add(new Trip(tripId, name,tripDate, vehicle, pathanalyzed, user));
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
    public List<Trip> findAllByDates(Date startDate, Date endDate, Integer userId) {
        logger.debug("findAllByDates: startDate"+startDate+" endDate"+endDate+"userId:"+userId);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Trip> trips = new ArrayList<Trip>();
        try {
            String query;
            List<Date> prepare = new ArrayList<Date>();
            if(startDate==null && endDate==null){
                return trips;
            }else if(startDate!=null){
                query="SELECT * FROM `trip` WHERE `TRIP_DATE` >= ?";
                prepare.add(startDate);
            }else if(endDate!=null){
                query="SELECT * FROM `trip` WHERE `TRIP_DATE`<= ? ";
                prepare.add(endDate);
            }else
                query="SELECT * FROM `trip` WHERE `TRIP_DATE` BETWEEN ? AND ? ";
            query=query+"AND ID_USER=?";
            stmt = connection.prepareStatement(query);
            for (int i = 0; i < prepare.size(); i++) {
                stmt.setDate(i + 1, prepare.get(i));
            }
            stmt.setInt(prepare.size() + 1, userId);

            rs = stmt.executeQuery();
            while (rs.next()) {
                Integer tripId = rs.getInt("ID");
                String name = rs.getString("NAME");
                Date tripDate=rs.getDate("TRIP_DATE");
                String vehicle = rs.getString("VEHICLE");
                String path = rs.getString("PATH");
                UserDAO userDAO = UserDAOFactory.getUserDAO();
                Optional<User> optionalUser = userDAO.findById(rs.getInt("ID_USER"));
                User user = optionalUser.isPresent() ? optionalUser.get() : null;
                Gson gson = new Gson();
                LinkedTreeMap pathanalyzed = gson.fromJson(path,LinkedTreeMap.class);
                trips.add(new Trip(tripId, name,tripDate, vehicle, pathanalyzed, user));
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
        //TODO check trip different from null
        logger.debug("createTrip: " + trip.getName());
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(INSERT_TRIP);
            stmt.setString(1, trip.getName());
            Gson gson = new Gson();

            stmt.setDate(2, new Date(trip.getTripDate().getTime()));
            stmt.setString(3, trip.getVehicle());
            stmt.setString(4, gson.toJson(trip.getPath()));
            stmt.setInt(5, trip.getUser().getId());
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
        //TODO check trip different from null
        logger.debug("deleteTrip: " + trip.getId());
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(DELETE_TRIP_BY_ID);
            stmt.setInt(1, trip.getId());
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
        return true;
    }

    @Override
    public Optional<Trip> update(Trip trip) {
        
        logger.debug("update Trip: " + trip.getId());
        PreparedStatement stmt = null;
        try {
            Gson gson = new Gson();
            // prepare query
            StringBuffer query = new StringBuffer();
            query.append("UPDATE TRIP SET ");

            boolean comma = false;
            List<String> prepare = new ArrayList<String>();
            List<Date> prepareDate=new ArrayList<Date>();
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
            }
            if (trip.getPath() != null) {
                if (comma) query.append(",");
                query.append("PATH=?");
                comma = true;
                prepare.add(gson.toJson(trip.getPath()));
            }
            if (trip.getTripDate() != null) {
                if (comma) query.append(",");
                query.append("TRIP_DATE=?");
                comma = true;
                prepareDate.add(trip.getTripDate());
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
            for (int i = 0; i < prepareDate.size(); i++) {
                stmt.setDate(prepare.size()+i+ 1, prepareDate.get(i));
            }
            for (int i = 0; i < prepareInteger.size(); i++) {
                stmt.setInt(prepare.size()+prepareDate.size()+i+1, prepareInteger.get(i));
            }

            stmt.setInt(prepare.size()+prepareInteger.size()+prepareDate.size() + 1, trip.getId());

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
