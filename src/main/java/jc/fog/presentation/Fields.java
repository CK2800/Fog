/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation;

/**
 * Non-extendable class with String constants for attribute names in web application.
 * 
 * @author Claus
 */
public final class Fields
{
    // private constructor to avoid instantiation.
    private Fields(){}
    
    /**
     * Name of variable in the request object that contains an error text. 
     */
    public static final String ERROR_TEXT = "errorText";
    /**
     * Name of variable in form or querystring that contains path to return to if error occurs.
     */
    public static final String ERROR_PATH = "errorPath";
}
