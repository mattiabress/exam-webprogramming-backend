package it.units.studenti.mattiabressan.examwebprogramming.rest.database.connection.mysql;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import it.units.studenti.mattiabressan.examwebprogramming.rest.config.DbConfig;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.TripDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.TripDAOFactory;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.UserDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.UserDAOFactory;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.mysql.MySQLTripDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.Trip;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.User;
import it.units.studenti.mattiabressan.examwebprogramming.rest.restapi.ResponseBuilder;
import it.units.studenti.mattiabressan.examwebprogramming.rest.security.PasswordSecurity;
import org.apache.log4j.Logger;

import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;
import java.util.Optional;

public class MySQLConnection implements it.units.studenti.mattiabressan.examwebprogramming.rest.database.connection.Connection {

    final static Logger logger = Logger.getLogger(MySQLConnection.class);

    Connection connection = null;

    @Override
    public Object get() {
        return connection;
    }

    @Override
    public boolean open() {

        connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String path = null;
            if (DbConfig.getDbPath() != null) {
                path = DbConfig.getDbPath();
            } else {
                path = "jdbc:mysql://" + DbConfig.getDbHost() + ":" + DbConfig.getDbPassword() + "/" + DbConfig.getDbName();
            }
            connection = DriverManager.getConnection(path, DbConfig.getDbUser(), DbConfig.getDbPassword());

        } catch (Exception e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        logger.debug("Opened database successfully");

        // database openend - init database schema
        createUserSchema();
        // create admin user
        createAdminUser();

        //Trip schema
        createTripSchema();
        // create general user
        createGeneralUser();
        initializeExampleTrip();
        return true;
    }

    @Override
    public boolean close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.debug("database not closed correctly: " + e.getMessage());
            }
            return true;
        }
        return false;
    }

    private boolean checkForUserSchema() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT table_schema AS database_name, table_name FROM information_schema.tables WHERE table_schema='" +
                    DbConfig.getDbName()+"' and table_name='user'");
            if (rs.next()) {
                return true;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }

    private void createUserSchema() {
        if (checkForUserSchema() == true) return;

        try {
            Statement stmt = connection.createStatement();
            String sql = "CREATE TABLE `webprogramming`.`user` ( " +
                    "`ID` INT NOT NULL AUTO_INCREMENT , " +
                    "`EMAIL` VARCHAR(50) NOT NULL , " +
                    "`USERNAME` VARCHAR(50) NOT NULL , " +
                    "`FIRSTNAME` VARCHAR(50) NOT NULL , " +
                    "`LASTNAME` VARCHAR(50) NOT NULL , " +
                    "`PASSWORD` VARCHAR(200) NOT NULL , " +
                    "`TOKEN` text  , " +
                    "`ROLE` VARCHAR(50) NOT NULL , PRIMARY KEY (`ID`)) " +
                    "ENGINE = InnoDB;";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        }
    }




    private boolean checkForAdminUser() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT `USERNAME` FROM `user` WHERE `USERNAME`='root';");
            if (rs.next()) {
                return true;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }

    private boolean checkForGeneralUser() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT `USERNAME` FROM `user` WHERE `USERNAME`='mattia';");
            if (rs.next()) {
                return true;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }


    private void createAdminUser() {
        if (checkForAdminUser()) return;

        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement("INSERT INTO `user` (`ID`, `EMAIL`, `USERNAME`, `FIRSTNAME`, `LASTNAME`, `PASSWORD`,  `ROLE`) " +
                            "VALUES (NULL, ?,?,?, ?, ?,?)"
                    );

            stmt.setString(1, "root@root");
            stmt.setString(2, "root");
            stmt.setString(3, "root");
            stmt.setString(4, "root");
            stmt.setString(5, PasswordSecurity.generateHash("root"));
            stmt.setString(6, "admin");
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            }
        }
    }

    private void createGeneralUser() {
        if (checkForGeneralUser()) return;

        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement("INSERT INTO `user` (`ID`, `EMAIL`, `USERNAME`, `FIRSTNAME`, `LASTNAME`, `PASSWORD`,  `ROLE`) " +
                    "VALUES (NULL, ?,?,?, ?, ?,?)"
            );

            stmt.setString(1, "mattia@mattia");
            stmt.setString(2, "mattia");
            stmt.setString(3, "mattia");
            stmt.setString(4, "Bressan");
            stmt.setString(5, PasswordSecurity.generateHash("secret"));
            stmt.setString(6, "user");
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        } finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                logger.warn(e.getMessage());
            }
        }
    }


