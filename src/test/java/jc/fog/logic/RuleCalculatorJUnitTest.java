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
    public void testCalculatorInitialization() throws FogException
    {
        // Arrange
//        MaterialDAO dao = new MaterialDAO(connection);
//        List<MaterialDTO> materials = dao.getMaterials();        
//        
//        // Act
//        Calculator.calculateBill(carportRequest, materials)
//        
//        // Assert
//        assertTrue(Calculator.materials.size() > 0);
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
        CarportRequestDTO forespoergsel = new CarportRequestDTO(
                2, 0, shedWidth, 210, 800, "blabla", 120, shedWidth);        
        RuleCalculatorPost postCalculator = new RuleCalculatorPost(materials);
        
        // Act
        int items = postCalculator.calculate(forespoergsel, stykliste);        
        BillItem billItem = stykliste.get(0);
                
        // Assert
        int expected = 7;
        Assert.assertEquals(expected, billItem.getCount());
    }
    
    public void TestRuleCalculatorBattens() throws FogException
    {
        //CarportRequestDTO forespoergsel = new CarportRequestDTO(0, 2, 15, 
    }
    
//    @Test
//    public void CalculateStolper() throws FogException
//    {
//        List<MaterialeDTO> materialer = MaterialeDAO.getMaterialer();
//        CarportRequestDTO forespoergsel = ForesporgselDAO.getForesporgselSingle(1);
//        forespoergsel.getSkurDTO().setBredde(forespoergsel.getBredde()); // skur og carport lige brede.
//        List<StyklisteItem> stykliste = new ArrayList<StyklisteItem>();
//        
//        StolpeCalculatorRule calc = new StolpeCalculatorRule();
//        calc.calculate(forespoergsel, materialer, stykliste);
//        
//        assertTrue(stykliste.size() == 1);
//    }
//    
//    @Test
//    public void CalculateRem() throws FogException
//    {
//        List<MaterialeDTO> materialer = MaterialeDAO.getMaterialer();
//        CarportRequestDTO forespoergsel = ForesporgselDAO.getForesporgselSingle(1);
//        forespoergsel.setLaengde(1501);
//        List<StyklisteItem> stykliste = new ArrayList<StyklisteItem>();
//        
//        RemCalculatorRule calc = new RemCalculatorRule();
//        calc.calculate(forespoergsel, materialer, stykliste);
//        
//        assertTrue(stykliste.size() == 1);
//    }
}
