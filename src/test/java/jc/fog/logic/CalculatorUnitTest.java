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

/**
 *
 * @author Claus
 */
public class CalculatorUnitTest
{
    static Connection connection = null;    
    
    public CalculatorUnitTest()
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
    
    /** Tester udregningen af materialer for stolper. */
    @Test
    public void postCalculateMaterial() throws FogException
    {
        // Arrange
        List<MaterialDTO> materials = new ArrayList();
        // Opret stolpe
        materials.add(new MaterialDTO(1, Rules.Materialtype.POST.getMaterialtypeId(), "stolpe", 20, "stk", "stolpetekst", 0, 25));
        // Opret spær træ på 1 mtr, hvilket giver brud på rem og spær for hver meter.
        materials.add(new MaterialDTO(1, Rules.Materialtype.RAFTERS.getMaterialtypeId(), "test bræt 1", 100, "stk", "bræt", 0, 25));
        RulesCalculator.initializeMaterials(materials);
        // Opret carport i 5 x 8 mtr, skur i 1.2 x 5 mtr.
        CarportRequestDTO carportRequest = new CarportRequestDTO(2, 0, 500, 210, 800, "carport 500 x 800 cm, skur 500 x 120 cm, fladt tag.", 120, 500);
        RulesCalculatorPost calculator = new RulesCalculatorPost();
        
        // Act
        MaterialCount mc = calculator.calculateMaterials(carportRequest);
        
        // Assert
        // Carport i 5 x 8 mtr giver 9 x 6 stolper + skurets (som er i fuld bredde) 3 => 57.
        assertEquals(mc.getCount(), 57);         
    }
    
    /** Tester fejlet udregning af materialer for stolper. */
    @Test(expected = FogException.class)
    public void postCalculateMaterialFail() throws FogException
    {
        // Arrange        
        List<MaterialDTO> materials = new ArrayList();
        // Opret stolpe
        materials.add(new MaterialDTO(1, Rules.Materialtype.POST.getMaterialtypeId(), "stolpe", 20, "stk", "stolpetekst", 0, 25));
        // Opret spær træ på 0 mtr., som stolpeberegner skal bruge, for at beregne antal og placering af evt. ekstra stolper.
        materials.add(new MaterialDTO(1, Rules.Materialtype.RAFTERS.getMaterialtypeId(), "test bræt 1", 0, "stk", "bræt", 0, 25));
        // Initialiser materialer for beregnere.
        RulesCalculator.initializeMaterials(materials);
        // Opret carport forespørgsel og beregner.
        CarportRequestDTO carportRequest = new CarportRequestDTO(2, 0, 500, 210, 800, "carport 500 x 800 cm, skur 500 x 120 cm, fladt tag.", 120, 500);
        RulesCalculatorPost calculator = new RulesCalculatorPost();
        
        // Act
        MaterialCount mc = calculator.calculateMaterials(carportRequest);
        
        // Assert - her skal vi have FogException.
        assertEquals(mc.getCount(), 57);        
    }
    
    /** Tester udregning af materialer for spær til fladt tag. */
    @Test
    public void raftersCalculateMaterialNoSlope() throws FogException
    {
        // Arrange.
        List<MaterialDTO> materials = new ArrayList();
        // Opret spær træ med længde på 100 cm.
        materials.add(new MaterialDTO(1, Rules.Materialtype.RAFTERS.getMaterialtypeId(), "test bræt 2", 100, "stk", "bræt", 0, 12));
        // Initialiser materialer for beregnere.
        RulesCalculator.initializeMaterials(materials);
        // Opret carport forespørgsel og beregner.
        CarportRequestDTO carportRequest = new CarportRequestDTO(2,0,500,210,800,"carport 500 x 800 cm, skur 500 x 120 cm, fladt tag.",120,500); 
        RulesCalculatorRafters calculator = new RulesCalculatorRafters();
        
        // Act.
        MaterialCount mc = calculator.calculateMaterials(carportRequest);
        
        // Assert.
        /*
        Tagets længde: 800 - 2 * udhæng => 740 cm.
        Tagets bredde: 500 cm.
        Brættets længde: 100 cm. => 5 brædder pr. spær.
        Afstand ml. spær, fladt tag: max 55 cm => 14 mellemrum ml. 15 spær => 15 x 5 = 75 brædder.        
        */
        int expected = 75;
        assertEquals(mc.getCount() * calculator.getRaftersCount(), expected);        
    }
    