// Trip's methods

    private void createTripSchema(){
        if (checkForTripSchema() == true) return;
        try {
            Statement stmt = connection.createStatement();
            String sql = MySQLTripDAO.CREATE_TRIP_TABLE;
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    private boolean checkForTripSchema() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT table_schema AS database_name, table_name FROM information_schema.tables WHERE table_schema='" +
                    DbConfig.getDbName()+"' and table_name='trip'");
            if (rs.next()) {
                return true;
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            logger.debug(e.getClass().getName() + ": " + e.getMessage());
        }
        return false;
    }

    private boolean initializeExampleTrip(){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        UserDAO userDao = UserDAOFactory.getUserDAO();
        TripDAO tripDAO = TripDAOFactory.getTripDAO();
        String trip1JsonString="{\"id\":null,\"name\":\"Primo viaggio\",\"tripDate\":\"2022-08-25\",\"vehicle\":\"macchina\",\"path\":{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[13.770461,45.654738],[13.773208,45.653988],[13.776255,45.653148],[13.777714,45.650208],[13.778658,45.648468],[13.776813,45.649608],[13.772264,45.650058],[13.768616,45.651648]]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.770332,45.654708]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.775911,45.653028]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.778615,45.648498]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.776727,45.649488]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.772049,45.649878]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.768444,45.651558]}}]}}";
        String trip2JsonString="{\"id\":null,\"name\":\"secondo viaggio\",\"tripDate\":\"2022-08-26\",\"vehicle\":\"monopattino\",\"path\":{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[13.775139,45.655158],[13.776469,45.652968],[13.779345,45.654078],[13.782649,45.656358],[13.786812,45.656538],[13.789687,45.656508],[13.785996,45.654048],[13.789387,45.654498],[13.79355,45.655338],[13.797927,45.654798],[13.789344,45.653658],[13.789473,45.652098],[13.791876,45.652638],[13.793764,45.651168],[13.795953,45.650478],[13.797154,45.649698]]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.775096,45.655218]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.776469,45.652878]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.782692,45.656268]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.789773,45.656418]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.785911,45.654018]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.793721,45.655248]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.797798,45.654798]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.797112,45.649488]}}]}}";
        String trip3JsonString="{\"id\":null,\"name\":\"Terzo viaggio\",\"tripDate\":\"2022-08-27\",\"vehicle\":\"bici\",\"path\":{\"type\":\"FeatureCollection\",\"features\":[{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"LineString\",\"coordinates\":[[13.760934,45.646683],[13.768873,45.651663],[13.771148,45.656283],[13.771749,45.657513],[13.773723,45.657933],[13.775997,45.658592],[13.777242,45.657153],[13.775053,45.656343],[13.775353,45.654933],[13.777885,45.649593],[13.780589,45.646083],[13.785996,45.647583],[13.790717,45.649773],[13.791919,45.650193],[13.791704,45.652683],[13.79458,45.650523],[13.799558,45.648393]]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.760934,45.646713]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.768916,45.651723]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.771791,45.657363]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.776083,45.658262]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.777285,45.656973]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.78046,45.645963]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.791747,45.652623]}},{\"type\":\"Feature\",\"properties\":{},\"geometry\":{\"type\":\"Point\",\"coordinates\":[13.799386,45.648273]}}]}}";
        Optional<User> optionalUser = userDao.findByUsername("root");
        if (optionalUser.isEmpty()) {
            return false;
        }
        User root = optionalUser.get();
        optionalUser = userDao.findByUsername("mattia");
        if (optionalUser.isEmpty()) {
            return false;
        }
        User mattia = optionalUser.get();
        Trip trip1 = gson.fromJson(trip1JsonString, Trip.class);
        Trip trip2=gson.fromJson(trip2JsonString, Trip.class);
        Trip trip3=gson.fromJson(trip3JsonString, Trip.class);
        trip1.setUser(root);
        trip2.setUser(root);
        trip3.setUser(mattia);
        Optional<Trip> optionalTrip1 = tripDAO.createTrip(trip1);
        Optional<Trip> optionalTrip2 = tripDAO.createTrip(trip2);
        Optional<Trip> optionalTrip3 = tripDAO.createTrip(trip3);
        if (optionalTrip1.isPresent()&& optionalTrip2.isPresent() && optionalTrip3.isPresent())
            return true;
        return false;
    }





}