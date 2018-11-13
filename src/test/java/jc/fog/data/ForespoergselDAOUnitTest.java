/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.sql.Connection;
import jc.fog.logic.ForesporgselDAO;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Claus
 */
public class ForespoergselDAOUnitTest
{
    Connection connection = null;
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
        try
        {
            connection.close();
        }
        catch(Exception e)
        {
            System.out.println("Database connection was not closed: " + e.getMessage());
        }
    }

    /**
     * Test connection is established.
     */
    @Test
    public void testConnection()
    {
        if (connection != null)
            System.out.println("DB forbindelse OK");
        assertNotNull(connection);
    }
//    @Test
//    public void testForespoergselMedSkur()
//    {
//        // Mangler den korrekte signatur, afv.
//        //ForesporgselDAO.createForesporgsel(0, 0, 0, 0, bemaerkning)
//    }
}
