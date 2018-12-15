/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import jc.fog.data.dao.MaterialDAO;
import java.sql.Connection;
import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.dto.MaterialDTO;
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
public class MaterialDAOIntegrationTest
{
    static Connection connection = null;
    public MaterialDAOIntegrationTest()
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
    public void testCreateMaterial() throws FogException
    {
        MaterialDAO dao = new MaterialDAO(connection);
        boolean succes = dao.createMaterial(2, "5x5 mm", 6, "stk", 78.95F);
        assertTrue(succes);
    }
    
    
    @Test
    public void testGetSingleMaterial() throws FogException
    {
        MaterialDAO dao = new MaterialDAO(connection);
        MaterialDTO request = dao.getMaterial(1);
        assertTrue(request != null);
    }
    
    @Test
    public void testGetMaterials() throws FogException
    {           
        MaterialDAO dao = new MaterialDAO(connection);
        List<MaterialDTO> materials = dao.getMaterials();        
        assertTrue(materials.size() > 0);
    }
    
    @Test(expected = FogException.class)
    public void testGetMaterialsFails() throws Exception
    {
        MaterialDAO dao = new MaterialDAO(connection);
        DbConnector.closeConnection();
        List<MaterialDTO> materials = dao.getMaterials();
        
        assertTrue(materials.size() > 0);
    }
}
