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
    
    public static final String LOGIN = "login";
    public static final String LOGOUT = "logout";
    public static final String SHOWREQUESTS = "showrequests";
    public static final String SHOWSINGLEREQUEST = "showsinglerequest";
    public static final String SHOWSINGLEMATERIAL = "showsinglematerial";
    public static final String SHOWMATERIALS = "showmaterials";
    public static final String BILL = "bill";
    public static final String SINGLEDRAW = "singledraw";
}
