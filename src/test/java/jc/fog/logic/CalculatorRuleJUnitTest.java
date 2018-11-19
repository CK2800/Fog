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
import jc.fog.logic.calculator.RemCalculatorRule;
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
            connection.close();
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
    public void CalculateRem() throws FogException
    {
        List<MaterialeDTO> materialer = MaterialeDAO.materialerTilBeregning();
        ForesporgselDTO forespoergsel = ForesporgselDAO.getForesporgselSingle(1);
        List<StyklisteItem> stykliste = new ArrayList<StyklisteItem>();
        
        RemCalculatorRule calc = new RemCalculatorRule();
        stykliste = calc.calculate(forespoergsel, materialer, stykliste);
        
        assertTrue(stykliste != null);
    }
}
