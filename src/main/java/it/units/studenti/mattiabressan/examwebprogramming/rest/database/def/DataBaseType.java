package it.units.studenti.mattiabressan.examwebprogramming.rest.database.def;

public enum DataBaseType {
    ORIENTDB("ORIENTDB"),
    SQLITE("SQLITE");

    private final String val;

    private DataBaseType( String s ) {
        val = s;
    }

    public String toString() {
        return this.val;
    }
}