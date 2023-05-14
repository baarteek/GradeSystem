package com.gradingsystem.server;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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
            stmtKlasa.setString(1, nazwa);
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

    public static void add_zajecia_uczen(int uczen_id, int zajecia_id) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO zajecia_uczen (uczen_id, zajecia_id) VALUES (?, ?)");
            pstmt.setInt(1, uczen_id);
            pstmt.setInt(2, zajecia_id);
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

    public static Map<String, Object> getAllFieldsFromTable(String tableName) {
        Map<String, Object> result = new HashMap<>();

        try {
            Statement statement = conn.createStatement();
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, tableName, null);

            if (tables.next()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
                if (resultSet.next()) {
                    ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                    int columnCount = resultSetMetaData.getColumnCount();
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = resultSetMetaData.getColumnName(i);
                        Object columnValue = resultSet.getObject(i);
                        result.put(columnName, columnValue);
                    }
                } else {
                    System.out.println("Table " + tableName + " is empty.");
                }
                statement.close();
            } else {
                System.out.println("Table " + tableName + " doesn't exist.");
            }
        } catch (SQLException e) {
            System.out.println("Cannot fetch from " + tableName);
            e.printStackTrace();
        }

        return result;
    }

    public static String checkCredentials(String login, String password) {
        String selectUczen = "SELECT * FROM uczen WHERE email = ? AND haslo = ?";
        String selectNauczyciel = "SELECT * FROM nauczyciel WHERE email = ? AND haslo = ?";

        try {
            PreparedStatement stmtUczen = conn.prepareStatement(selectUczen);
            stmtUczen.setString(1, login);
            stmtUczen.setString(2, password);
            ResultSet rsUczen = stmtUczen.executeQuery();

            PreparedStatement stmtNauczyciel = conn.prepareStatement(selectNauczyciel);
            stmtNauczyciel.setString(1, login);
            stmtNauczyciel.setString(2, password);
            ResultSet rsNauczyciel = stmtNauczyciel.executeQuery();

            if (rsUczen.next()) {
                return "LOGIN_SUCCESS|STUDENT|" + rsUczen.getInt("uczen_id");
            } else if(rsNauczyciel.next()) {
                return "LOGIN_SUCCESS|TEACHER|" + rsNauczyciel.getInt("nauczyciel_id");
            }
        } catch (SQLException e) {
            System.out.println("Error during checking credentials");
            e.printStackTrace();
        }
        return "LOGIN_FAILURE|ST";
    }

    public static String checkIfDataExists(String tableName, String[] columnNames, String[] values) {
        if (columnNames.length != values.length) {
            throw new IllegalArgumentException("The number of column names must be equal to the number of values");
        }

        for (int i = 0; i < columnNames.length; i++) {
            String query = "SELECT * FROM " + tableName + " WHERE " + columnNames[i] + " = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, values[i]);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {;
                        return "FAILURE|"+columnNames[i].toUpperCase();
                    }
                }
            } catch (SQLException e) {
                System.out.println("An error occurred while checking data: " + e.getMessage());
            }
        }
        return "SUCCESS";
    }

    public static String getUserDataById(String tableName, int userId) {
        String selectQuery = "SELECT * FROM " + tableName + " WHERE " + tableName + "_id = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(selectQuery);
            statement.setInt(1, userId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                StringBuilder userData = new StringBuilder();
                int columnCount = resultSet.getMetaData().getColumnCount();
                for (int i = 1; i <= columnCount; i++) {
                    userData.append(resultSet.getString(i));
                    if (i < columnCount) {
                        userData.append("|");
                    }
                }
                return userData.toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String changeUserData(String tableName, int userID, String column, String value) {
        String updateQuery = "UPDATE " + tableName + " SET " + column + " = ? WHERE " + tableName + "_id = ?";
        int rowsAffected = 0;

        try (PreparedStatement statement = conn.prepareStatement(updateQuery)) {

            statement.setString(1, value);
            statement.setInt(2, userID);

            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (rowsAffected > 0) {
            return "CHANGE_USER_DATA_SUCCESS";
        } else {
            return "CHANGE_USER_DATA_FAILURE";
        }
    }

        public static void add_test_data() {
        add_klasa("3A");
        add_klasa("3B");
        add_klasa("3C");

        add_uczen(1, "Jan", "Kowalski", "12345678901", "jan.kowalski@school.com", "123456789", "password1");
        add_uczen(1, "Anna", "Nowak", "12345678902", "anna.nowak@school.com", "234567890", "password2");
        add_uczen(2, "Piotr", "Kowalski", "12345678903", "piotr.kowalski@school.com", "345678901", "password3");
        add_uczen(2, "Maria", "Kowalczyk", "12345678904", "maria.kowalczyk@school.com", "456789012", "password4");

        add_nauczyciel("Adam", "Nowak", "12345678905", "adam.nowak@school.com", "567890123", "password5");
        add_nauczyciel("Katarzyna", "Kowalska", "12345678906", "katarzyna.kowalska@school.com", "678901234", "password6");

        add_przedmiot("matematyka");
        add_przedmiot("j. polski");
        add_przedmiot("historia");

        add_zajecia(1, 1);
        add_zajecia(1, 2);
        add_zajecia(2, 3);

        add_zajecia_uczen(1, 1);
        add_zajecia_uczen(2, 1);
        add_zajecia_uczen(1, 2);
        add_zajecia_uczen(2, 2);

        add_ocena(4, 1);
        add_ocena(5, 2);
        add_ocena(3, 1);

        add_oceny_uczniow_na_zajeciach(1, 1, "2023-05-01");
        add_oceny_uczniow_na_zajeciach(1, 2, "2023-05-01");
        add_oceny_uczniow_na_zajeciach(2, 3, "2023-05-01");

        add_konwersacje(false, true, 1, 2);
        add_konwersacje(true, false, 2, 1);
    }
}
