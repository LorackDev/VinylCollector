package com.example.vinylcollector;

/**
 * Exception used in VinylDataValidator to show when user input is invalid.
 * @author Lorenz
 */
public class InvalidInputException extends Exception{
    public InvalidInputException (String errorMessage){
        super(errorMessage);
    }
}
