/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jc.fog.exceptions.FogException;
import jc.fog.logic.UsersDTO;

/**
 *
 * @author Jespe
 */
public class UserDAO extends AbstractDAO
{
    final static String CREATE_USER_SQL = "INSERT INTO Users (name, zip, phone, email, password) VALUES (?,?,?,?,?)";
    
    //skal måske ha lavet navnet om på den her.
    final static String GET_USER_SQL = "SELECT id, rank FROM Users WHERE email=? AND password=?";
    
    final static String UPDATE_PASSWORD = "UPDATE Users SET password = ? WHERE email = ?";
       
    
    public UserDAO(Connection connection) throws FogException {
        super(connection);
    } 
    
    /**
     * Opret bruger i user table.
     * @param name
     * @param email
     * @param password
     * @param zip
     * @param phone
     * @return
     * @throws FogException 
     */
    public boolean createUser(String email, String name, String password, int phone, int zipcode) throws FogException
    {
        name = name.trim();
        password = password.trim();
        try {
            
            PreparedStatement pstm;
            
            pstm = connection.prepareStatement(CREATE_USER_SQL);
            pstm.setString(1, name);
            pstm.setInt(2, zipcode);
            pstm.setInt(3, phone);
            pstm.setString(4, email);
            pstm.setString(5, password);
            
            return pstm.executeUpdate() == 1;
        }
        catch(Exception e)
        {
            throw new FogException("Bruger kunne ikke blive oprettet." + e.getMessage());
        }
    }
    
    /**
     * 
     * @param email
     * @param password
     * @return
     * @throws FogException 
     */
    public UsersDTO login(String email, String password) throws FogException
    {
        password = password.trim();
        
        try
        {
            PreparedStatement pstm;
            
            pstm = connection.prepareStatement(GET_USER_SQL);
            pstm.setString(1, email);
            pstm.setString(2, password);
            
            ResultSet rs = pstm.executeQuery();
            
            if(rs.next())
            {
                //Får fat i både Id og rank
                int userId = rs.getInt("id");
                int rank = rs.getInt("rank");                
                
                UsersDTO user = new UsersDTO(userId, rank);
                user.setId(userId);
                return user;
            }
            else
            {
                //Hvad skal der ske hvis den ikke findes?
                throw new FogException("Den findes desværre ikke");
            }
            
        }
        catch(Exception e)
        {
            throw new FogException("Kun ikke log ind.. " + e.getMessage());
        }
    }
    
    public boolean forgetUser(String email) throws FogException
    {
        try {
            PreparedStatement pstm;
            
            pstm = connection.prepareStatement(UPDATE_PASSWORD);
            pstm.setString(1, "123456");
            pstm.setString(2, email);
            
            return pstm.executeUpdate() == 1;
        }
        catch(Exception e)
        {
            throw new FogException("Den kun ikke finde denne email." + e.getMessage());
        }
    }
}
