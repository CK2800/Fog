/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import jc.fog.exceptions.FogException;

/**
 *
 * @author Claus
 */
public class DbConnector
{    
    /**
    * Class of the database driver.
    */
    public final static String DRIVER_CLASS = "com.mysql.jdbc.Driver";        

    /**
     * Statisk, deles på tværs af alle instanser af DbConnector. 
     * Singleton.
     */
    private static Connection connection;
    
    /**
     * Establishes the connection to the database with the connection properties
     * read from db.properties ressource file.
     * @return Connection som er forbindelse til databasen.
     * @throws jc.fog.exceptions.FogException     
     */
    public static Connection getConnection() throws FogException 
    {
        if ( connection == null ) {
            try
            {
                Class.forName( DRIVER_CLASS );

                // Read the properties of the database connection from target/classes/db.properties
                Properties dbProperties = new Properties();
                InputStream inputStream = DbConnector.class.getResourceAsStream("/db.properties");
                dbProperties.load(inputStream);                
                connection = DriverManager.getConnection(dbProperties.getProperty("URL"), 
                                                         dbProperties.getProperty("USERNAME"), 
                                                         dbProperties.getProperty("PASSWORD"));                
            }            
            catch(Exception e)
            {
                throw new FogException("Fejl v. etablering af db. forbindelse.", e.getMessage(), e);
            }
        }
        return connection;
    }
    
    public static void closeConnection() throws FogException
    {
        
        if (connection != null)
        {
            try
            {
                connection.close();
            }
            catch(SQLException s)
            {
                throw new FogException("Forbindelse til databasen fejlede.", "Db forbindelse ej lukket: " + s.getMessage(), s);
            }
            connection = null;
        }
    }
    
    /**
     * Klassemetode til at sætte en ny forbindelse.
     * Brug denne hvis systemet skal testes mod en anden database.
     * @param connection 
     */
    public static void setConnection(Connection connection)
    {
        DbConnector.connection = connection;
    }
}