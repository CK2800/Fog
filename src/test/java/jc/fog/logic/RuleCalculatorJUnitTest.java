/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import jc.fog.data.DbConnector;

import jc.fog.data.MaterialDAO;

import jc.fog.exceptions.FogException;
import junit.framework.Assert;
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
public class RuleCalculatorJUnitTest
{
    static Connection connection = null;    
    
    public RuleCalculatorJUnitTest()
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
    public void testCalculators() throws FogException
    {
        // Arrange
        MaterialDAO dao = new MaterialDAO(connection);
        List<MaterialDTO> materials = dao.getMaterials();        
        List<BillItem> stykliste = new ArrayList<>();
        // Skur i fuld vidde af carport, 500 cm.
        int shedWidth = 500;
        CarportRequestDTO carportRequest = new CarportRequestDTO(
                2, 0, shedWidth, 210, 800, "blabla", 120, shedWidth);
        // Opret calculator.
        Calculator calculator = new Calculator(materials);
        
        // Act        
        stykliste = calculator.calculateBill(carportRequest);
        
        // Assert
        assertTrue(stykliste.size() > 0);
    }
    
    /**
     * Tester stolpe udregning hvor skuret er i samme bredde som carporten.
     */
    @Test
    public void testPostCalculator() throws FogException
    {
        // Arrange
        MaterialDAO dao = new MaterialDAO(connection);
        List<MaterialDTO> materials = dao.getMaterials();        
        ArrayList<BillItem> stykliste = new ArrayList<>();
        // Skur i fuld vidde af carport, 500 cm.
        int shedWidth = 500;
        CarportRequestDTO forespoergsel = new CarportRequestDTO(2, 0, shedWidth, 210, 800, "blabla", 120, shedWidth);
        
        //Initialiser materialer i rule calculator.
        RuleCalculator.initializeMaterials(materials);        
        // Opret stolpeudregner.
        RuleCalculatorPost postCalculator = new RuleCalculatorPost();
        
        // Act
        int items = postCalculator.calculate(forespoergsel, stykliste);        
        BillItem billItem = stykliste.get(0);
                
        // Assert
        int expected = 7;
        Assert.assertEquals(expected, billItem.getCount());
    }
    
    @Test
    public void testRoofCalculator() throws FogException
    {
        // Arrange
        MaterialDAO dao = new MaterialDAO(connection);
        List<MaterialDTO> materials = dao.getMaterials();        
        ArrayList<BillItem> stykliste = new ArrayList<>();
        
        
        CarportRequestDTO forespoergsel = new CarportRequestDTO(
                3, 0, 600, 210, 800, "blabla", 0,0);
        RuleCalculator.initializeMaterials(materials);
        RuleCalculatorRoof roofCalculator = new RuleCalculatorRoof();
        
        // Act
        int items = roofCalculator.calculate(forespoergsel, stykliste);        
        BillItem billItem = stykliste.get(0);
                
        // Assert
        int expected = 6;
        Assert.assertEquals(expected, billItem.getCount());
    }    
}
