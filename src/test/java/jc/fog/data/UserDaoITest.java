/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import jc.fog.data.dao.UserDAO;
import java.sql.Connection;
import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.dto.UsersDTO;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Jespe
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoITest {
    
    static Connection connection = null;    
    public UserDaoITest()
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
    public void createUser() throws FogException
    {      
        UserDAO userdao = new UserDAO(connection);
        int userId = userdao.createUser("0123456@123456.dk", "Test user", "12345", 11, 3450);
        boolean deleteUser = userdao.deleteUser(userId);
        
        assertTrue(userId > 0);
    } 
        
    @Test
    public void getAllUsers() throws FogException
    {
        // Arrange
        UserDAO userDAO = new UserDAO(connection);        
        int id = userDAO.createUser("0123456@123456.dk", "Test user", "12345", 11, 3450); //  lav test user
        // Act
        List<UsersDTO> users = userDAO.getAllUsers();
        userDAO.deleteUser(id); // oprydning af test user.
        
        // Assert
        assertTrue(!users.isEmpty());
    }
}