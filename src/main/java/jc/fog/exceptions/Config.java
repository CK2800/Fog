/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.exceptions;

/**
 *
 * @author Claus
 */
public class Config
{
    public static final boolean PRODUCTION = false;
    public static final String LOG_PATH = "/var/log/tomcat8/jc.fog.%u.%g.log"; // ej testet.
    public static final String LOG_PATH_DEVELOP = "%t/jc.fog.%u.%g.log"; // f.eks. [bruger]/AppData/Local/Temp/jc.fog.0.0.log
    
}
