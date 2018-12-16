/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application specific exception to provide a friendly message to the user
 * as well as a detailed message for the developers.
 * 
 * @author Claus
 */
public class FogException extends Exception
{   
    /**
     * Logger for FogException klassen. 
     * Erklæret statisk, så alle instanser deler samme logger.
     */
    private static final Logger LOGGER = FogLogger.getLogger(jc.fog.exceptions.FogException.class.getName(), false);
    
    private Exception causingException;
    
    /**
     * The exception that caused this FogException to be thrown.
     * Look here for the stack trace.
     * @return 
     */
    public Exception getCausingException(){return causingException;}
    
    private String detailedMessage;
    /**
     * A detailed message for the developers.
     */
    public String getDetailedMessage(){ return detailedMessage; }
    /**
     * The user-friendly message.     
     */
    public String getFriendlyMessage() { return super.getMessage(); }
    
    
    
    /**
     * Creates a custom exception with a friendly message for users
     * and a detailed message for developers.
     * Exception is also logged as SEVERE.
     * @param friendlyMessage A message that the user should be able to understand.
     * @param detailedMessage A detailed message for developers.
     * @param causingException The exception that caused this exception to be thrown. If null is submitted, an empty Exception instance will be generated in the FogException constructor.
     */
    public FogException(String friendlyMessage, String detailedMessage, Exception causingException)
    {
        // Call super with friendlyMessage, so it will display with getMessage().
        super(friendlyMessage);
        // store detailed message to display in debugging mode or verbose...
        this.detailedMessage = detailedMessage;
        // Hvis argumentet causingException er null, erklæres nyt Exception objekt.
        this.causingException = (causingException != null) ? causingException : new Exception();
        
        // Log undtagelsen.
        LOGGER.log(Level.SEVERE, friendlyMessage, causingException);
    }
}
