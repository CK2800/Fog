/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.sql.Connection;
import static jc.fog.data.RooftypeDAOIntegrationTest.connection;
import jc.fog.exceptions.FogException;
import org.junit.After;
import org.junit.AfterClass;
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
    public static void setUpClass() {
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
        boolean success = userdao.createUser("Jesper", 3450, 11, "test@test.dk", "12345", 5);
        assertTrue(success);
    }
    
}
