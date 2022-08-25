package it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.UserDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.exception.UserExistingException;
import it.units.studenti.mattiabressan.examwebprogramming.rest.exception.UserNotFoundException;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.User;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.UserSecurity;

public class MySQLUserDAO implements UserDAO {

    public final static Logger logger = Logger.getLogger(MySQLUserDAO.class);

    private Connection connection = null;

    //query
    private static final String INSERT_USER="INSERT INTO USER(EMAIL,USERNAME,FIRSTNAME,LASTNAME,PASSWORD,ROLE) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_ALL_USER="SELECT ID, FIRSTNAME, LASTNAME, EMAIL,USERNAME FROM USER;";
    private static final String SELECT_USER_BY_ID="SELECT ID, FIRSTNAME, LASTNAME, EMAIL,USERNAME FROM USER WHERE ID=?;";
    private static final String SELECT_USER_BY_MAIL="SELECT ID, FIRSTNAME, LASTNAME, EMAIL,USERNAME FROM USER WHERE EMAIL=?;";
    private static final String SELECT_USER_BY_USERNAME="SELECT ID, FIRSTNAME, LASTNAME, EMAIL,USERNAME FROM USER WHERE USERNAME=?;";
    private static final String SELECT_USER_AUTHENTICATION_BY_ID="SELECT EMAIL, PASSWORD, TOKEN, ROLE,USERNAME FROM USER WHERE ID=?;";
    private static final String DELETE_USER_BY_ID="DELETE FROM USER WHERE ID=?";
    private static final String LOGOUT_USER_BY_ID="UPDATE `USER` SET `TOKEN` = NULL WHERE `USER`.`ID` = ?";
    public MySQLUserDAO(it.units.studenti.mattiabressan.examwebprogramming.rest.database.connection.Connection connection) {
        this.connection = (Connection) connection.get();
    }

