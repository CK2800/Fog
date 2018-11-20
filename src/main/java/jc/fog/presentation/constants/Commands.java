/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.constants;

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
    
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String SHOWREQUESTS = "showrequests";
    public static final String SHOWSINGLEREQUEST = "showsinglerequest";
    public static final String SHOWSINGLEMATERIALE = "showsinglemateriale";
    public static final String SHOWMATERIALE = "showmateriale";
}
