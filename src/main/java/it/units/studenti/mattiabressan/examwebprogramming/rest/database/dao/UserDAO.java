package it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao;

import java.util.List;
import java.util.Optional;

import it.units.studenti.mattiabressan.examwebprogramming.rest.exception.UserNotFoundException;
import it.units.studenti.mattiabressan.examwebprogramming.rest.exception.UserExistingException;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.User;
import it.units.studenti.mattiabressan.examwebprogramming.rest.model.UserSecurity;


public interface UserDAO {
    public List<User> findAll() ;
    public Optional<User> findById(Integer id);
    public Optional<User> findByEmail(String email);
    public Optional<User> findByUsername(String username);
    public Optional<User> createUser( UserSecurity user );
    public Optional<UserSecurity> getUserAuthentication( Integer id );
    public Optional<UserSecurity> setUserAuthentication( UserSecurity user );
    public Optional<User> updateUser( User user ) ;
    public boolean deleteUser( Integer id ) ;



    //public boolean createUser( UserSecurity user ) throws UserExistingException;
    //public int getUserIdByEmail( String email ) throws UserNotFoundException;
    //public User getUserByUsername(String username )  throws UserNotFoundException;
    //public User getUser( int id ) throws UserNotFoundException;

}
