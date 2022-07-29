package it.units.studenti.mattiabressan.examwebprogramming.rest.database.connection;

import it.units.studenti.mattiabressan.examwebprogramming.rest.config.DbConfig;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.connection.orientdb.OrientDbConnection;
import it.units.studenti.mattiabressan.examwebprogramming.rest.database.connection.sqlite.SQLiteConnection;


public class ConnectionFactory {

    private static Connection connection = null;

    public static Connection getConnection() {
        if( connection != null ) return connection;

        switch( DbConfig.getDbType() ) {
            case ORIENTDB:
                connection = new OrientDbConnection();
            case SQLITE:
                connection = new SQLiteConnection();
            default:
                break;
        }

        // open connection
        connection.open();

        return connection;
    }
}