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
    /** Command som viser alle forespørgsler. */
    public static final String SHOW_REQUESTS = "showrequests";
    /** Command som viser en enkelt forespørgsel. */
    public static final String SHOW_SINGLE_REQUEST = "showsinglerequest";
    /** Command som viser et enkelt materiale */
    public static final String SHOW_SINGLE_MATERIAL = "showsinglemateriale";
    /** Command som viser en liste af materialer */
    public static final String SHOW_MATERIALS = "showmaterials";
    /** Command som viser styklisten. */
    public static final String SHOW_BILL = "showBill";
    /** Command som viser tegning af carport. */
    public static final String SINGLEDRAW = "singledraw";
    /** Command til oprettelse af carport request. */
    public static final String SHOW_CARPORT_FORM = "showCarportForm";
    /** Command til oprettelse af carport request så den updater i databasen. */
    public static final String CREATE_CARPORT_REQUEST = "createCarportRequest";
}
