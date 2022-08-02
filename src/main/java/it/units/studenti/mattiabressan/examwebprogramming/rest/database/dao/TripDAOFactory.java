package it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao;

import it.units.studenti.mattiabressan.examwebprogramming.rest.config.DbConfig;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.connection.Connection;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.connection.ConnectionFactory;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.dao.mysql.MySQLTripDAO;

public class TripDAOFactory {

    public static TripDAO getTripDAO() {
        // get connection
        Connection connection = ConnectionFactory.getConnection();

        // use driver specified according to database
        switch( DbConfig.getDbType() ) {
            case MYSQL:
                return new MySQLTripDAO( connection );
            default:
                // should not happen: we test for correct input in DbConfig.java
                return null;
        }
    }
}
