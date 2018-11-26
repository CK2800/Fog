/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.util.List;
import static jc.fog.data.ForespoergselDAOUnitTest.connection;
import jc.fog.exceptions.FogException;
import jc.fog.logic.MaterialDTO;
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
public class MaterialeDAOJUnitTest
{
    
    public MaterialeDAOJUnitTest()
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

//    @Test
//    public void hentVarerTilBeregneren() throws FogException
//    {
//        List<MaterialeDTO> varer = VareDAO.getMaterialer();
//        System.out.println("Antal varer fudnet: " + varer.size());
//        assertTrue(varer.size() > 0);
//    }
    
    @Test
    public void testCreateMateriale() throws FogException
    {
        boolean succes = DataFacade.createMaterial(2, "5x5 mm", 6, "stk");
        assertTrue(succes);
    }
    
    @Test
    public void testGetSingleMateriale() throws FogException
    {
        MaterialDTO request = DataFacade.getMaterial(1);
        assertTrue(request != null);
    }
    
    @Test
    public void testGetAllMateriale() throws FogException
    {
        // Her hentes først alle materialer og så oprettes en ny - hvad er det?
        List<MaterialDTO> varer = MaterialDAO.getMaterials();
        System.out.println("Antal varer fudnet: " + varer.size());
        assertTrue(varer.size() > 0);

    }
}
