/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic.dto;

import jc.fog.logic.dto.MaterialtypeDTO;
import jc.fog.logic.dto.MaterialDTO;
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
public class MaterialDtoUTest
{
    
    public MaterialDtoUTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    @Test
    public void materialComparison() 
    {
        // Arrange
        MaterialDTO first = new MaterialDTO(1, 1, "first", 100, "stk", "first material", 0, (float) 2.0);
        MaterialDTO second = new MaterialDTO(2, 2, "second", 2, "pcs", "second type", 1, (float) 0.5);
        
        // Act
        int result = first.compareTo(second);
        int expected = -1;
        
        // Assert
        assertEquals(result, expected);        
    }
    @Test
    public void materialComparisonWithNull()
    {
        // Arrange
        MaterialDTO first = new MaterialDTO(1, 1, "first", 100, "stk", "first material", 0, (float) 2.0);
        MaterialDTO second = new MaterialDTO(2, 2, "second", 2, "pcs", "second type", 1, (float) 0.5);
        second.setMaterialtypeDTO(null);
        
        // Act
        int result = first.compareTo(second);
        int expected = 1;
        
        // Assert
        assertEquals(result, expected);
    }
    
    @Test
    public void materialComparisonWithNegativeTypeId()
    {
        // Arrange
        MaterialDTO first = new MaterialDTO(1, 1, "first", 100, "stk", "first material", 0, (float) 2.0);
        MaterialDTO second = new MaterialDTO(2, 2, "second", 2, "pcs", "second type", 1, (float) 0.5);
        second.setMaterialtypeDTO(new MaterialtypeDTO(-1, "invalid type"));
        
        // Act
        int result = first.compareTo(second);
        int expected = 1;
        
        // Assert
        assertEquals(result, expected);
    }
}
