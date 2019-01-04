/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import jc.fog.logic.dto.CarportRequestDTO;
import jc.fog.logic.dto.MaterialDTO;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import jc.fog.data.DbConnector;
import jc.fog.data.dao.MaterialDAO;
import jc.fog.exceptions.FogException;
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
public class CalculatorITest
{
    private static Connection connection;
    
    public CalculatorITest()
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
}
