package it.units.studenti.mattiabressan.examwebprogramming.rest.database.connection.mysql;

import it.units.studenti.mattiabressan.examwebprogramming.rest.config.DbConfig;
import it.units.studenti.mattiabressan.examwebprogramming.rest.security.PasswordSecurity;
import org.apache.log4j.Logger;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;

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
                    "`PASSWORD` VARCHAR(50) NOT NULL , " +
                    "`TOKEN` VARCHAR(250)  , " +
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

    private void createAdminUser() {
        if (checkForAdminUser()) return;

        PreparedStatement stmt = null;

        try {
            stmt = connection.prepareStatement("INSERT INTO `user` (`ID`, `EMAIL`, `USERNAME`, `FIRSTNAME`, `LASTNAME`, `PASSWORD`,  `ROLE`) " +
                            "VALUES (NULL, ?,?,?, ?, ?,?, ? )"
                    );

            stmt.setString(1, "root");
            stmt.setString(2, "root");
            stmt.setString(3, "root");
            stmt.setString(4, PasswordSecurity.generateHash("root"));
            stmt.setString(5, "root");
            stmt.setString(7, "root");
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

}