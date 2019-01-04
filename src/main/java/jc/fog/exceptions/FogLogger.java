/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.exceptions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 *
 * @author Claus
 */
public class FogLogger
{   
    public static Logger getLogger(String name, boolean production)
    {
        // Hent eller opret logger med ønsket navn.
        Logger logger = Logger.getLogger(name);
        // Tilføj default handler til loggeren.
        logger.addHandler(new ConsoleHandler());
        // Placeholder for FileHandler.
        FileHandler fileHandler;
        
        try
        {   
            // Tilføj korrekt sti til file handler ved production eller lokal.                                   
            fileHandler = new FileHandler(production ? Config.LOG_PATH : Config.LOG_PATH_DEVELOP, false); // ny fil pr. session.            
            
            logger.addHandler(fileHandler);
        }
        catch(IOException | SecurityException e)
        {
            e.printStackTrace();
        }
        
        return logger;
    }
    
}
