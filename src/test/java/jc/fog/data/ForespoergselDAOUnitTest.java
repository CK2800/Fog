/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import jc.fog.logic.ForesporgselDTO;
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
            //connection.close();
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
    @Test
    public void testOpretForespoergselMedSkur() throws SQLException
    {
        System.out.println("connection is open ?" + !connection.isClosed());
        boolean success = ForesporgselDAO.createForesporgsel(1,15,1000,250, 600, 300, 500, "Det bliver spændende");
        assertTrue(success);
        
    }
    
    @Test
    public void testOpretForespoergselUdenSkur() throws SQLException
    {
        boolean success = ForesporgselDAO.createForesporgsel(2, 30, 500, 125, 300,0,0,"Uden skur");
        assertTrue(success);
    }
    
    @Test
    public void testHentAlleForespoergsler() throws SQLException
    {        
        ForesporgselDAO.createForesporgsel(1,15,1000,250, 600, 300, 500, "Det bliver spændende");
        List<ForesporgselDTO> requests = DataFacade.getRequests();
        assertTrue(requests.size() > 0);        
    }
    
    @Test
    public void testHentEnkeltForespørgsel() throws SQLException
    {
        ForesporgselDAO.createForesporgsel(1,15,1000,250, 600, 300, 500, "Det bliver spændende");
        ForesporgselDTO request = DataFacade.getRequest(1);
        assertTrue(request != null);
    }
}
