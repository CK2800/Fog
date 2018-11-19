/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.constants;

/**
 * Non-extendable class with String constants for pages in web application.  
 * 
 * @author Claus
 */
public final class Pages
{
    // private constructor to avoid instantiation.
    private Pages(){}
    public static final String INDEX = "index.jsp";
    public static final String ALL_REQUESTS = "/WEB-INF/allForesporgsel.jsp";
    public static final String SINGLE_REQUEST = "/WEB-INF/singleForesporgsel.jsp";
    public static final String ALL_MATERIALE = "/WEB-INF/allMateriale.jsp";
    public static final String SINGLE_MATERIALE = "/WEB-INF/singleMateriale.jsp";
}
