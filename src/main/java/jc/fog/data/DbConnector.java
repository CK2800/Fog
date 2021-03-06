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
 * Konfigurerer forbindelse til databasen og implementerer forbindelsen som singleton.
 * @author Claus
 */
public class DbConnector
{    
    /**
    * Class of the database driver.
    */
    private final static String DRIVER_CLASS = "com.mysql.jdbc.Driver";    
    private static String URL = null;
    private static String USERNAME = null;
    private static String PASSWORD = null;
    
    /**
     * Statisk, deles på tværs af alle instanser af DbConnector. 
     * Singleton.
     */
    private static Connection connection;
    
    /**
     * Etablerer forbindelse til databasen.     
     * @return Connection som er forbindelse til databasen.
     * @throws jc.fog.exceptions.FogException     
     */
    public static Connection getConnection() throws FogException 
    {
        // Hvis connection er etableret, se om den er lukket.
        if (connection != null)
        {
            try
            {
                if (connection.isClosed())                     
                    connection = null;                
            }
            catch(SQLException s)
            {                
                // Får vi exception ved isClosed, fjern reference så den kan garbage collectes.
                connection = null;
            }
        }
        if ( connection == null) {
            try
            {
                Class.forName( DRIVER_CLASS );
                
                // Read the properties of the database connection from target/classes/db.properties if fields are empty.
                if (URL == null || USERNAME == null || PASSWORD == null)
                {
                    Properties dbProperties = new Properties();
                    InputStream inputStream = DbConnector.class.getResourceAsStream("/db.properties");
                    dbProperties.load(inputStream);                
                    URL = dbProperties.getProperty("URL");
                    USERNAME = dbProperties.getProperty("USERNAME");
                    PASSWORD = dbProperties.getProperty("PASSWORD");
                }
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);                
            }            
            catch(Exception e)
            {
                throw new FogException("Fejl v. etablering af db. forbindelse.", e.getMessage(), e);
            }
        }
        return connection;
    }
    
    /**
     * Lukker forbindelsen.
     * @throws FogException 
     */
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
            finally
            {
                connection = null;
            }
        }
    }
    
    /**
     * Sæt en forbindelse manuelt.
     * Brug denne hvis systemet skal testes mod en anden database.
     * @param connection 
     */
    public static void setConnection(Connection connection)
    {
        DbConnector.connection = connection;
    }
}