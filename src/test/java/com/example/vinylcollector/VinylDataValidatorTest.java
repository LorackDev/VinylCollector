package com.example.vinylcollector;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Includes test cases for VinylDataValidator class
 * @author Lorenz
 */
public class VinylDataValidatorTest {

    /**
     * Test case for checkReleaseYear method
     */
    @Test
    public void testCheckReleaseYear() throws InvalidInputException{
        // Test valid input
        int year1 = 2020;
        assertTrue(VinylDataValidator.checkReleaseYear(year1));

        // Test invalid input: year greater than current year
        int year2 = 2024;
        assertThrows(InvalidInputException.class, () -> VinylDataValidator.checkReleaseYear(year2));

        // Test invalid input: year less than zero
        int year3 = -10;
        assertThrows(InvalidInputException.class, () -> VinylDataValidator.checkReleaseYear(year3));
    }

    /**
     * Test case for checkGenre method
     */
    @Test
    public void testCheckGenre() throws InvalidInputException{
        // Test valid input
        String genre1 = "Rock";
        assertTrue(VinylDataValidator.checkGenre(genre1));

        // Test invalid input: genre contains numbers
        String genre2 = "Pop2";
        assertThrows(InvalidInputException.class, () -> VinylDataValidator.checkGenre(genre2));
    }
}
