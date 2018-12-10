/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import jc.fog.exceptions.FogException;
import jc.fog.logic.Rules;
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
    
    final static String FORGOT_PASSWORD_SQL = "UPDATE Users SET password = ? WHERE email = ?";
       
    
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
     * @return Nyoprettet brugers id returneres, eller 0 hvis oprettelsen fejler.
     * @throws FogException 
     */
    public int createUser(String email, String name, String password, int phone, int zipcode) throws FogException
    {
        // trim whitespace før og efter.
        name = name.trim();
        password = password.trim();
        // placeholders.
        int result = 0; // 0 indikerer at ingen ny bruger blev oprettet.
        
        try(PreparedStatement pstm = connection.prepareStatement(CREATE_USER_SQL, Statement.RETURN_GENERATED_KEYS))
        {
            pstm.setString(1, name);
            pstm.setInt(2, zipcode);
            pstm.setInt(3, phone);
            pstm.setString(4, email);
            pstm.setString(5, password);
            // udfør opdatering.
            pstm.executeUpdate();
            // Hent id for netop oprettet bruger.
            try (ResultSet rs = pstm.getGeneratedKeys())
            {
                if (rs.next())
                    result = rs.getInt(1);
            }
        }
        catch(Exception e)
        {
            if (result == 0)
                throw new FogException("Bruger kunne ikke blive oprettet." + e.getMessage());
            else
            {
                // log fejlen.
            }
        }
                
        // Returner nyoprettet id eller 0.
        return result;
    }
    
    /**
     * Logger en bruger ind med angivet email og password.
     * @param email
     * @param password
     * @return UsersDTO objekt som repræsenterer den indloggede bruger, eller null ved fejlet login.
     * @throws FogException 
     */
    public UsersDTO login(String email, String password) throws FogException
    {
        password = password.trim();
        email = email.trim();
        
        UsersDTO user = null;
        
        try(PreparedStatement pstm = connection.prepareStatement(GET_USER_SQL))
        {            
            pstm.setString(1, email);
            pstm.setString(2, password);
            
            try (ResultSet rs = pstm.executeQuery())
            {
                if(rs.next())
                {
                    //Får fat i både Id og rank
                    int userId = rs.getInt("id");
                    int rank = rs.getInt("rank");                                
                    
                    user = new UsersDTO(userId, rank);
                }
            }
        }
        catch(Exception e)
        {   
            // Fandt vi ingen user, kaster vi exception videre.
            if (user == null) 
                throw new FogException("Kunne ikke logge ind.. " + e.getMessage());
            else
            {
                // log hvad fejlen var...
            }
        }        
        return user;
    }
    
    public boolean forgotPassword(String email) throws FogException
    {
        boolean success = false;
        try (PreparedStatement pstm = connection.prepareStatement(FORGOT_PASSWORD_SQL);)
        {
            String password = Rules.randomPassword();            
            pstm.setString(1, password);
            pstm.setString(2, email);
            
            success = pstm.executeUpdate() == 1;
        }
        catch(Exception e)
        {
            throw new FogException("..." + e.getMessage());
        }
        return success;
    }
}
