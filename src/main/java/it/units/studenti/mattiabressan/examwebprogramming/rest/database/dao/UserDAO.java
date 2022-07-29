package it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao;

import java.util.List;

import it.units.studenti.mattiabressan.examwebprogramming.rest.exception.UserNotFoundException;
import it.units.studenti.mattiabressan.examwebprogramming.rest.exception.UserExistingException;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.User;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.UserSecurity;


public interface UserDAO {
    public boolean createUser( UserSecurity user ) throws UserExistingException;

    public String getUserIdByEmail( String email ) throws UserNotFoundException;
    public User getUser( String id ) throws UserNotFoundException;

    public List<User> getAllUsers();

    public UserSecurity getUserAuthentication( String id ) throws UserNotFoundException;
    public boolean setUserAuthentication( UserSecurity user ) throws UserNotFoundException;

    public boolean updateUser( User user ) throws UserNotFoundException;
    public boolean deleteUser( String id ) throws UserNotFoundException;
}