    /** Tester udregning af materialer for spær til tag med rejsning. */
    @Test
    public void raftersCalculateMaterialSlope() throws FogException
    {
        // Arrange.
        List<MaterialDTO> materials = new ArrayList();
        // Opret byg selv spær, længde er ligegyldig, da byg-selv spær kan spænde over alle vidder.
        materials.add(new MaterialDTO(1, Rules.Materialtype.PRE_FAB_RAFTERS.getMaterialtypeId(), "byg selv spær", 0, "stk", "byg selv spær", 0, 12));
        // Initialiser materialer for beregnere.
        RulesCalculator.initializeMaterials(materials);
        // Opret carport forespørgsel og beregner.
        CarportRequestDTO carportRequest = new CarportRequestDTO(2,15,500,210,800,"carport 500 x 800 cm, skur 500 x 120 cm, tag m. rejsning.",120,500); 
        RulesCalculatorRafters calculator = new RulesCalculatorRafters();
        
        // Act.
        MaterialCount mc = calculator.calculateMaterials(carportRequest);
        
        // Assert.
        /*
        Tagets længde: 800 - 2 * udhæng => 740 cm.
        Tagets bredde: 500 cm.
        Byg selv spær kan spænde over hele carportens bredde.
        Afstand ml. spær, fladt tag: max 89 cm => 9 mellemrum ml. 10 spær.
        */
        int expected = 10;
        assertEquals(mc.getCount() * calculator.getRaftersCount(), expected);        
    }
    
    /** Tester om udregning af materialer for spær til fladt tag fejler. */
    @Test(expected = FogException.class)
    public void raftersCalculateMaterialFail() throws FogException
    {        
        // Arrange.
        List<MaterialDTO> materials = new ArrayList();
        // Opret spær træ med negativ længde.
        materials.add(new MaterialDTO(1, Rules.Materialtype.RAFTERS.getMaterialtypeId(), "test bræt 2", -100, "stk", "bræt", 0, 12));
        // Initialiser materialer for beregnere.
        RulesCalculator.initializeMaterials(materials);
        // Opret carport forespørgsel og beregner.
        CarportRequestDTO carportRequest = new CarportRequestDTO(2,0,500,210,800,"carport 500 x 800 cm, skur 500 x 120 cm, fladt tag.",120,500); 
        RulesCalculatorRafters calculator = new RulesCalculatorRafters();         
        
        // Act.
        MaterialCount mc = calculator.calculateMaterials(carportRequest);
        
        // Assert.
        assertTrue(mc.getMaterialDTO() != null);        
    }
    
    /** Tester om udregning af materialer fejler hvis ingen materialer findes. */
    @Test(expected = FogException.class)
    public void calculateMaterialFail() throws FogException
    {
        // Arrange
        RulesCalculator.initializeMaterials(null);
        // Opret carport forespørgsel og beregner.
        CarportRequestDTO carportRequest = new CarportRequestDTO(2,0,500,210,800,"carport 500 x 800 cm, skur 500 x 120 cm, fladt tag.",120,500); 
        RulesCalculatorRafters calculator = new RulesCalculatorRafters();         
        
        // Act.
        MaterialCount mc = calculator.calculateMaterials(carportRequest);
        
        // Assert.
        assertTrue(mc.getMaterialDTO() != null);
    }
    
    /** Tester udregning af hypotenusen, dvs. længden på den skrånende tagflade. */
    @Test
    public void calculateHypothenuse() throws FogException
    {
        // Arrange.
        RulesCalculator calculator = new RulesCalculatorHead();
        
        // Act.
        double width = calculator.calculateSlopedWidth(500, 25); // 500 cm tagflade med 25 graders hældning.
        
        // Assert.
        assertEquals((int)Math.ceil(width), 552);        
    }
    
    /** Tester udregning af hypotenusen med vinkel på 90 grader */
    @Test(expected = FogException.class)
    public void calculateHypothenuseFailAngleTooBig() throws FogException
    {
        // Arrange.
        RulesCalculator calculator = new RulesCalculatorHead();
        // Act.
        double width = calculator.calculateSlopedWidth(500, 90); // Ugyldig vidde skal give FogException.
        // Assert.
        assertEquals((int)Math.ceil(width), 552);        
    }
    /** Tester udregning af hypotenusen med vinkel på 0 grader */
    @Test(expected = FogException.class)
    public void calculateHypothenuseFailAngleTooSmall() throws FogException
    {
        // Arrange.
        RulesCalculator calculator = new RulesCalculatorHead();
        // Act.
        double width = calculator.calculateSlopedWidth(500, 0); // Ugyldig vidde skal give FogException.
        // Assert.
        assertEquals((int)Math.ceil(width), 552);        
    }
}