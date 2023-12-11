package com.example.vinylcollector;

/**
 * @author Lorenz
 * Exception used in VinylDataValidator to show when user input is invalid.
 */
public class InvalidInputException extends Exception{
    public InvalidInputException (String errorMessage){
        super(errorMessage);
    }
}
