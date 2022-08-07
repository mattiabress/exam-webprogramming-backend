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
    private static final String INSERT_USER="INSERT INTO USER(email,username,firstname,lastname,password,role) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_ALL_USER="SELECT id, firstname, lastname, email,username FROM USER;";
    private static final String SELECT_USER_BY_ID="SELECT id, firstname, lastname, email,username FROM USER WHERE id=?;";
    private static final String SELECT_USER_BY_MAIL="SELECT id, firstname, lastname, email,username FROM USER WHERE email=?;";
    private static final String SELECT_USER_BY_USERNAME="SELECT id, firstname, lastname, email,username FROM USER WHERE username=?;";
    private static final String SELECT_USER_AUTHENTICATION_BY_ID="SELECT email, password, token, role,username FROM USER WHERE id=?;";
    private static final String DELETE_USER_BY_ID="DELETE FROM USER WHERE id=?";
    private static final String LOGOUT_USER_BY_ID="UPDATE `user` SET `TOKEN` = NULL WHERE `user`.`ID` = ?";
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
                Integer userId = rs.getInt("id");
                String email = rs.getString("email");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String username = rs.getString("username");
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
                Integer userId = rs.getInt("id");
                String email = rs.getString("email");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String username = rs.getString("username");
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
                Integer userId = rs.getInt("id");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
                String username = rs.getString("username");
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
                Integer userId = rs.getInt("id");
                String email = rs.getString("email");
                String firstname = rs.getString("firstname");
                String lastname = rs.getString("lastname");
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
                String email = rs.getString("email");
                String password = rs.getString("password");
                String token = rs.getString("token");
                String role = rs.getString("role");
                String username = rs.getString("username");

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
                query.append("password=?");
                comma = true;
                prepare.add(user.getPassword());
            }

            if (user.getToken() != null) {
                if (comma) query.append(",");
                query.append("token=?");
                comma = true;
                prepare.add(user.getToken());
            }

            if (user.getRole() != null) {
                if (comma) query.append(",");
                query.append("role=?");
                comma = true;
                prepare.add(user.getRole());
            }
            if (user.getUsername() != null) {
                if (comma) query.append(",");
                query.append("username=?");
                prepare.add(user.getUsername());
            }
            query.append(" WHERE id=?");
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
                query.append("firstname=?");
                comma = true;
                prepare.add(user.getFirstname());
            }

            if (user.getLastname() != null) {
                if (comma) query.append(",");
                query.append("lastname=?");
                comma = true;
                prepare.add(user.getLastname());
            }
            if (user.getUsername() != null) {
                if (comma) query.append(",");
                query.append("username=?");
                comma = true;
                prepare.add(user.getUsername());
            }
            if (user.getEmail() != null) {
                if (comma) query.append(",");
                query.append("email=?");
                prepare.add(user.getEmail());
            }

            query.append(" WHERE id=?");
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