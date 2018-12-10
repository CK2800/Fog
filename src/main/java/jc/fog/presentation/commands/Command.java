/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import jc.fog.presentation.commands.ShowAddRegisterCommand;
import jc.fog.presentation.commands.ShowAddRequestCommand;
import jc.fog.presentation.commands.ShowBillCommand;
import jc.fog.presentation.commands.ShowCarPortCommand;
import jc.fog.presentation.commands.ShowDrawingCommand;
import jc.fog.presentation.commands.ShowLoginCheckCommand;
import jc.fog.presentation.commands.ShowLoginCommand;
import jc.fog.presentation.commands.ShowMaterialsCommand;
import jc.fog.presentation.commands.ShowRegisterCommand;
import jc.fog.presentation.commands.ShowRequestsCommand;
import jc.fog.presentation.commands.ShowSingleMaterialeCommand;
import jc.fog.presentation.commands.ShowUpdateRequestCommand;
import jc.fog.presentation.commands.UnknownCommand;
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
        
        commands.put(Commands.SHOW_REQUESTS, new ShowRequestsCommand());
        commands.put(Commands.SHOW_MATERIALS, new ShowMaterialsCommand());
        commands.put(Commands.SHOW_SINGLE_MATERIAL, new ShowSingleMaterialeCommand());
        commands.put(Commands.SHOW_BILL, new ShowBillCommand());
        commands.put(Commands.SINGLEDRAW, new ShowDrawingCommand());
        commands.put(Commands.SHOW_FORM_REQUEST, new ShowCarPortCommand());
        commands.put(Commands.UPDATE_REQUEST, new ShowUpdateRequestCommand());
        commands.put(Commands.ADD_REQUEST, new ShowAddRequestCommand());
        commands.put(Commands.LOGIN, new ShowLoginCommand());
        commands.put(Commands.LOGIN_CHECK, new ShowLoginCheckCommand());
        commands.put(Commands.REGISTER, new ShowRegisterCommand());
        commands.put(Commands.ADD_REGISTER, new ShowAddRegisterCommand());
<<<<<<< HEAD:src/main/java/jc/fog/presentation/Command.java
        commands.put(Commands.ADMIN_USERS, new ShowAdminUsersCommand());
        commands.put(Commands.ADMIN_PASSWORD, new ShowAdminNewPasswordCommand());
        commands.put(Commands.ADMIN_RANK, new ShowAdminUpdateRankCommand());
        commands.put(Commands.LOGOUT, new Logout());
=======
        commands.put(Commands.LOGOUT, new LogoutCommand());
<<<<<<< HEAD
>>>>>>> FeatureClaus:src/main/java/jc/fog/presentation/commands/Command.java
=======
        commands.put(Commands.FORGOT, new ForgotPassword());
>>>>>>> FeatureClaus
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
