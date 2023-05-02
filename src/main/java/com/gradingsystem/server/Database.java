package com.gradingsystem.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private static Connection conn = null;

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:grading_system.db");
            System.out.println("Connected with database.");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            if (conn != null) {
                conn.close();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void create_tables() {
        String klasa = "CREATE TABLE IF NOT EXISTS klasa (\n"
                + " klasa_id integer PRIMARY KEY,\n"
                + " nazwa text NOT NULL\n"
                + ");";

        String uczen = "CREATE TABLE IF NOT EXISTS uczen (\n" +
                "    uczen_id INTEGER PRIMARY KEY,\n" +
                "    klasa_id INTEGER,\n" +
                "    imie TEXT,\n" +
                "    nazwisko TEXT,\n" +
                "    pesel TEXT,\n" +
                "    email TEXT,\n" +
                "    telefon TEXT,\n" +
                "    haslo TEXT,\n" +
                "    FOREIGN KEY (klasa_id) REFERENCES klasa(klasa_id)\n" +
                ");\n";

        String nauczyciel = "CREATE TABLE IF NOT EXISTS nauczyciel (\n" +
                "    nauczyciel_id INTEGER PRIMARY KEY,\n" +
                "    imie TEXT,\n" +
                "    nazwisko TEXT,\n" +
                "    pesel TEXT,\n" +
                "    email TEXT,\n" +
                "    telefon TEXT,\n" +
                "    haslo TEXT\n" +
                ");\n";

        String przedmiot = "CREATE TABLE IF NOT EXISTS przedmiot (\n" +
                "    przedmiot_id INTEGER PRIMARY KEY,\n" +
                "    nazwa TEXT\n" +
                ");\n";

        String zajecia = "CREATE TABLE IF NOT EXISTS zajecia (\n" +
                "    zajecia_id INTEGER PRIMARY KEY,\n" +
                "    nauczyciel_id INTEGER,\n" +
                "    przedmiot_id INTEGER,\n" +
                "    FOREIGN KEY(nauczyciel_id) REFERENCES nauczyciel(nauczyciel_id),\n" +
                "    FOREIGN KEY(przedmiot_id) REFERENCES przedmiot(przedmiot_id)\n" +
                ");\n";

        String zajecia_uczen = "CREATE TABLE IF NOT EXISTS zajecia_uczen (\n" +
                "    zajecia_uczen_id INTEGER PRIMARY KEY,\n" +
                "    uczen_id INTEGER,\n" +
                "    zajecia_id INTEGER,\n" +
                "    FOREIGN KEY (uczen_id) REFERENCES uczen(uczen_id),\n" +
                "    FOREIGN KEY (zajecia_id) REFERENCES zajecia(zajecia_id)\n" +
                ");\n";

        String ocena = "CREATE TABLE IF NOT EXISTS ocena (\n" +
                "    ocena_id INTEGER PRIMARY KEY,\n" +
                "    ocena INTEGER,\n" +
                "    waga INTEGER\n" +
                ");\n";

        String oceny_uczniow_na_zajeciach = "CREATE TABLE IF NOT EXISTS oceny_uczniow_na_zajeciach (\n" +
                "    oceny_ucz_na_zaj INTEGER PRIMARY KEY,\n" +
                "    zajecia_uczen_id INTEGER,\n" +
                "    ocena_id INTEGER,\n" +
                "    data TEXT,\n" +
                "    FOREIGN KEY (zajecia_uczen_id) REFERENCES zajecia_uczen(zajecia_uczen_id),\n" +
                "    FOREIGN KEY (ocena_id) REFERENCES ocena(ocena_id)\n" +
                ");\n";

        String konwersacja = "CREATE TABLE IF NOT EXISTS konwersacje (\n" +
                "    id_konwersacji INTEGER PRIMARY KEY,\n" +
                "    typ_nadawcy_student BOOL,\n" +
                "    typ_odbiorcy_student BOOL,\n" +
                "    id_nadawcy INTEGER,\n" +
                "    id_odbiorcy INTEGER\n" +
                ");\n";

        String wiadomosc = "CREATE TABLE IF NOT EXISTS wiadomosc (\n" +
                "    id_wiadomosci INTEGER PRIMARY KEY,\n" +
                "    data DATETIME,\n" +
                "    id_konwersacji INTEGER,\n" +
                "    tresc TEXT,\n" +
                "    FOREIGN KEY (id_konwersacji) REFERENCES konwersacje(id_konwersacji)\n" +
                ");\n";

        create_table(klasa);
        create_table(uczen);
        create_table(nauczyciel);
        create_table(przedmiot);
        create_table(zajecia);
        create_table(zajecia_uczen);
        create_table(ocena);
        create_table(oceny_uczniow_na_zajeciach);
        create_table(konwersacja);
        create_table(wiadomosc);
    }

    private static void create_table(String sql) {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
