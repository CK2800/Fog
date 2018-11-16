/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.exceptions;

/**
 * Application specific exception to provide a friendly message to the user
 * as well as a detailed message for the developers.
 * 
 * @author Claus
 */
public class FogException extends Exception
{    
    /**
     * A detailed message for the developers.
     */
    private String detailedMessage;
    public String getDetailedMessage(){ return detailedMessage; }
    
    /**
     * Creates a custom exception with a friendly message for users
     * and a detailed message for developers.
     * @param friendlyMessage
     * @param detailedMessage 
     */
    public FogException(String friendlyMessage, String detailedMessage)
    {
        // Call super with friendlyMessage, so it will display with getMessage().
        super(friendlyMessage);
        // store detailed message to display in debugging mode or verbose...
        this.detailedMessage = detailedMessage;
        
    }
}
