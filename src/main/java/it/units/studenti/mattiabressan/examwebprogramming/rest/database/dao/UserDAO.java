package it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao;

import java.util.List;
import java.util.Optional;

import it.units.studenti.mattiabressan.examwebprogramming.rest.exception.UserNotFoundException;
import it.units.studenti.mattiabressan.examwebprogramming.rest.exception.UserExistingException;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.User;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.UserSecurity;


public interface UserDAO {


    public List<User> findAll() ;

    public Optional<User> findById(Integer id) throws UserNotFoundException;








    public boolean createUser( UserSecurity user ) throws UserExistingException;

    public int getUserIdByEmail( String email ) throws UserNotFoundException;
    public User getUserByUsername(String username )  throws UserNotFoundException;
    public User getUser( int id ) throws UserNotFoundException;

    public List<User> getAllUsers();

    public UserSecurity getUserAuthentication( int id ) throws UserNotFoundException;
    public boolean setUserAuthentication( UserSecurity user ) throws UserNotFoundException;

    public boolean updateUser( User user ) throws UserNotFoundException;
    public boolean deleteUser( int id ) throws UserNotFoundException;
}
