/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.util.List;
import static jc.fog.data.ForespoergselDAOUnitTest.connection;
import jc.fog.exceptions.FogException;
import jc.fog.logic.VareDTO;
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
public class VareDAOJUnitTest
{
    
    public VareDAOJUnitTest()
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
    public void hentVarerTilBeregneren() throws FogException
    {
        List<VareDTO> varer = VareDAO.VarerTilBeregning();
        System.out.println("Antal varer fudnet: " + varer.size());
        assertTrue(varer.size() > 0);
    }
}
