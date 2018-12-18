/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import jc.fog.data.dao.CarportRequestDAO;
import java.sql.Connection;
import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.dto.CarportRequestDTO;
import jc.fog.logic.dto.ShedDTO;
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
public class CarportRequestDaoITest
{
    static Connection connection = null;
    public CarportRequestDaoITest()
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
    
    
    /** Tester DataFacadeImpl ved at hente alle carport forespørgsler. */
    @Test
    public void getCarports() throws FogException
    {
        // Arrange
        DataFacadeImpl df = new DataFacadeImpl(connection);
        List<CarportRequestDTO> carports = null;
        df.createCarPort(1, 15, 1000, 600, 210, 300, 500, "Test carport.");                
        
        // Act
        carports = df.getCarports();
        
        
        // Assert
        assertTrue(carports != null);        
    }
    
    /** Tester at DataFacadeImpl fejler korrekt i forsøget på at hente carport forespørgsler */
    @Test(expected = FogException.class)    
    public void getCarportsFails() throws Exception
    {        
        // Arrange
        DataFacadeImpl df = new DataFacadeImpl(connection);        
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
        DataFacadeImpl df = new DataFacadeImpl(connection);
        int id = df.createCarPort(1, 15, 1000, 600, 210, 300, 500, "Test carport.");                
        
        // Act
        CarportRequestDTO carport = df.getCarport(id);
        
        // Assert
        assertTrue(carport != null);        
    }
        
    @Test
    public void updateRequest() throws FogException
    {        
        // Arrange
        DataFacadeImpl df = new DataFacadeImpl(connection);
        int id = df.createCarPort(1, 15, 1000, 600, 210, 300, 500, "Test carport.");                
        CarportRequestDTO carport = df.getCarport(id);
        carport.setRemark(carport.getRemark() + " ændret i integration test");
                
        // Act
        ShedDTO shed = carport.getShedDTO();
        boolean success = df.updateRequest(carport.getId(), carport.getShedId(), "on", carport.getSlope(), carport.getWidth(), 
                carport.getLength(), shed.getWidth(), shed.getLength(), carport.getRooftypeId(), carport.getRemark());
        
        // Assert
        assertTrue(success);        
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
        int id = carportDAO.createCarportRequestAndShed(1,15,1000,250, 600, 300, 500, "Denne carport request skal fejle.");        
        // Genåbn forbindelsen.
        connection = DbConnector.getConnection();
        
        assertFalse(id > 0);        
    }
    
    @Test
    public void createRequestWithShed() throws Exception
    {   
        // Opret carportdao som opretter forbindelse, hvis den mangler.
        CarportRequestDAO carportDAO = new CarportRequestDAO(connection);
        int id = 0;
        id = carportDAO.createCarportRequestAndShed(3, 0, 600, 210, 1000, 275, 125, "600 x 1000 m skur 275 x 125, fladt tag.");
        assertTrue(id != 0);        
    }
    
    @Test
    public void createRequestNoShed() throws FogException
    {
        CarportRequestDAO carportDAO = new CarportRequestDAO(connection);
        int id = 0;
        id = carportDAO.createCarportRequestAndShed(2, 30, 500, 125, 750,0,0,"500 x 750, 30 graders taghældning, uden skur");
        assertTrue(id != 0);
    }
    
    @Test
    public void getRequests() throws FogException
    {        
        CarportRequestDAO carportDAO = new CarportRequestDAO(connection);
        carportDAO.createCarportRequestAndShed(1,15,1000,250, 600, 300, 500, "Det bliver spændende");
        List<CarportRequestDTO> requests = carportDAO.getCarportRequests();
        assertTrue(requests.size() > 0);        
    }
    
    @Test
    public void getRequest() throws FogException
    {
        CarportRequestDAO carportDAO = new CarportRequestDAO(connection);
        int id = carportDAO.createCarportRequestAndShed(1,15,1000,250, 600, 300, 500, "Det bliver spændende");
        CarportRequestDTO request = carportDAO.getCarportRequest(id);
        assertTrue(request != null);
    }
}
