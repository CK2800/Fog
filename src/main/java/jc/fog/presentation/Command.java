/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.exceptions.FogException;

/**
 * Command class to be extended into specific commands accepted by the front controller.
 * 
 * Functionality common to all implementing classes is assembled here.
 * Use static String members to specify the supported commands in the views.
 * @see jc.fog.presentation.constants.Commands
 * 
 * @author Claus
 */
public abstract class Command
{
    // placeholder.
    public static String command;
    
    /**
     * HashMap of available commands.
     */
    private static HashMap<String, Command> commands;
    
    /**
     * Sets up the HashMap of Commands.
     */
    private static void initializeCommands()
    {
        // Instantiate the hash map.
        commands = new HashMap<>();
        
        // put available commands onto hash map.
        //commands.put(Commands.LOGIN, new LoginCommand());
        //commands.put(Commands.LOGOUT, new LogoutCommand());
        commands.put(Commands.SHOW_REQUESTS, new ShowRequestsCommand());
        commands.put(Commands.SHOW_MATERIALS, new ShowMaterialsCommand());
        commands.put(Commands.SHOW_SINGLE_MATERIAL, new ShowSingleMaterialeCommand());
        commands.put(Commands.SHOW_BILL, new ShowBillCommand());
        commands.put(Commands.SINGLEDRAW, new ShowDrawingCommand());
        commands.put(Commands.SHOW_FORM_REQUEST, new ShowCarPortCommand());
        commands.put(Commands.UPDATE_REQUEST, new ShowUpdateRequestCommand());
        commands.put(Commands.ADD_REQUEST, new ShowAddRequestCommand());
        commands.put(Commands.LOGIN, new ShowLoginCommand());
    }
    
    /**
     * Gets the parameter named 'command' from the request and returns
     * the corresponding Command child object.
     * 
     * @param request HttpServletRequest
     * @return Command object.
     */
    public static Command from(HttpServletRequest request)
    {
        // Get 'command' parameter from request.
        command = request.getParameter("command");
        
        // Initialize the hash map if needed.
        if (commands == null)
            initializeCommands();
        
        // If it exists, get corresponding Command object from hash map, 
        // otherwise return UnknownCommand Command.        
        return commands.getOrDefault(command, new UnknownCommand());        
    }  
    
    /**
     * Abstract method execute must be implemented in children.
     * @param request
     * @param response
     * @return String
     * @throws FogException 
     */
    abstract public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException;
            
    
}