    @Override
    public List<User> findAll() {
        logger.debug("getAllUsers");
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<User> users = new ArrayList<User>();
        try {
            stmt = connection.prepareStatement(SELECT_ALL_USER);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Integer userId = rs.getInt("ID");
                String email = rs.getString("EMAIL");
                String firstname = rs.getString("FIRSTNAME");
                String lastname = rs.getString("LASTNAME");
                String username = rs.getString("USERNAME");
                users.add(new User(userId, email, firstname, lastname,username));
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
        return users;
    }

    @Override
    public Optional<User> findById(Integer id){
        logger.debug("getUser: " + id);

        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;

        try {
            stmt = connection.prepareStatement(SELECT_USER_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Integer userId = rs.getInt("ID");
                String email = rs.getString("EMAIL");
                String firstname = rs.getString("FIRSTNAME");
                String lastname = rs.getString("LASTNAME");
                String username = rs.getString("USERNAME");
                user = new User(userId, email, firstname, lastname,username);
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
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        logger.debug("findByEmail: " + email);
        Integer id = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user=null;
        try {
            stmt = connection.prepareStatement(SELECT_USER_BY_MAIL);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Integer userId = rs.getInt("ID");
                String firstname = rs.getString("FIRSTNAME");
                String lastname = rs.getString("LASTNAME");
                String username = rs.getString("USERNAME");
                user = new User(userId, email, firstname, lastname,username);
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

        return Optional.of(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        logger.debug("findByUsername: " + username);

        Integer id = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user=null;
        try {
            stmt = connection.prepareStatement(SELECT_USER_BY_USERNAME);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Integer userId = rs.getInt("ID");
                String email = rs.getString("EMAIL");
                String firstname = rs.getString("FIRSTNAME");
                String lastname = rs.getString("LASTNAME");
                user = new User(userId, email, firstname, lastname,username);
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

        return Optional.of(user);
    }


    @Override
    public Optional<User> createUser(UserSecurity user){
        logger.debug("createUser: " + user.getEmail());
        PreparedStatement stmt = null;
        try {
            // check if user already registered
            if (findByEmail(user.getEmail()).isPresent()) {
                return Optional.empty();
            }

            stmt = connection.prepareStatement(INSERT_USER);
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getFirstname());
            stmt.setString(4, user.getLastname());
            stmt.setString(5, user.getPassword());
            stmt.setString(6, user.getRole());
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

        return Optional.of(user);
    }




    @Override
    public Optional<UserSecurity> getUserAuthentication(Integer id){
        logger.debug("getUserAuthentication: " + id);
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UserSecurity userSecurity = null;
        try {
            stmt = connection.prepareStatement(SELECT_USER_AUTHENTICATION_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String email = rs.getString("EMAIL");
                String password = rs.getString("PASSWORD");
                String token = rs.getString("TOKEN");
                String role = rs.getString("ROLE");
                String username = rs.getString("USERNAME");

                userSecurity = new UserSecurity(email, password, token, role,username);
            } else {
                Optional.empty();
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

        return Optional.of(userSecurity);
    }

    @Override
    public Optional<UserSecurity> setUserAuthentication(UserSecurity user) {
        logger.debug("setUserAuthentication: " + user.getId());
        PreparedStatement stmt = null;

        try {
            // prepare query
            StringBuffer query = new StringBuffer();
            query.append("UPDATE USER SET ");

            boolean comma = false;
            List<String> prepare = new ArrayList<String>();
            if (user.getPassword() != null) {
                query.append("PASSWORD=?");
                comma = true;
                prepare.add(user.getPassword());
            }

            if (user.getToken() != null) {
                if (comma) query.append(",");
                query.append("TOKEN=?");
                comma = true;
                prepare.add(user.getToken());
            }

            if (user.getRole() != null) {
                if (comma) query.append(",");
                query.append("ROLE=?");
                comma = true;
                prepare.add(user.getRole());
            }
            if (user.getUsername() != null) {
                if (comma) query.append(",");
                query.append("USERNAME=?");
                prepare.add(user.getUsername());
            }
            query.append(" WHERE ID=?");
            stmt = connection.prepareStatement(query.toString());
            for (int i = 0; i < prepare.size(); i++) {
                stmt.setString(i + 1, prepare.get(i));
            }
            stmt.setInt(prepare.size() + 1, user.getId());

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
        return Optional.of(user);
    }

    @Override
    public Optional<User> updateUser(User user) {
        logger.debug("updateUser: " + user.getId());
        PreparedStatement stmt = null;
        try {
            // prepare query
            StringBuffer query = new StringBuffer();
            query.append("UPDATE USER SET ");

            boolean comma = false;
            List<String> prepare = new ArrayList<String>();
            if (user.getFirstname() != null) {
                query.append("FIRSTNAME=?");
                comma = true;
                prepare.add(user.getFirstname());
            }

            if (user.getLastname() != null) {
                if (comma) query.append(",");
                query.append("LASTNAME=?");
                comma = true;
                prepare.add(user.getLastname());
            }
            if (user.getUsername() != null) {
                if (comma) query.append(",");
                query.append("USERNAME=?");
                comma = true;
                prepare.add(user.getUsername());
            }
            if (user.getEmail() != null) {
                if (comma) query.append(",");
                query.append("EMAIL=?");
                prepare.add(user.getEmail());
            }

            query.append(" WHERE ID=?");
            stmt = connection.prepareStatement(query.toString());

            for (int i = 0; i < prepare.size(); i++) {
                stmt.setString(i + 1, prepare.get(i));
            }

            stmt.setInt(prepare.size() + 1, user.getId());

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

        return Optional.of(user);
    }

    public boolean deleteUser(Integer id) {
        logger.debug("deleteUser: " + id);
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(DELETE_USER_BY_ID);
            stmt.setInt(1, id);
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
    public boolean logout(Integer id) {
        logger.debug("logoutUser: " + id);
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(LOGOUT_USER_BY_ID);
            stmt.setInt(1, id);
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
}