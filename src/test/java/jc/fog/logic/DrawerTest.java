/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.sql.Connection;
import java.util.List;
import jc.fog.data.DbConnector;
import jc.fog.data.MaterialDAO;
import jc.fog.exceptions.FogException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Claus
 */
public class DrawerTest
{
    static Connection connection = null; 
    public DrawerTest()
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
            System.out.println("setUp not completed: " + e.getMessage());
        }
    }
    
    @After
    public void tearDown()
    {
    }

    @Test
    public void unitTestDrawerShed() throws FogException
    {
        // Arrange
        // Carport med skur
        CarportRequestDTO carportRequest = new CarportRequestDTO(
                2, 0, 500, 210, 800, "blabla", 120, 500);
        RulesCalculatorShed rcs = new RulesCalculatorShed();
        
        // Act.
        List<Rectangle> rectangles = rcs.draw(carportRequest);
        
        // Assert.
        assertTrue(rectangles.size() == 1); // 1 rektangel for skur.
    }
    
    
    
    @Test
    public void integrationTestDrawers() throws FogException
    {
        // Arrange
        MaterialDAO dao = new MaterialDAO(connection);
        List<MaterialDTO> materials = dao.getMaterials();                
        
        // Skur i fuld vidde af carport, 500 cm.
        int shedWidth = 500;
        CarportRequestDTO carportRequest = new CarportRequestDTO(
                2, 0, shedWidth, 210, 800, "blabla", 120, shedWidth);
        
        // Act
        List<Rectangle> rects = LogicFacade.drawCarport(carportRequest, materials);
        
        // Assert
        assertTrue(rects.size() > 0);
        
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
}
