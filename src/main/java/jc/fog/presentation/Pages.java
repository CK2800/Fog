/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation;

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
    
    
    public static final String ALL_CARPORTS = "/WEB-INF/allRequest.jsp";
    public static final String SINGLE_CARPORTVIEW = "/WEB-INF/request.jsp";
    
    public static final String ALL_MATERIALS = "/WEB-INF/allMaterials.jsp";
    public static final String SINGLE_MATERIAL = "/WEB-INF/singleMaterial.jsp";
    
    public static final String BILL = "/WEB-INF/bill.jsp";
    
    public static final String SINGLE_DRAW = "/WEB-INF/singleDraw.jsp";
    
    public static final String LOGIN = "/WEB-INF/login.jsp";
    public static final String REGISTER = "/WEB-INF/register.jsp";
    
    
    public static final String USER_PASSWORD = "/WEB-INF/userPassword.jsp";
    public static final String USER_HOME = "/WEB-INF/user.jsp";
    
    
    public static final String ADMIN_USER = "/WEB-INF/adminUsers.jsp";
    
    
    
}
