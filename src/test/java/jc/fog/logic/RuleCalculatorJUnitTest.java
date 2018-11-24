/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.sql.Connection;
import java.util.List;
import jc.fog.data.DataFacade;
import jc.fog.data.DbConnection;

import jc.fog.data.CarPortDAO;
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
public class RuleCalculatorJUnitTest
{
    static Connection connection = null;
    static List<MaterialeDTO> materialer;
    
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
            DbConnection.closeConnection();            
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
            connection = DbConnection.getConnection(); 
            System.out.println("Db forbindelse åbnet");
            
            if (materialer == null)
                materialer = DataFacade.getMaterials();
            
            
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
    public void TestCalculator() throws FogException
    {

        materialer = MaterialDAO.getMaterials();        
        CarportRequestDTO forespoergsel = CarPortDAO.getCarportRequest(1);

        forespoergsel.getShedDTO().setBredde(forespoergsel.getWidth());
        //forespoergsel.setHaeldning(0); fladt tag.
        forespoergsel.setLength(1000);
        List<BillItem> stykliste = Calculator.beregnStykliste(forespoergsel, materialer);
        assertTrue(stykliste.size() > 0);
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
