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
    public static final String ALL_CARPORTS = "/WEB-INF/allCarports.jsp";
    public static final String SINGLE_CARPORT = "/WEB-INF/singleCarPort.jsp";
    public static final String ALL_MATERIALS = "/WEB-INF/allMaterials.jsp";
    public static final String SINGLE_MATERIAL = "/WEB-INF/singleMaterial.jsp";
    public static final String BILL = "/WEB-INF/bill.jsp";
}
