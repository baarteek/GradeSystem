package com.gradingsystem.server;

import java.sql.*;

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
                + " klasa_id integer AUTO_INCREMENT PRIMARY KEY,\n"
                + " nazwa text NOT NULL\n"
                + ");";

        String uczen = "CREATE TABLE IF NOT EXISTS uczen (\n" +
                "    uczen_id INTEGER AUTO_INCREMENT PRIMARY KEY,\n" +
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
                "    nauczyciel_id INTEGER AUTO_INCREMENT PRIMARY KEY,\n" +
                "    imie TEXT,\n" +
                "    nazwisko TEXT,\n" +
                "    pesel TEXT,\n" +
                "    email TEXT,\n" +
                "    telefon TEXT,\n" +
                "    haslo TEXT\n" +
                ");\n";

        String przedmiot = "CREATE TABLE IF NOT EXISTS przedmiot (\n" +
                "    przedmiot_id INTEGER AUTO_INCREMENT PRIMARY KEY,\n" +
                "    nazwa TEXT\n" +
                ");\n";

        String zajecia = "CREATE TABLE IF NOT EXISTS zajecia (\n" +
                "    zajecia_id INTEGER AUTO_INCREMENT PRIMARY KEY,\n" +
                "    nauczyciel_id INTEGER,\n" +
                "    przedmiot_id INTEGER,\n" +
                "    FOREIGN KEY(nauczyciel_id) REFERENCES nauczyciel(nauczyciel_id),\n" +
                "    FOREIGN KEY(przedmiot_id) REFERENCES przedmiot(przedmiot_id)\n" +
                ");\n";

        String zajecia_uczen = "CREATE TABLE IF NOT EXISTS zajecia_uczen (\n" +
                "    zajecia_uczen_id INTEGER AUTO_INCREMENT PRIMARY KEY,\n" +
                "    uczen_id INTEGER,\n" +
                "    zajecia_id INTEGER,\n" +
                "    FOREIGN KEY (uczen_id) REFERENCES uczen(uczen_id),\n" +
                "    FOREIGN KEY (zajecia_id) REFERENCES zajecia(zajecia_id)\n" +
                ");\n";

        String ocena = "CREATE TABLE IF NOT EXISTS ocena (\n" +
                "    ocena_id INTEGER AUTO_INCREMENT PRIMARY KEY,\n" +
                "    ocena INTEGER,\n" +
                "    waga INTEGER\n" +
                ");\n";

        String oceny_uczniow_na_zajeciach = "CREATE TABLE IF NOT EXISTS oceny_uczniow_na_zajeciach (\n" +
                "    oceny_ucz_na_zaj INTEGER AUTO_INCREMENT PRIMARY KEY,\n" +
                "    zajecia_uczen_id INTEGER,\n" +
                "    ocena_id INTEGER,\n" +
                "    data TEXT,\n" +
                "    FOREIGN KEY (zajecia_uczen_id) REFERENCES zajecia_uczen(zajecia_uczen_id),\n" +
                "    FOREIGN KEY (ocena_id) REFERENCES ocena(ocena_id)\n" +
                ");\n";

        String konwersacja = "CREATE TABLE IF NOT EXISTS konwersacje (\n" +
                "    id_konwersacji INTEGER AUTO_INCREMENT PRIMARY KEY,\n" +
                "    typ_nadawcy_student BOOL,\n" +
                "    typ_odbiorcy_student BOOL,\n" +
                "    id_nadawcy INTEGER,\n" +
                "    id_odbiorcy INTEGER\n" +
                ");\n";

        String wiadomosc = "CREATE TABLE IF NOT EXISTS wiadomosc (\n" +
                "    id_wiadomosci INTEGER AUTO_INCREMENT PRIMARY KEY,\n" +
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

    public static void add_klasa(String nazwa) {
        String insertKlasa = "INSERT INTO klasa (nazwa) VALUES (?)";
        try {
            PreparedStatement stmtKlasa = conn.prepareStatement(insertKlasa);
            stmtKlasa.setString(2, nazwa);
            stmtKlasa.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Insert into klasa error");
        }
    }

    public static void add_uczen(int klasa_id, String imie, String nazwisko, String pesel, String email, String telefon, String haslo) {
        String insertUczen = "INSERT INTO uczen (klasa_id, imie, nazwisko, pesel, email, telefon, haslo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmtUczen = conn.prepareStatement(insertUczen);
            stmtUczen.setInt(1, klasa_id);
            stmtUczen.setString(2, imie);
            stmtUczen.setString(3, nazwisko);
            stmtUczen.setString(4, pesel);
            stmtUczen.setString(5, email);
            stmtUczen.setString(6, telefon);
            stmtUczen.setString(7, haslo);
            stmtUczen.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Insert into uczen error");
        }
    }

    public static void add_nauczyciel(String imie, String nazwisko, String pesel, String email, String telefon, String haslo) {
        String insertNauczyciel = "INSERT INTO nauczyciel (imie, nazwisko, pesel, email, telefon, haslo) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmtNauczyciel = conn.prepareStatement(insertNauczyciel);
            stmtNauczyciel.setString(1, imie);
            stmtNauczyciel.setString(2, nazwisko);
            stmtNauczyciel.setString(3, pesel);
            stmtNauczyciel.setString(4, email);
            stmtNauczyciel.setString(5, telefon);
            stmtNauczyciel.setString(6, haslo);
            stmtNauczyciel.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Insert into nauczyciel error");
        }
    }

    public static void add_przedmiot(String nazwa) {
        String insertPrzedmiot = "INSERT INTO przedmiot (nazwa) VALUES (?)";
        try {
            PreparedStatement stmtPrzedmiot = conn.prepareStatement(insertPrzedmiot);
            stmtPrzedmiot.setString(1, nazwa);
            stmtPrzedmiot.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Insert into przedmiot error");
        }
    }

    public static void add_zajecia(int nauczyciel_id, int przedmiot_id) {
        String insertZajecia = "INSERT INTO zajecia (nauczyciel_id, przedmiot_id) VALUES (?, ?)";
        try {
            PreparedStatement stmtZajecia = conn.prepareStatement(insertZajecia);
            stmtZajecia.setInt(1, nauczyciel_id);
            stmtZajecia.setInt(2, przedmiot_id);
            stmtZajecia.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Insert into zajecia error");
        }
    }

    public static void add_zajecia_uczen() {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO zajecia_uczen (uczen_id, zajecia_id) VALUES (?, ?)");
            pstmt.setNull(1, java.sql.Types.INTEGER);
            pstmt.setInt(2, 1);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Insert into zajecia_uczen error");
        }
    }

    public static void add_ocena(int ocena, int waga) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ocena (ocena, waga) VALUES (?, ?)");
            pstmt.setInt(1, ocena);
            pstmt.setInt(2, waga);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Insert into ocena error");
        }
    }

    public static void add_oceny_uczniow_na_zajeciach(int zajecia_uczen_id, int ocena_id, String data) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO oceny_uczniow_na_zajeciach (zajecia_uczen_id, ocena_id, data) VALUES (?, ?, ?)");
            pstmt.setInt(1, zajecia_uczen_id);
            pstmt.setInt(2, ocena_id);
            pstmt.setString(3, data); //2023-05-02
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Insert into oceny_uczniow_na_zajeciach error");
        }
    }

    public static void add_konwersacje(Boolean typ_nadawcy_student, Boolean typ_odbiorcy_student, int id_nadawcy, int id_odbiorcy) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO konwersacje (typ_nadawcy_student, typ_odbiorcy_student, id_nadawcy, id_odbiorcy) VALUES (?, ?, ?, ?)");
            pstmt.setBoolean(1, typ_nadawcy_student);
            pstmt.setBoolean(2, typ_odbiorcy_student);
            pstmt.setInt(3, id_nadawcy);
            pstmt.setInt(4, id_odbiorcy);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Insert into konwersacje error");
        }
    }

    public static void add_wiadomosc(String data, int id_konwersacji, String tresc) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO wiadomosc (data, id_konwersacji, tresc) VALUES (?, ?, ?)");
            pstmt.setString(1, data);
            pstmt.setInt(2, id_konwersacji);
            pstmt.setString(3, tresc);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Insert into wiadomosc error");
        }
    }
}
