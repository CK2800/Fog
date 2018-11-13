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

/**
 *
 * @author Claus
 */
public class DbConnection
{
    
    /**
    * Class of the database driver.
    */
    public final static String DRIVER_CLASS = "com.mysql.jdbc.Driver";        

    
    private static Connection connection;

    public static void setConnection( Connection con ) {
        connection = con;
    }

    /**
     * Establishes the connection to the database with the connection properties
     * read from db.properties ressource file.
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException 
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if ( connection == null ) {
            try
            {
                Class.forName( DRIVER_CLASS );

                // Read the properties of the database connection from target/classes/db.properties
                Properties dbProperties = new Properties();
                InputStream inputStream = DbConnection.class.getResourceAsStream("/db.properties");
                dbProperties.load(inputStream);                
                connection = DriverManager.getConnection(dbProperties.getProperty("URL"), 
                                                         dbProperties.getProperty("USERNAME"), 
                                                         dbProperties.getProperty("PASSWORD"));                
            }
            catch(Exception e)
            {
                System.out.println("Fejl v. etablering af db. forbindelse: " + e.getMessage());
            }
        }
        return connection;
    }
    
    public static void closeConnection() throws SQLException
    {
        if (connection != null)
            connection.close();
        connection = null;
    }

}