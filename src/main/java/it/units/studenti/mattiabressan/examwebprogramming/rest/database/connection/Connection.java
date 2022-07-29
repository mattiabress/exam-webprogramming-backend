package it.units.studenti.mattiabressan.examwebprogramming.rest.database.connection;

public interface Connection {
    public Object get();
    public boolean open();
    public boolean close();
}