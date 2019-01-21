/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

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
    /**
     * Indikerer om en klient skal redirectes til en given url.
     */
    protected boolean clientRedirect = false;
    /**
     * Indikerer om en klient skal redirectes til en given URL.
     * Bruges til at implementere POST-REDIRECT-GET pattern.
     * Controller eller anden klasse, som har kørt dette command og fået en URL 
     * tilbage, kan efterfølgende evaluere om klient skal redirectes eller om 
     * requestdispatcher kan forwardes til URL.
     * @return 
     */
    public boolean redirectClient(){ return clientRedirect;}
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
        commands.put(Commands.ADMIN_USERS, new ShowAdminUsersCommand());
        commands.put(Commands.ADMIN_PASSWORD, new ShowAdminNewPasswordCommand());
        commands.put(Commands.ADMIN_RANK, new ShowAdminUpdateRankCommand());
        commands.put(Commands.LOGOUT, new LogoutCommand());
        commands.put(Commands.FORGET_PASSWORD, new ForgetPasswordCommand());
        commands.put(Commands.ADMIN_DELETE_USER, new ShowAdminDeleteUserCommand());
        commands.put(Commands.USER_HOME, new ShowUserHomeCommand());
        commands.put(Commands.USER_PASSWORD, new ShowUserPasswordCommand());
        commands.put(Commands.USER_UPDATEPASSWORD, new ShowUserPasswordUpdate());
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
        String command = request.getParameter("command");
        
        // Initialize the hash map if needed.
        if (commands == null)
            initializeCommands();
        
        // If it exists, get corresponding Command object from hash map, 
        // otherwise return UnknownCommand Command.        
        return commands.getOrDefault(command, new UnknownCommand());        
    }  
    
    /**
     * Metode som afgør om requestet er sendt vha. POST eller GET og håndterer det i
     * enten doPost()- eller doGet()-metoden.     
     * Kan overskrives i nedarvninger, så der ikke skelnes mellem POST og GET, hvis der
     * i begge tilfælde skal udføres det samme kode.
     * @param request
     * @param response
     * @return String
     * @throws FogException 
     */
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        if ("POST".equals(request.getMethod()))
            return doPost(request, response);
        else
            return doGet(request, response);    
    }  
        
    /**
     * Håndterer et POST request.
     * @param request
     * @param response
     * @return
     * @throws FogException 
     */
    abstract protected String doPost(HttpServletRequest request, HttpServletResponse response) throws FogException;
    /**
     * Håndterer et GET request.
     * @param request
     * @param response
     * @return
     * @throws FogException 
     */
    abstract protected String doGet(HttpServletRequest request, HttpServletResponse response) throws FogException;
}
