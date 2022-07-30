package it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao;

import it.units.studenti.mattiabressan.examwebprogramming.rest.config.DbConfig;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.connection.Connection;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.connection.ConnectionFactory;
//import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.gremlin.GremlinUserDAO;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.mysql.MySQLUserDAO;
//import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.sqlite.SqlUserDAO;


public class UserDAOFactory {

    public static UserDAO getUserDAO() {
        // get connection
        Connection connection = ConnectionFactory.getConnection();

        // use driver specified according to database
        switch( DbConfig.getDbType() ) {/*
            case ORIENTDB:
                return new GremlinUserDAO( connection );
            case SQLITE:
                return new SqlUserDAO( connection );*/
            case MYSQL:
                return new MySQLUserDAO( connection );
            default:
                // should not happen: we test for correct input in DbConfig.java
                return null;
        }
    }
}
