package com.example.vinylcollector;

import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Includes test cases for VinylDataValidator class
 * @author Lorenz
 */
public class VinylDataValidatorTest {

    /**
     * Test case for checkGenre method
     */
    @Test
    public void testCheckGenre() throws InvalidInputException {
        // Test valid input
        String genre1 = "Rock";
        assertTrue(VinylDataValidator.checkGenre(genre1));

        // Test invalid input: genre contains numbers
        String genre2 = "Pop2";
        assertThrows(InvalidInputException.class, () -> VinylDataValidator.checkGenre(genre2));
    }

    @Test
    public void testDatabaseConnection() {
        String jdbcUrl = "jdbc:sqlite:C:\\Users\\lobin\\IdeaProjects\\VinylCollector\\src\\main\\resources\\VinylCollectorDB.db";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl);

            // Überprüfen, ob die Verbindung nicht null ist
            assertNotNull(connection, "Die Verbindung zur Datenbank sollte nicht null sein.");

            connection.close();
        } catch (SQLException e) {
            fail("Fehler beim Herstellen der Verbindung zur Datenbank: " + e.getMessage());
        }
    }

    @Test
    public void testVinylTableExistence() {
        String jdbcUrl = "jdbc:sqlite:src\\main\\resources\\com\\example\\vinylcollector\\VinylCollectorDB.db";

        try {
            Connection connection = DriverManager.getConnection(jdbcUrl);
            DatabaseMetaData metaData = connection.getMetaData();

            System.out.println("Tabellen in der Datenbank:");
            try (ResultSet resultSet = metaData.getTables(null, null, "%", null)) {
                while (resultSet.next()) {
                    String tableName = resultSet.getString("TABLE_NAME");
                    System.out.println(tableName);
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private boolean tableExists(DatabaseMetaData metaData, String tableName) throws SQLException {
        try (var resultSet = metaData.getTables(null, null, tableName, null)) {
            return resultSet.next();
        }
    }

    @Test
    public void testAddToDatabase() {

        String[] testValues = {"Title134212", "Artist1", "2022", "Rock", "https://link1", "/path/to/image1.jpg"};

        VinylDataExchange.writeIntoDataBase(testValues);

    }

    @Test
    public void testGetFullDataTable() {
        String[][] array = VinylDataExchange.getFullDataTable();

        for (String[] row : array) {
            System.out.println(Arrays.toString(row));
        }
    }

    @Test
    public void testGetRowByID() {
        System.out.println(Arrays.toString(VinylDataExchange.getRowById("1")));
    }

    @Test
    public void testUpdateInDatabase() {

        // Aktualisierte Werte
        String[] updatedValues = {"UpdatedTitle", "UpdatedArtist", "2023", "UpdatedGenre", "UpdatedLink", "/path/to/updated/image.jpg"};

        // Test durchführen
        VinylDataExchange.writeIntoDataBase(updatedValues, "19");

    }

}
