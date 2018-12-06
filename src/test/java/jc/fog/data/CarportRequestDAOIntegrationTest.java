/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.sql.Connection;
import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.CarportRequestDTO;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Claus
 */
public class CarportRequestDAOIntegrationTest
{
    static Connection connection = null;
    public CarportRequestDAOIntegrationTest()
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
            DbConnector.closeConnection();            
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
            connection = DbConnector.getConnection();
            System.out.println("Db forbindelse åbnet");
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
    public void testConnection() throws Exception
    {
        Connection connection = DbConnector.getConnection();
        if (connection != null)
        {
            String tilstand = connection.isClosed() ? "lukket" : "åben";
            System.out.println("DB forbindelse OK. DB forbindelsen er ?.".replace("?", tilstand));
        }
        assertNotNull(connection);
    }
    
    @Test
    public void testGetCarportsIntegration() throws FogException
    {
        DataFacade df = new DataFacade(DbConnector.getConnection());
        List<CarportRequestDTO> carports = df.getCarports();
        assertTrue(carports.size() > 0);
        
    }
    
    @Test(expected = FogException.class)
    public void testGetCarportsIntegrationFails() throws Exception
    {
        
        DataFacade df = new DataFacade(DbConnector.getConnection());
        DbConnector.closeConnection();
        List<CarportRequestDTO> carports = df.getCarports();
        assertTrue(carports.size() > 0);
        
    }
    
    @Test(expected = FogException.class)    
    public void testCreateCarportRequestFailure() throws Exception
    {
       // Opret carportdao.
        CarportRequestDAO carportDAO = new CarportRequestDAO(DbConnector.getConnection());
         // luk forbindelsen så vi får fejl.
        DbConnector.closeConnection();
        boolean success = carportDAO.createCarportRequest(1,15,1000,250, 600, 300, 500, "Det bliver spændende");        
        assertFalse(success);        
    }
    
    @Test
    public void testOpretForespoergselMedSkur() throws Exception
    {   
        // Opret carportdao som opretter forbindelse, hvis den mangler.
        CarportRequestDAO carportDAO = new CarportRequestDAO(DbConnector.getConnection());         
        boolean success = carportDAO.createCarportRequest(1,15,1000,250, 600, 300, 500, "Det bliver spændende");
        assertTrue(success);        
    }
    
    @Test
    public void testOpretForespoergselUdenSkur() throws FogException
    {
        CarportRequestDAO carportDAO = new CarportRequestDAO(DbConnector.getConnection());
        boolean success = carportDAO.createCarportRequest(2, 30, 500, 125, 300,0,0,"Uden skur");
        assertTrue(success);
    }
    
    @Test
    public void testHentAlleForespoergsler() throws FogException
    {        
        CarportRequestDAO carportDAO = new CarportRequestDAO(DbConnector.getConnection());
        carportDAO.createCarportRequest(1,15,1000,250, 600, 300, 500, "Det bliver spændende");
        List<CarportRequestDTO> requests = carportDAO.getCarportRequests();
        assertTrue(requests.size() > 0);        
    }
    
    @Test
    public void testHentEnkeltForespørgsel() throws FogException
    {
        CarportRequestDAO carportDAO = new CarportRequestDAO(DbConnector.getConnection());
        carportDAO.createCarportRequest(1,15,1000,250, 600, 300, 500, "Det bliver spændende");
        CarportRequestDTO request = carportDAO.getCarportRequest(1);
        assertTrue(request != null);
    }
}
