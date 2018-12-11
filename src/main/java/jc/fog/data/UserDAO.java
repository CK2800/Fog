/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
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
    
    final static String UPDATE_PASSWORD_SQL = "UPDATE Users SET password = ? WHERE email = ?";
    
    final static String GET_ALL_USERS_SQL = "SELECT * FROM Users";

    final static String FORGOT_PASSWORD_SQL = "UPDATE Users SET password = ? WHERE email = ?";
    
    final static String ADD_NEW_RANK_SQL ="UPDATE Users SET rank = ? WHERE id = ?";
    
    final static String DELETE_USER_SQL ="DELETE FROM Users WHERE id = ?";
    
    final static String UPDATE_USER_PASSWORD_SQL = "UPDATE Users SET password = ? WHERE id = ?";
    
    final static String GET_USER_NAME_SQL = "SELECT name FROM Users WHERE id = ?";

       
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
    public int createUser(String email, String name, String password, int phone, int zipcode) throws FogException
    {
        name = name.trim();
        password = password.trim();
        try {
            
            PreparedStatement pstm;
            
            pstm = connection.prepareStatement(CREATE_USER_SQL, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, name);
            pstm.setInt(2, zipcode);
            pstm.setInt(3, phone);
            pstm.setString(4, email);
            pstm.setString(5, password);
            
            // udfør opdatering.
            pstm.executeUpdate();
            
            // Hent id.
            ResultSet rs = pstm.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);
            else
                return 0;
            
        }
        catch(Exception e)
        {
            throw new FogException("Bruger kunne ikke blive oprettet." + e.getMessage());
        }
    }
    
    /**
     * Bruges til, at find ud af om man har opretter sig på siden
     * Hvis man findes i db så vil man få adgang til siden.
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
    
    /**
     * Den her gør det muligt at slet bruger.
     * @param userid
     * @return
     * @throws FogException 
     */
    public boolean deleteUser(int userid) throws FogException
    {
        try {
            PreparedStatement pstm;
            
            pstm = connection.prepareStatement(DELETE_USER_SQL);
            pstm.setInt(1, userid);
            
            return pstm.executeUpdate() == 1;
        }
        catch(Exception e)
        {
            throw new FogException("Kunne ikke slette bruger.. " + e.getMessage());
        }
    }

    /**
     * Skal tildele bruger med denne email ny adgangskode.
     * @param email
     * @return
     * @throws FogException 
     */
    public boolean forgotPassword(String email) throws FogException
    {
        try
        {
            //Henter vores random som sender tal + bogstaver tilbage som bruges til password.
            String password = Rules.RandomPassword();
            
            PreparedStatement pstm;
            
            pstm = connection.prepareStatement(FORGOT_PASSWORD_SQL);
            pstm.setString(1, password);
            pstm.setString(2, email);
            
            return pstm.executeUpdate() == 1;
        }
        catch(Exception e)
        {
            throw new FogException("Den kun ikke finde denne email. " + e.getMessage());
        }
    }
    
    /**
     * Tildeler den ny rank til brugeren.
     * @param id
     * @param rank
     * @return
     * @throws FogException 
     */
    public boolean setNewRankUser(int id, int rank) throws FogException
    {
        try {
            PreparedStatement pstm;
            
            pstm = connection.prepareStatement(ADD_NEW_RANK_SQL);
            pstm.setInt(1, rank);
            pstm.setInt(2, id);
            
            return pstm.executeUpdate() == 1;
        }
        catch(Exception e)
        {
            throw new FogException("Der sket en fejl ved opdater ranken. " + e.getMessage());
        }
    }
    
    /**
     * Den her skal opdater den enkelt brugers adgangskode til det som man har skrevet.
     * @param password
     * @param id
     * @return
     * @throws FogException 
     */
    public boolean updateUserPassword(String password, int id) throws FogException
    {
        try
        {
            PreparedStatement pstm;
            
            pstm = connection.prepareStatement(UPDATE_USER_PASSWORD_SQL);
            pstm.setString(1, password);
            pstm.setInt(2, id);
            
            return pstm.executeUpdate() == 1;
        }
        catch (Exception e)
        {
            throw new FogException("Der gik noget galt da man skulle opdater adgangskode. " + e.getMessage());
        }
    }
    
    /**
     * Den skal sende navn tilbage som kan blive vist frem til bruger
     * @param id
     * @return
     * @throws FogException 
     */
    public String returnUserName(int id) throws FogException
    {
        try 
        {
            PreparedStatement pstm;
            
            pstm = connection.prepareStatement(GET_USER_NAME_SQL);
            pstm.setInt(1, id);
            
            ResultSet rs = pstm.executeQuery();
            
            if(rs.next())
            {
                //Får fat i både Id og rank
                return rs.getString("name");
            }
            else
            {
                //Hvad skal der ske hvis den ikke findes?
                throw new FogException("Den findes desværre ikke");
            }
        }
        catch (Exception e)
        {
            throw new FogException("Der er sket en fejl ved fremvis af navn.. " + e.getMessage());
        }
    }
    
    /**
     * Hente alle bruger frem i en liste.
     * @return
     * @throws FogException 
     */
    public List<UsersDTO> getAllUsers() throws FogException
    {
        try {
            List<UsersDTO> user = new ArrayList<UsersDTO>();
            PreparedStatement pstm = connection.prepareStatement(GET_ALL_USERS_SQL);
            try(ResultSet rs = pstm.executeQuery())
            {
                while(rs.next())
                {
                    user.add(new UsersDTO(rs.getInt("id"), rs.getInt("rank"), rs.getString("name"), rs.getString("email")));
                }
            }          
            return user;
        }
        catch(Exception e)
        {
            throw new FogException("Systemet havde problemet med at find users - " + e.getMessage());
        }
    }
}
