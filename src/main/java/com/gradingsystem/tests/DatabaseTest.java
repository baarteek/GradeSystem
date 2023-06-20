package com.gradingsystem.tests;

import java.sql.*;
import java.util.List;
import java.util.Map;

import com.gradingsystem.server.Database;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DatabaseTest {
    private static Connection conn;

    private void clearDatabase() {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM klasa");
            stmt.executeUpdate("DELETE FROM uczen");
            stmt.executeUpdate("DELETE FROM admin");
            stmt.executeUpdate("DELETE FROM nauczyciel");
            stmt.executeUpdate("DELETE FROM przedmiot");
            stmt.executeUpdate("DELETE FROM zajecia");
            stmt.executeUpdate("DELETE FROM zajecia_uczen");
            stmt.executeUpdate("DELETE FROM ocena");
            stmt.executeUpdate("DELETE FROM oceny_uczniow_na_zajeciach");
            stmt.executeUpdate("DELETE FROM wiadomosc");
            stmt.executeUpdate("DELETE FROM klasa_przedmiot");

            stmt.close();
        } catch (SQLException e) {
            System.out.println("Clear database error");
            e.printStackTrace();
        }
    }

    @Before
    public void setup() {
        Database.connect("jdbc:sqlite:grading_system_test.db");
        conn = Database.getConn();
        clearDatabase();
    }

    @After
    public void cleanup() {
        clearDatabase();
        Database.disconnect();
    }
    @Test
    public void testAddKlasa() {
        Database.add_klasa("Klasa A");

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM klasa WHERE nazwa = 'Klasa A'");
            rs.next();
            Assert.assertEquals(1, rs.getInt(1));
        } catch (SQLException e) {
            Assert.fail("SQLException occurred");
        }
    }

    @Test
    public void testAddUczen() {
        Database.add_uczen("Jan", "Kowalski", "99945678901", "jan.kowalski@example.com", "123456789", "password");

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM uczen WHERE imie = 'Jan' AND nazwisko = 'Kowalski' AND pesel = '99945678901'");
            rs.next();
            Assert.assertEquals(1, rs.getInt(1));
        } catch (SQLException e) {
            Assert.fail("SQLException occurred");
        }
    }

    @Test
    public void testAddAdmin() {
        Database.add_admin("Jan", "Kowalski", "1176543210", "test@example.com", "987654321", "password");

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM admin WHERE imie = 'Jan' AND nazwisko = 'Kowalski' AND pesel = '1176543210'");
            rs.next();
            Assert.assertEquals(1, rs.getInt(1));
        } catch (SQLException e) {
            Assert.fail("SQLException occurred");
        }
    }

    @Test
    public void testAddNauczyciel() {
        Database.add_nauczyciel("Jan", "Kowalski", "3334567890", "john.doe@example.com", "987654321", "password");

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM nauczyciel WHERE imie = 'Jan' AND nazwisko = 'Kowalski' AND pesel = '3334567890'");
            rs.next();
            Assert.assertEquals(1, rs.getInt(1));
        } catch (SQLException e) {
            Assert.fail("SQLException occurred");
        }
    }

    @Test
    public void testAddPrzedmiot() {
        Database.add_przedmiot("TestowyPrzedmiot");

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM przedmiot WHERE nazwa = 'TestowyPrzedmiot'");
            rs.next();
            Assert.assertEquals(1, rs.getInt(1));
        } catch (SQLException e) {
            Assert.fail("SQLException occurred");
        }
    }

    @Test
    public void testAddZajecia() {
        int nauczycielId = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nauczyciel_id FROM nauczyciel WHERE imie = 'Jan' AND nazwisko = 'Kowalski'");
            if (rs.next()) {
                nauczycielId = rs.getInt("id");
            }
        } catch (SQLException e) {
            Assert.fail("SQLException occurred");
        }

        int przedmiotId = 0;
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT przedmiot_id FROM przedmiot WHERE nazwa = 'TestowyPrzedmiot'");
            if (rs.next()) {
                przedmiotId = rs.getInt("id");
            }
        } catch (SQLException e) {
            Assert.fail("SQLException occurred");
        }

        Database.add_zajecia(nauczycielId, przedmiotId);

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM zajecia WHERE nauczyciel_id = " + nauczycielId + " AND przedmiot_id = " + przedmiotId);
            rs.next();
            Assert.assertEquals(1, rs.getInt(1));
        } catch (SQLException e) {
            Assert.fail("SQLException occurred");
        }

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("DELETE FROM zajecia WHERE nauczyciel_id = " + nauczycielId + " AND przedmiot_id = " + przedmiotId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAddZajeciaUczen() {
        int uczenId = 1;
        int zajeciaId = 1;
        String rok = "2023";

        Database.add_zajecia_uczen(uczenId, zajeciaId, rok);

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM zajecia_uczen WHERE uczen_id = " + uczenId + " AND zajecia_id = " + zajeciaId + " AND rok = '" + rok + "'");
            rs.next();
            Assert.assertEquals(1, rs.getInt(1));
        } catch (SQLException e) {
            Assert.fail("SQLException occurred");
        }
    }

    @Test
    public void testAddOcena() {
        int ocena = 4;
        int waga = 2;

        Database.add_ocena(ocena, waga);

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM ocena WHERE ocena = " + ocena + " AND waga = " + waga);
            rs.next();
            Assert.assertEquals(1, rs.getInt(1));
        } catch (SQLException e) {
            Assert.fail("SQLException occurred");
        }
    }

    @Test
    public void testAddKlasaPrzedmiot() {
        int klasaId = 1;
        int przedmiotId = 1;

        Database.add_klasa_przedmiot(klasaId, przedmiotId);

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM klasa_przedmiot WHERE klasa_id = " + klasaId + " AND przedmiot_id = " + przedmiotId);
            rs.next();
            Assert.assertEquals(1, rs.getInt(1));
        } catch (SQLException e) {
            Assert.fail("SQLException occurred");
        }
    }

    @Test
    public void testAddOcenyUczniowNaZajeciach() {
        int zajeciaUczenId = 1;
        int ocenaId = 1;
        String data = "2023-05-02";

        Database.add_oceny_uczniow_na_zajeciach(zajeciaUczenId, ocenaId, data);

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM oceny_uczniow_na_zajeciach WHERE zajecia_uczen_id = " + zajeciaUczenId + " AND ocena_id = " + ocenaId + " AND data = '" + data + "'");
            rs.next();
            Assert.assertEquals(1, rs.getInt(1));
        } catch (SQLException e) {
            Assert.fail("SQLException occurred");
        }
    }

    @Test
    public void testGetAllFieldsFromTable() {
        String tableName = "klasa";

        List<String> fields = Database.getAllFieldsFromTable(tableName);

        Assert.assertFalse("Table " + tableName + " doesn't exist.", fields.isEmpty());
        for (String field : fields) {
            Assert.assertNotNull(field);
        }
    }

    @Test
    public void testGetStudentsWithoutClass() {
        Database.add_uczen("Jan", "Kowalski", "12345678901", "jan.kowalski@example.com", "123456789", "password");
        Database.add_uczen("Anna", "Nowak", "98765432109", "anna.nowak@example.com", "987654321", "password");

        List<Map<String, Object>> students = Database.getStudentsWithoutClass();

        Assert.assertEquals("Incorrect number of students without class.", 2, students.size());
    }

    @Test
    public void testCheckCredentials() {
        String studentLogin = "student@example.com";
        String studentPassword = "student123";

        String teacherLogin = "teacher@example.com";
        String teacherPassword = "teacher123";

        String adminLogin = "admin@example.com";
        String adminPassword = "admin123";

        Database.add_admin("Jan", "Kowalski", "1176543210", adminLogin, "987654321", adminPassword);
        Database.add_uczen("Jan", "Kowalski", "1176543210", studentLogin, "987654321", studentPassword);
        Database.add_nauczyciel("Jan", "Kowalski", "1176543210", teacherLogin, "987654321", teacherPassword);

        String studentResult = Database.checkCredentials(studentLogin, studentPassword);
        Assert.assertTrue("LOGIN_SUCCESS|STUDENT", studentResult.startsWith("LOGIN_SUCCESS|STUDENT"));

        String teacherResult = Database.checkCredentials(teacherLogin, teacherPassword);
        Assert.assertTrue("LOGIN_SUCCESS|TEACHER", teacherResult.startsWith("LOGIN_SUCCESS|TEACHER"));

        String adminResult = Database.checkCredentials(adminLogin, adminPassword);
        Assert.assertTrue("LOGIN_SUCCESS|ADMIN", adminResult.startsWith("LOGIN_SUCCESS|ADMIN"));

        String invalidResult = Database.checkCredentials(adminLogin, "invalid123");
        Assert.assertFalse("LOGIN_SUCCESS|ADMIN", invalidResult.startsWith("LOGIN_SUCCESS|ADMIN"));
    }

}

