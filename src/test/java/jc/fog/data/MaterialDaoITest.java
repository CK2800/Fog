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
public class MaterialDaoITest
{
    static Connection connection = null;
    public MaterialDaoITest()
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
    public void createMaterial() throws FogException
    {
        // Arrange.
        MaterialDAO dao = new MaterialDAO(connection);
        
        // Act.
        boolean succes = dao.createMaterial(2, "5x5 mm", 6, "stk", 78.95F);
        
        // Assert.
        assertTrue(succes);
    }
    
    
    @Test
    public void testGetSingleMaterial() throws FogException
    {
        // Arrange.
        MaterialDAO dao = new MaterialDAO(connection);
        
        // Act.
        MaterialDTO request = dao.getMaterial(1); // Kræver dog at materialet findes. Bedre hvis vi kan få id tilbage fra createMaterial(...).
        
        // Assert.
        assertTrue(request != null);
    }
    
    @Test
    public void testGetMaterials() throws FogException
    {           
        // Arrange.
        MaterialDAO dao = new MaterialDAO(connection);
        dao.createMaterial(2, "5x5 mm", 6, "stk", 78.95F);
        
        // Act.
        List<MaterialDTO> materials = dao.getMaterials();        
        
        // Assert.
        assertTrue(materials.size() > 0);
    }
    
    @Test(expected = FogException.class)
    public void testGetMaterialsFails() throws Exception
    {
        // Arrange.
        MaterialDAO dao = new MaterialDAO(connection);        
        DbConnector.closeConnection();
        
        // Act.
        List<MaterialDTO> materials = dao.getMaterials();
        
        // Assert.
        assertTrue(materials.size() > 0);
    }
}
