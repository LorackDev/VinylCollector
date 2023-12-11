package com.example.vinylcollector;

import java.time.Year;

/**
 * @author Lorenz
 * This class provides functions to validate data which has been input in VinylCollectionAppGUI.
 * Every function throws an exception if invalid and returns true if valid.
 */
public class VinylDataValidator {

    public static boolean checkReleaseYear(int year) throws InvalidInputException{

    if(year > Year.now().getValue() || year < 0)
        throw new InvalidInputException("Invalid input in release year.");
    else
        return true;
    }

    public static boolean checkGenre(String genre) throws InvalidInputException{
        if(genre.matches(".*\\d.*"))
            throw new InvalidInputException("Genre isn't allowed to contain numbers.");
        else
            return true;
       }
}
