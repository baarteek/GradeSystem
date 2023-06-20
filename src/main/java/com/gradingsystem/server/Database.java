package com.gradingsystem.server;

import java.sql.*;
import java.util.*;

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

    public static void connect(String dbName) {
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(dbName);
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
                + " klasa_id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " nazwa text NOT NULL\n"
                + ");";

        String uczen = "CREATE TABLE IF NOT EXISTS uczen (\n" +
                "    uczen_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
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
                "    nauczyciel_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    imie TEXT,\n" +
                "    nazwisko TEXT,\n" +
                "    pesel TEXT,\n" +
                "    email TEXT,\n" +
                "    telefon TEXT,\n" +
                "    haslo TEXT\n" +
                ");\n";

        String przedmiot = "CREATE TABLE IF NOT EXISTS przedmiot (\n" +
                "    przedmiot_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    nazwa TEXT\n" +
                ");\n";

        String zajecia = "CREATE TABLE IF NOT EXISTS zajecia (\n" +
                "    zajecia_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    nauczyciel_id INTEGER,\n" +
                "    przedmiot_id INTEGER,\n" +
                "    FOREIGN KEY(nauczyciel_id) REFERENCES nauczyciel(nauczyciel_id),\n" +
                "    FOREIGN KEY(przedmiot_id) REFERENCES przedmiot(przedmiot_id)\n" +
                ");\n";

        String zajecia_uczen = "CREATE TABLE IF NOT EXISTS zajecia_uczen (\n" +
                "    zajecia_uczen_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    uczen_id INTEGER,\n" +
                "    zajecia_id INTEGER,\n" +
                "    rok TEXT,\n" +
                "    FOREIGN KEY (uczen_id) REFERENCES uczen(uczen_id),\n" +
                "    FOREIGN KEY (zajecia_id) REFERENCES zajecia(zajecia_id)\n" +
                ");\n";


        String ocena = "CREATE TABLE IF NOT EXISTS ocena (\n" +
                "    ocena_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    ocena INTEGER,\n" +
                "    waga INTEGER\n" +
                ");\n";

        String oceny_uczniow_na_zajeciach = "CREATE TABLE IF NOT EXISTS oceny_uczniow_na_zajeciach (\n" +
                "    oceny_ucz_na_zaj INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    zajecia_uczen_id INTEGER,\n" +
                "    ocena_id INTEGER,\n" +
                "    data TEXT,\n" +
                "    FOREIGN KEY (zajecia_uczen_id) REFERENCES zajecia_uczen(zajecia_uczen_id),\n" +
                "    FOREIGN KEY (ocena_id) REFERENCES ocena(ocena_id)\n" +
                ");\n";

        String konwersacja = "CREATE TABLE IF NOT EXISTS konwersacje (\n" +
                "    id_konwersacji INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    typ_nadawcy_student BOOL,\n" +
                "    typ_odbiorcy_student BOOL,\n" +
                "    id_nadawcy INTEGER,\n" +
                "    id_odbiorcy INTEGER\n" +
                ");\n";

        String wiadomosc = "CREATE TABLE IF NOT EXISTS wiadomosc (\n" +
                "    id_wiadomosci INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    data DATETIME,\n" +
                "    id_konwersacji INTEGER,\n" +
                "    tresc TEXT,\n" +
                "    FOREIGN KEY (id_konwersacji) REFERENCES konwersacje(id_konwersacji)\n" +
                ");\n";

        String klasa_przedmiot = "CREATE TABLE IF NOT EXISTS klasa_przedmiot (\n" +
                "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    klasa_id INTEGER,\n" +
                "    przedmiot_id INTEGER,\n" +
                "    FOREIGN KEY (klasa_id) REFERENCES klasa(klasa_id),\n" +
                "    FOREIGN KEY (przedmiot_id) REFERENCES przedmiot(przedmiot_id)\n" +
                ");\n";

        String admin = "CREATE TABLE IF NOT EXISTS admin (\n" +
                "    admin_id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "    imie TEXT,\n" +
                "    nazwisko TEXT,\n" +
                "    pesel TEXT,\n" +
                "    email TEXT,\n" +
                "    telefon TEXT,\n" +
                "    haslo TEXT\n" +
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
        create_table(klasa_przedmiot);
        create_table(admin);
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

    public static void add_uczen(String imie, String nazwisko, String pesel, String email, String telefon, String haslo) {
        String insertUczen = "INSERT INTO uczen (imie, nazwisko, pesel, email, telefon, haslo) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmtUczen = conn.prepareStatement(insertUczen);
            stmtUczen.setString(1, imie);
            stmtUczen.setString(2, nazwisko);
            stmtUczen.setString(3, pesel);
            stmtUczen.setString(4, email);
            stmtUczen.setString(5, telefon);
            stmtUczen.setString(6, haslo);
            stmtUczen.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert into uczen error");
            e.printStackTrace();
        }
    }

    public static void add_admin(String imie, String nazwisko, String pesel, String email, String telefon, String haslo) {
        String insertAdmin = "INSERT INTO admin (imie, nazwisko, pesel, email, telefon, haslo) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmtAdmin = conn.prepareStatement(insertAdmin);
            stmtAdmin.setString(1, imie);
            stmtAdmin.setString(2, nazwisko);
            stmtAdmin.setString(3, pesel);
            stmtAdmin.setString(4, email);
            stmtAdmin.setString(5, telefon);
            stmtAdmin.setString(6, haslo);
            stmtAdmin.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Insert into admin error");
            e.printStackTrace();
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

    public static void add_zajecia_uczen(int uczen_id, int zajecia_id, String rok) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO zajecia_uczen (uczen_id, zajecia_id, rok) VALUES (?, ?, ?)");
            pstmt.setInt(1, uczen_id);
            pstmt.setInt(2, zajecia_id);
            pstmt.setString(3, rok);
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

    public static void add_klasa_przedmiot(int klasa_id, int przedmiot_id) {
        try {
            PreparedStatement pstmt = conn.prepareStatement("INSERT INTO klasa_przedmiot (klasa_id, przedmiot_id) VALUES (?, ?)");
            pstmt.setInt(1, klasa_id);
            pstmt.setInt(2, przedmiot_id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Insert into klasa_przedmiot error");
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

    public static List<String> getAllFieldsFromTable(String tableName) {
        List<String> results = new ArrayList<>();

        try {
            Statement statement = conn.createStatement();
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, tableName, null);

            if (tables.next()) {
                ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
                ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
                int columnCount = resultSetMetaData.getColumnCount();

                while (resultSet.next()) {
                    StringBuilder result = new StringBuilder();
                    for (int i = 1; i <= columnCount; i++) {
                        result.append(resultSet.getObject(i));
                        if(i != columnCount) {
                            result.append(",");
                        }
                    }
                    results.add(result.toString());
                }

                statement.close();
            } else {
                System.out.println("Table " + tableName + " doesn't exist.");
            }
        } catch (SQLException e) {
            System.out.println("Cannot fetch from " + tableName);
            e.printStackTrace();
        }

        return Collections.singletonList(String.join("|", results));
    }

    public static boolean updateField(String tableName, String fieldName, String newValue, String condition) {
        String updateQuery = "UPDATE " + tableName + " SET " + fieldName + " = ? WHERE " + condition;
        PreparedStatement statement = null;

        try {
            statement = conn.prepareStatement(updateQuery);
            statement.setString(1, newValue);

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }


    public static List<Map<String, Object>> getStudentsWithoutClass() {
        List<Map<String, Object>> students = new ArrayList<>();
        try {
            String query = "SELECT * FROM uczen WHERE klasa_id IS NULL OR klasa_id = 0";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Map<String, Object> uczen = new HashMap<>();
                uczen.put("uczen_id", resultSet.getInt("uczen_id"));
                uczen.put("imie", resultSet.getString("imie"));
                uczen.put("nazwisko", resultSet.getString("nazwisko"));
                uczen.put("pesel", resultSet.getString("pesel"));
                uczen.put("email", resultSet.getString("email"));
                uczen.put("telefon", resultSet.getString("telefon"));
                students.add(uczen);
            }
        } catch (SQLException e) {
            System.out.println("Cannot fetch uczniowie bez klasy");
            e.printStackTrace();
        }
        return students;
    }

    public static String getStudentDetailsSorted(String sortOrder, String subjectName) {
        String query = "SELECT klasa.nazwa, uczen.uczen_id, uczen.imie, uczen.nazwisko, przedmiot.nazwa, klasa.klasa_id, przedmiot.przedmiot_id\n"
                + "FROM uczen\n"
                + "JOIN klasa ON uczen.klasa_id = klasa.klasa_id\n"
                + "JOIN klasa_przedmiot ON klasa.klasa_id = klasa_przedmiot.klasa_id\n"
                + "JOIN przedmiot ON klasa_przedmiot.przedmiot_id = przedmiot.przedmiot_id\n"
                + "WHERE przedmiot.nazwa = ?";

        switch(sortOrder) {
            case "STUDENT":
                query += "ORDER BY uczen.uczen_id ASC;";
                break;
            case "CLASS":
                query += "ORDER BY klasa.klasa_id ASC;";
                break;
            case "SUBJECT":
                query += "ORDER BY przedmiot.przedmiot_id ASC;";
                break;
            default:
                query += "ORDER BY uczen.uczen_id ASC;";
                break;
        }


        ArrayList<String> resultList = new ArrayList<>();
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, subjectName);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String klasa_nazwa = rs.getString(1);
                int uczen_id = rs.getInt(2);
                String imie = rs.getString(3);
                String nazwisko = rs.getString(4);
                String przedmiot_nazwa = rs.getString(5);
                int klasa_id = rs.getInt(6);
                int przedmiot_id = rs.getInt(7);

                resultList.add(klasa_nazwa + ", " + klasa_id + ", " + uczen_id + ", " + imie
                        + ", " + nazwisko + ", " + przedmiot_nazwa + ", " + przedmiot_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();
        for (String s : resultList) {
            sb.append(s);
            sb.append("|");
        }

        return sb.toString();
    }

    public static String checkCredentials(String login, String password) {
        String selectUczen = "SELECT * FROM uczen WHERE email = ? AND haslo = ?";
        String selectNauczyciel = "SELECT * FROM nauczyciel WHERE email = ? AND haslo = ?";
        String selectAdmin = "SELECT * FROM admin WHERE email = ? AND haslo = ?";

        try {
            PreparedStatement stmtUczen = conn.prepareStatement(selectUczen);
            stmtUczen.setString(1, login);
            stmtUczen.setString(2, password);
            ResultSet rsUczen = stmtUczen.executeQuery();

            PreparedStatement stmtNauczyciel = conn.prepareStatement(selectNauczyciel);
            stmtNauczyciel.setString(1, login);
            stmtNauczyciel.setString(2, password);
            ResultSet rsNauczyciel = stmtNauczyciel.executeQuery();

            PreparedStatement stmtAdmin = conn.prepareStatement(selectAdmin);
            stmtAdmin.setString(1, login);
            stmtAdmin.setString(2, password);
            ResultSet rsAdmin = stmtAdmin.executeQuery();

            if (rsUczen.next()) {
                return "LOGIN_SUCCESS|STUDENT|" + rsUczen.getInt("uczen_id");
            } else if(rsNauczyciel.next()) {
                return "LOGIN_SUCCESS|TEACHER|" + rsNauczyciel.getInt("nauczyciel_id");
            } else if(rsAdmin.next()) {
                return "LOGIN_SUCCESS|ADMIN|" + rsAdmin.getInt("admin_id");
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

    public static String getUserDataByName(String tableName, String fieldName, String searchedName) {
        String selectQuery = "SELECT * FROM " + tableName + " WHERE "+ fieldName + " = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = conn.prepareStatement(selectQuery);
            statement.setString(1, searchedName);
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

    public static boolean deleteRecordById(String tableName, int id, String fieldName) {
        PreparedStatement statement = null;

        try {
            String deleteQuery = "DELETE FROM " + tableName + " WHERE "+ fieldName + " = ?";
            statement = conn.prepareStatement(deleteQuery);
            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A row was deleted successfully!");
                return true;
            } else {
                System.out.println("No row was deleted.");
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while deleting the row.");
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static String getTableData(String tableName, String columns, String conditions) {
        StringBuilder sb = new StringBuilder();
        try {
            String query = "SELECT " + columns + " FROM " + tableName + " WHERE " + conditions;
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String[] columnList = columns.split(",");
                for (String column : columnList) {
                    sb.append(resultSet.getString(column.trim()));
                    sb.append(",");
                }
                sb.setLength(sb.length() - 1);
                sb.append("|");
            }
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 1);
            }
        } catch (SQLException e) {
            System.out.println("Cannot fetch data from " + tableName);
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static String[] getStudentByName(String name) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM uczen WHERE imie = ?");
        statement.setString(1, name);
        String students = fetchStudents(statement);
        if (students.isEmpty()) {
            return null;
        }
        return students.split("\\|");
    }

    public static String[] getStudentBySurname(String surname) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM uczen WHERE nazwisko = ?");
        statement.setString(1, surname);
        String students = fetchStudents(statement);
        if (students.isEmpty()) {
            return null;
        }
        return students.split("\\|");
    }

    public static String[] getStudentByPesel(String pesel) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM uczen WHERE pesel = ?");
        statement.setString(1, pesel);
        String students = fetchStudents(statement);
        if (students.isEmpty()) {
            return null;
        }
        return students.split("\\|");
    }

    public static String[] getStudentByEmail(String email) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM uczen WHERE email = ?");
        statement.setString(1, email);
        String students = fetchStudents(statement);
        if (students.isEmpty()) {
            return null;
        }
        return students.split("\\|");
    }

    public static String[] getStudentByPhoneNumber(String phoneNumber) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT * FROM uczen WHERE telefon = ?");
        statement.setString(1, phoneNumber);
        String students = fetchStudents(statement);
        if (students.isEmpty()) {
            return null;
        }
        return students.split("\\|");
    }

    public static String[] getStudentsFromClass(String className) throws SQLException {
        PreparedStatement statement = conn.prepareStatement("SELECT uczen.* FROM uczen JOIN klasa ON uczen.klasa_id = klasa.klasa_id WHERE klasa.nazwa = ?");
        statement.setString(1, className);
        String students = fetchStudents(statement);
        if (students.isEmpty()) {
            return null;
        }
        return students.split("\\|");
    }

    private static String fetchStudents(PreparedStatement statement) throws SQLException {
        ResultSet rs = statement.executeQuery();
        StringBuilder studentsBuilder = new StringBuilder();
        while (rs.next()) {
            studentsBuilder.append(rs.getString("uczen_id")).append(",");
            studentsBuilder.append(rs.getString("klasa_id")).append(",");
            studentsBuilder.append(rs.getString("imie")).append(",");
            studentsBuilder.append(rs.getString("nazwisko")).append(",");
            studentsBuilder.append(rs.getString("pesel")).append(",");
            studentsBuilder.append(rs.getString("email")).append(",");
            studentsBuilder.append(rs.getString("telefon")).append("|");
        }
        return studentsBuilder.toString();
    }

    public static boolean addGrade(int uczenId, int przedmiotId, int ocena, double waga) {
        try {
            PreparedStatement ocenaStmt = conn.prepareStatement("INSERT INTO ocena (ocena, waga) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            ocenaStmt.setInt(1, ocena);
            ocenaStmt.setDouble(2, waga);
            ocenaStmt.executeUpdate();

            ResultSet rs = ocenaStmt.getGeneratedKeys();
            int ocenaId = -1;
            if(rs.next()) {
                ocenaId = rs.getInt(1);
            }

            PreparedStatement zajeciaStmt = conn.prepareStatement("SELECT zajecia_id FROM zajecia WHERE przedmiot_id = ?");
            zajeciaStmt.setInt(1, przedmiotId);
            ResultSet rsZajecia = zajeciaStmt.executeQuery();

            if (!rsZajecia.next()) {
                return false;
            }

            int zajeciaId = rsZajecia.getInt(1);

            PreparedStatement zajeciaUczenCheckStmt = conn.prepareStatement("SELECT zajecia_uczen_id FROM zajecia_uczen WHERE uczen_id = ? AND zajecia_id = ?");
            zajeciaUczenCheckStmt.setInt(1, uczenId);
            zajeciaUczenCheckStmt.setInt(2, zajeciaId);
            ResultSet rsZajeciaUczenCheck = zajeciaUczenCheckStmt.executeQuery();

            int zajeciaUczenId;

            if (rsZajeciaUczenCheck.next()) {
                zajeciaUczenId = rsZajeciaUczenCheck.getInt(1);
            } else {
                PreparedStatement zajeciaUczenCreateStmt = conn.prepareStatement("INSERT INTO zajecia_uczen (uczen_id, zajecia_id, rok) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                zajeciaUczenCreateStmt.setInt(1, uczenId);
                zajeciaUczenCreateStmt.setInt(2, zajeciaId);
                zajeciaUczenCreateStmt.setString(3, java.time.LocalDate.now().toString().substring(0, 4)); // ustaw rok na aktualny rok
                zajeciaUczenCreateStmt.executeUpdate();

                ResultSet rsZajeciaUczenCreate = zajeciaUczenCreateStmt.getGeneratedKeys();
                zajeciaUczenId = -1;
                if(rsZajeciaUczenCreate.next()) {
                    zajeciaUczenId = rsZajeciaUczenCreate.getInt(1);
                }
            }

            PreparedStatement ocenyUczniowNaZajeciachStmt = conn.prepareStatement("INSERT INTO oceny_uczniow_na_zajeciach (zajecia_uczen_id, ocena_id, data) VALUES (?, ?, ?)");
            ocenyUczniowNaZajeciachStmt.setInt(1, zajeciaUczenId);
            ocenyUczniowNaZajeciachStmt.setInt(2, ocenaId);
            ocenyUczniowNaZajeciachStmt.setString(3, java.time.LocalDate.now().toString());
            ocenyUczniowNaZajeciachStmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




    public static String getStudentSubjectsAndGrades(String userId) {
        StringBuilder sb = new StringBuilder();

        try {
            String query = "SELECT p.nazwa, ocena.ocena, zu.rok " +
                    "FROM przedmiot p " +
                    "JOIN zajecia ON zajecia.przedmiot_id = p.przedmiot_id " +
                    "JOIN zajecia_uczen zu ON zu.zajecia_id = zajecia.zajecia_id " +
                    "JOIN uczen u ON u.uczen_id = zu.uczen_id " +
                    "LEFT JOIN oceny_uczniow_na_zajeciach ounz ON ounz.zajecia_uczen_id = zu.zajecia_uczen_id " +
                    "LEFT JOIN ocena ON ocena.ocena_id = ounz.ocena_id " +
                    "WHERE u.uczen_id = ?";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, userId);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String subject = resultSet.getString("nazwa");
                String grade = resultSet.getString("ocena");
                String year = resultSet.getString("rok");

                sb.append(subject.toUpperCase());

                if (year != null) {
                    sb.append(" (").append(year).append(")");
                }

                if (grade != null) {
                    sb.append(grade);
                }

                sb.append("|");
            }
            sb.append("|");

            if (sb.length() > 0) {
                sb.setLength(sb.length() - 1);
            }

        } catch (SQLException e) {
            System.out.println("Cannot fetch data from: " + e.getMessage());
        }

        return sb.toString();
    }

    public static String getStudentsRanking(String userId) {
        StringBuilder sb = new StringBuilder();

        try {
            String query = "SELECT u.uczen_id, u.imie, u.nazwisko, AVG(ocena.ocena) AS srednia_ocen\n" +
                    "FROM uczen u\n" +
                    "JOIN zajecia_uczen zu ON zu.uczen_id = u.uczen_id\n" +
                    "LEFT JOIN oceny_uczniow_na_zajeciach ounz ON ounz.zajecia_uczen_id = zu.zajecia_uczen_id\n" +
                    "LEFT JOIN ocena ON ocena.ocena_id = ounz.ocena_id\n" +
                    "WHERE zu.rok = ?\n" +
                    "GROUP BY u.uczen_id\n" +
                    "ORDER BY srednia_ocen DESC";

            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "2023");
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("uczen_id");
                String firstName = resultSet.getString("imie");
                String lastName = resultSet.getString("nazwisko");
                double averageGrade = resultSet.getDouble("srednia_ocen");

                String record = id + " " + firstName + " " + lastName + " " + averageGrade;
                sb.append(record).append("|");
            }

            if (sb.length() > 0) {
                sb.setLength(sb.length() - 1);
            }
        } catch (SQLException e) {
            System.out.println("Cannot fetch data: " + e.getMessage());
        }

        return sb.toString();
    }

    public static void add_test_data() {
        add_admin("Adam", "Adminowski", "12345676543", "admin", "102222222", "admin");

        add_klasa("3A");
        add_klasa("3B");
        add_klasa("3C");

        add_uczen( "Jan", "Kowalski", "12345678901", "jan.kowalski@school.com", "123456789", "password1");
        add_uczen( "Anna", "Nowak", "12345678902", "anna.nowak@school.com", "234567890", "password2");
        add_uczen( "Piotr", "Kowalski", "12345678903", "piotr.kowalski@school.com", "345678901", "password3");
        add_uczen( "Maria", "Kowalczyk", "12345678904", "maria.kowalczyk@school.com", "456789012", "password4");

        add_nauczyciel("Adam", "Nowak", "12345678905", "adam.nowak@school.com", "567890123", "password5");
        add_nauczyciel("Katarzyna", "Kowalska", "12345678906", "katarzyna.kowalska@school.com", "678901234", "password6");

        add_przedmiot("matematyka");
        add_przedmiot("j. polski");
        add_przedmiot("historia");

        add_zajecia(1, 1);
        add_zajecia(1, 2);
        add_zajecia(2, 3);

        add_zajecia_uczen(1, 1, "2023");
        add_zajecia_uczen(2, 1, "2023");
        add_zajecia_uczen(2, 2, "2023");
        add_zajecia_uczen(1, 2, "2022");

        add_ocena(4, 1);
        add_ocena(5, 2);
        add_ocena(3, 1);

        add_oceny_uczniow_na_zajeciach(1, 1, "2023-05-01");
        add_oceny_uczniow_na_zajeciach(1, 2, "2023-05-01");
        add_oceny_uczniow_na_zajeciach(3, 3, "2023-05-01");

        add_konwersacje(false, true, 1, 2);
        add_konwersacje(true, false, 2, 1);

        add_klasa_przedmiot(1, 3);
        add_klasa_przedmiot(2, 2);
        add_klasa_przedmiot(3, 1);
    }

    public static Connection getConn() {
        return conn;
    }
}
