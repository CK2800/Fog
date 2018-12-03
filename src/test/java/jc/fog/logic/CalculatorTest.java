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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Claus
 */
public class CalculatorTest
{
    static Connection connection = null;    
    
    public CalculatorTest()
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
        MaterialDAO dao = new MaterialDAO(connection); // forbindelse.
        List<MaterialDTO> materials = dao.getMaterials(); // materialer.        
        List<BillItem> stykliste; // tom stykliste.        
        int shedWidth = 500; // skur vidde.
        CarportRequestDTO forespoergsel = new CarportRequestDTO(2, 0, shedWidth, 210, 800, "blabla", 120, shedWidth); // carport, 500 x 800 cm., med plastic tag, med skur i fuld bredde.
        
        //Initialiser materialer i rule calculator.
        RulesCalculator.initializeMaterials(materials);        
        // Opret stolpeudregner.
        RulesCalculatorPost postCalculator = new RulesCalculatorPost();
        
        // Act
        
        stykliste = postCalculator.calculate(forespoergsel);        
        BillItem billItem = stykliste.get(0);
                
        // Assert
        int expected = 9; // 3 på hver rem, 3 til skur.
        assertEquals(expected, billItem.getCount());
    }
    
    
    @Test
    public void testRoofCalculatorSloped() throws FogException
    {
        // Arrange
        MaterialDAO dao = new MaterialDAO(connection); // forbindelse
        List<MaterialDTO> materials = dao.getMaterials(); // materialer
        List<BillItem> stykliste; // tom stykliste 
        CarportRequestDTO forespoergsel = new CarportRequestDTO(1, 45, 600, 210, 800, "45 grader", 210, 600);
        RulesCalculator.initializeMaterials(materials);
        RulesCalculatorRoof roofCalculator = new RulesCalculatorRoof();
        
        // Act
        stykliste = roofCalculator.calculate(forespoergsel);
        BillItem billItem = null;
        for(BillItem b : stykliste)
            if (b.getMaterialDTO().getMaterialtypeDTO().getId() == 7) // tagfladebelægning
                billItem = b;
        
        // Assert
        // Tagsten er 25 x 50 cm
        // Taghældningens bredde er 300 / cos(45) ~ 424,264 cm => 424,264 / 50 = 9 hele rækker.
        // Taglængden er 800 => 800 / 25 = 32 kolonner
        // 9 rk a 32 kolonner = 288 teglsten.        
        int expectedTiles = 288;
        Assert.assertEquals(expectedTiles, billItem.getCount() );
    }
    /**
     * Tester RulesCalculatorRoof med tagtype 3 (fladt plastic tag).
     * @throws FogException 
     */
    @Test
    public void testRoofCalculatorNoSlope() throws FogException
    {
        // Arrange
        MaterialDAO dao = new MaterialDAO(connection); // forbindelse
        List<MaterialDTO> materials = dao.getMaterials(); // materialer
        List<BillItem> stykliste; // tom stykliste        
        CarportRequestDTO forespoergsel = new CarportRequestDTO(3, 0, 600, 210, 800, "blabla", 0,0); // carport, 600 x 800 cm., med plastic tag, uden skur.        
        RulesCalculator.initializeMaterials(materials); // Initialiser RulesCalculator.
        RulesCalculatorRoof roofCalculator = new RulesCalculatorRoof(); // Opret tag udregner.
        
        // Act
        stykliste = roofCalculator.calculate(forespoergsel);        
        BillItem billItem = stykliste.get(0);
                
        // Assert
        int expected = 6; // Vi forventer 6 tagplader, idet deres bredde er 109 cm.
        Assert.assertEquals(expected, billItem.getCount());
    }    
}
