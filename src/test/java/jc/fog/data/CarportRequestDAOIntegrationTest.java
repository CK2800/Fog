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
        connection = DbConnector.getConnection();
        if (connection != null)
        {
            String tilstand = connection.isClosed() ? "lukket" : "åben";
            System.out.println("DB forbindelse OK. DB forbindelsen er ?.".replace("?", tilstand));
        }
        assertNotNull(connection);
    }
    
    
    /******************* Data facade tests *************************/    
    
    
    /** Tester DataFacade ved at hente alle carport forespørgsler. */
    @Test
    public void GetCarports() throws FogException
    {
        // Arrange
        DataFacade df = new DataFacade(connection);
        List<CarportRequestDTO> carports = null;
        
        // Act
        carports = df.getCarports();
        
        // Assert
        assertTrue(carports != null);        
    }
    
    /** Tester at DataFacade fejler korrekt i forsøget på at hente carport forespørgsler */
    @Test(expected = FogException.class)    
    public void getCarportsFails() throws Exception
    {        
        // Arrange
        DataFacade df = new DataFacade(connection);        
        List<CarportRequestDTO> carports = null;        
        DbConnector.closeConnection();
        
        // Act        
        carports = df.getCarports();
        // genåbn forbindelse.
        connection = DbConnector.getConnection();
        
        // Assert
        assertTrue(carports == null);        
    }
    
    @Test
    public void getCarport() throws FogException
    {
        // Arrange
        DataFacade df = new DataFacade(connection);
        
        // Act
        CarportRequestDTO carport = df.getCarport(1);
        
        // Assert
        assertTrue(carport != null);        
    }
        
    
    public void updateRequest() throws FogException
    {        
        // Arrange
        DataFacade df = new DataFacade(connection);
        CarportRequestDTO carport = df.getCarport(1);
        carport.setRemark(carport.getRemark() + " ændret i integration test");
        
        // Act
        //df.updateRequest(carport.getId(), carport.getShedId(), shedCheck, 0, 0, 0, 0, 0, 0, remark);
        
        
        
    }
    
    /************************* CarportRequestDAO tests *************************/
    
    
    /** Tester at oprettelse af carport request fejler. */
    @Test(expected = FogException.class)    
    public void createCarportRequestFails() throws Exception
    {
       // Opret carport dao.
        CarportRequestDAO carportDAO = new CarportRequestDAO(connection);
         // luk forbindelsen så vi får fejl.
        DbConnector.closeConnection();
        boolean success = carportDAO.createCarportRequest(1,15,1000,250, 600, 300, 500, "Denne carport request skal fejle.");        
        // Genåbn forbindelsen.
        connection = DbConnector.getConnection();
        
        assertFalse(success);        
    }
    
    @Test
    public void testOpretForespoergselMedSkur() throws Exception
    {   
        // Opret carportdao som opretter forbindelse, hvis den mangler.
        CarportRequestDAO carportDAO = new CarportRequestDAO(connection);         
        boolean success = carportDAO.createCarportRequest(3, 0, 600, 210, 1000, 275, 125, "600 x 1000 m skur 275 x 125, fladt tag.");
        assertTrue(success);        
    }
    
    @Test
    public void testOpretForespoergselUdenSkur() throws FogException
    {
        CarportRequestDAO carportDAO = new CarportRequestDAO(connection);
        boolean success = carportDAO.createCarportRequest(2, 30, 500, 125, 750,0,0,"500 x 750, 30 graders taghældning, uden skur");
        assertTrue(success);
    }
    
    @Test
    public void testHentAlleForespoergsler() throws FogException
    {        
        CarportRequestDAO carportDAO = new CarportRequestDAO(connection);
        carportDAO.createCarportRequest(1,15,1000,250, 600, 300, 500, "Det bliver spændende");
        List<CarportRequestDTO> requests = carportDAO.getCarportRequests();
        assertTrue(requests.size() > 0);        
    }
    
    @Test
    public void testHentEnkeltForespørgsel() throws FogException
    {
        CarportRequestDAO carportDAO = new CarportRequestDAO(connection);
        carportDAO.createCarportRequest(1,15,1000,250, 600, 300, 500, "Det bliver spændende");
        CarportRequestDTO request = carportDAO.getCarportRequest(1);
        assertTrue(request != null);
    }
}
