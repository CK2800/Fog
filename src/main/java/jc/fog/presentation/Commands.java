/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation;

/**
 * Non-extendable class with Command String constants
 * for use with the FrontController in presentation layer.
 * aka. Constant interface anti-pattern.
 * 
 * @author Claus
 */
public final class Commands
{
    // Private constructor to avoid instantiation.
    private Commands(){}
    
    /** Logger bruger ind. */
    public static final String LOGIN = "login";
    /** Logger brugeren ud. */
    public static final String LOGOUT = "logout";
    /** Command til at tjek om man er bruger eller ej?*/
    public static final String LOGIN_CHECK = "loginCheck";
    /** Command til at opret bruger */
    public static final String REGISTER = "register";
    /** Command til send/smide bruger oplysning op */
    public static final String ADD_REGISTER = "addRegister";
    
    
    /** Command som viser alle forespørgsler. */
    public static final String SHOW_REQUESTS = "showrequests";
    /** Command til oprettelse af carport request. */
    public static final String SHOW_FORM_REQUEST = "showRequestForm";
    /** Command til updater af carport request så den updater i databasen. */
    public static final String UPDATE_REQUEST = "updateRequest";
    /** Command til oprettelse af carport request så det ind i databasen. */
    public static final String ADD_REQUEST = "addRequest";
    
    
    /** Command som viser et enkelt materiale */
    public static final String SHOW_SINGLE_MATERIAL = "showsinglematerial";
    /** Command som viser en liste af materialer */
    public static final String SHOW_MATERIALS = "showmaterials";
   
    
    /** Command som viser styklisten. */
    public static final String SHOW_BILL = "showBill";
    
    
    /** Command som viser tegning af carport. */
    public static final String SINGLEDRAW = "singledraw";
    
    
    public static final String ADMIN_USERS ="adminUsers";
    
}
