package it.units.studenti.mattiabressan.examwebprogramming.rest.database.def;

public enum DataBaseType {
    MYSQL("MYSQL");

    private final String val;

    private DataBaseType( String s ) {
        val = s;
    }

    public String toString() {
        return this.val;
    }
}