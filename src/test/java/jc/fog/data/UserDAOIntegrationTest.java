/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.sql.Connection;
import jc.fog.exceptions.FogException;
import jc.fog.exceptions.RecordNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Jespe
 */
public class UserDAOIntegrationTest {
    
    static Connection connection = null;    
    public UserDAOIntegrationTest()
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
    public void tearDown() {
    }

    @Test
    public void testCreateUser() throws FogException
    {      
        UserDAO userdao = new UserDAO(connection);
        int userId = userdao.createUser("123456@123456.dk", "Test user", "12345", 11, 3450);
        boolean deleteUser = userdao.deleteUser(userId);
        
        assertTrue(deleteUser);
    } 
    
    @Test(expected = FogException.class)    
    public void testGetUserName() throws FogException
    {
        // Assert
        UserDAO userdao = new UserDAO(connection);
        
        // Act
        String username = userdao.returnUserName(500);
        
        // Assert
        assertEquals(username, "testuser");
                
    }
}
