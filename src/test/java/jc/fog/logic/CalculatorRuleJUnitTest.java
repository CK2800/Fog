/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import jc.fog.data.DbConnection;
import jc.fog.data.ForesporgselDAO;
import jc.fog.data.MaterialeDAO;
import jc.fog.exceptions.FogException;
import jc.fog.logic.calculator.Calculator;
import jc.fog.logic.calculator.RemCalculatorRule;
import jc.fog.logic.calculator.StolpeCalculatorRule;
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
public class CalculatorRuleJUnitTest
{
    static Connection connection = null;
    
    public CalculatorRuleJUnitTest()
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
    
    @Test
    public void TestCalculator() throws FogException
    {
        List<MaterialeDTO> materialer = MaterialeDAO.getMaterialer();        
        ForesporgselDTO forespoergsel = ForesporgselDAO.getForesporgselSingle(1);
        forespoergsel.getSkurDTO().setBredde(forespoergsel.getBredde());
        //forespoergsel.setHaeldning(0); fladt tag.
        forespoergsel.setLaengde(1000);
        List<StyklisteItem> stykliste = Calculator.beregnStykliste(forespoergsel, materialer);
        assertTrue(stykliste.size() > 0);
    }
    
//    @Test
//    public void CalculateStolper() throws FogException
//    {
//        List<MaterialeDTO> materialer = MaterialeDAO.getMaterialer();
//        ForesporgselDTO forespoergsel = ForesporgselDAO.getForesporgselSingle(1);
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
//        ForesporgselDTO forespoergsel = ForesporgselDAO.getForesporgselSingle(1);
//        forespoergsel.setLaengde(1501);
//        List<StyklisteItem> stykliste = new ArrayList<StyklisteItem>();
//        
//        RemCalculatorRule calc = new RemCalculatorRule();
//        calc.calculate(forespoergsel, materialer, stykliste);
//        
//        assertTrue(stykliste.size() == 1);
//    }
}
