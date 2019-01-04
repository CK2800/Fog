/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic.calculators;

import jc.fog.logic.RulesCalculator;
import java.sql.Connection;
import java.util.List;
import jc.fog.data.DbConnector;
import jc.fog.data.dao.MaterialDAO;
import jc.fog.exceptions.FogException;
import jc.fog.logic.BillItem;
import jc.fog.logic.dto.CarportRequestDTO;
import jc.fog.logic.dto.MaterialDTO;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Claus
 */
public class RulesCalculatorITest
{
    static Connection connection;
    
    public RulesCalculatorITest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
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
         
    }
    
    @After
    public void tearDown()
    {
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
        CarportRequestDTO forespoergsel = new CarportRequestDTO(3, 0, 600, 210, 800, "carport 600 x 800 cm, intet skur, fladt plast tag", 0,0); 
        RulesCalculator.initializeMaterials(materials); // Initialiser RulesCalculator.
        RulesCalculatorRoof roofCalculator = new RulesCalculatorRoof(); // Opret tag udregner.
        
        // Act
        stykliste = roofCalculator.calculate(forespoergsel);        
        BillItem billItem = stykliste.get(0);
                
        // Assert
        int expected = 6; // Vi forventer 6 tagplader, idet deres bredde er 109 cm.
        Assert.assertEquals(expected, billItem.getCount());
    }
    
    @Test
    public void testRoofCalculatorSloped() throws FogException
    {
        // Arrange
        MaterialDAO dao = new MaterialDAO(connection); // forbindelse
        List<MaterialDTO> materials = dao.getMaterials(); // materialer
        List<BillItem> stykliste; // tom stykliste 
        CarportRequestDTO forespoergsel = new CarportRequestDTO(2, 45, 600, 210, 800, "carport 600 x 800 cm, skur 210 x 600 cm, 45 graders hældning på tag.", 210, 600);
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
        CarportRequestDTO forespoergsel = new CarportRequestDTO(2, 0, shedWidth, 210, 800, "Skur og carport i samme bredde.", 120, shedWidth); // carport, 500 x 800 cm., med plastic tag, med skur i fuld bredde.
        
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
    
    
    
    /**
     * Tester RulesCalculatorRoof med tagtype 3 (fladt plastic tag).
     * @throws FogException 
     */
    @Test
    public void testHeadCalculatorNoSlope() throws FogException
    {
        // Arrange
        MaterialDAO dao = new MaterialDAO(connection); // forbindelse
        List<MaterialDTO> materials = dao.getMaterials(); // materialer
        List<BillItem> stykliste; // tom stykliste        
        
        // carport, 610 x 800 cm., med plastic tag, uden skur. 
        CarportRequestDTO forespoergsel = new CarportRequestDTO(3, 0, 610, 210, 800, "blabla", 0,0); // bredde 610 giver 1 brud på spær, dvs. 3 remme med 2 stk. træ i hver = 6.
        RulesCalculator.initializeMaterials(materials); // Initialiser RulesCalculator.
        RulesCalculatorHead headCalculator = new RulesCalculatorHead(); // Opret rem udregner.
        
        // Act
        stykliste = headCalculator.calculate(forespoergsel);        
        BillItem billItem = stykliste.get(0);
                
        // Assert
        int expected = 6; // Vi forventer 6 stk. træ, idet 2 remme i hver side + 1 hvor spær brydes = 3, og hver rem skal bruge 2 stk. træ.
        Assert.assertEquals(expected, billItem.getCount());
    }    
}
