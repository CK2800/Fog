/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.sql.Connection;
import java.sql.SQLException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Claus
 */
public class ForespoergselDAOUnitTest
{
    static Connection connection = null;
    public ForespoergselDAOUnitTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
        try
        {
            connection.close();
            System.out.println("Db forbindelse lukket.");
        }
        catch(Exception e)
        {
            System.out.println("Database connection was not closed: " + e.getMessage());
        }
    }
    
    @Before
    public void setUp()
    {
        try
        {
            connection = DbConnection.getConnection();         
        }
        catch(Exception e)
        {
            System.out.println("No database connection established: " + e.getMessage());
        }
    }
    
    @After
    public void tearDown()
    {        
    }

    /**
     * Test connection is established.
     */
    @Test
    public void testConnection() throws SQLException
    {
        if (connection != null)
        {
            String tilstand = connection.isClosed() ? "lukket" : "åben";
            System.out.println("DB forbindelse OK. DB forbindelsen er ?.".replace("?", tilstand));
        }
        assertNotNull(connection);
    }
    @Test
    public void testForespoergselMedSkur() throws SQLException
    {        
        boolean success = ForesporgselDAO.createForesporgsel(1,15,1000,250, 600, 300, 500, "Det bliver spændende");
        assertTrue(success);        
    }
    
    @Test
    public void testForespoergselUdenSkur() throws SQLException
    {
        boolean success = ForesporgselDAO.createForesporgsel(2, 30, 500, 125, 300,0,0,"Uden skur");
        assertTrue(success);
    }
}
