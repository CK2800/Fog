/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Statement;
import java.util.Random;
import java.util.UUID;
import javafx.util.Pair;
import jc.fog.exceptions.FogException;
import jc.fog.logic.dto.UsersDTO;

/**
 *
 * @author Jespe
 */
public class UserDAO extends AbstractDAO
{
    final static String CREATE_USER_SQL = "INSERT INTO Users (name, zip, phone, email, password) VALUES (?,?,?,?,?)";
    
    //skal måske ha lavet navnet om på den her.
    final static String GET_USER_SQL = "SELECT id, name, zip, phone, email, rank FROM Users WHERE email=? AND password=?";
        
    final static String GET_ALL_USERS_SQL = "SELECT * FROM Users";

    final static String UPDATE_USER_PASSWORD_SQL = "UPDATE Users SET password = ? WHERE email = ?";
    
    final static String ADD_NEW_RANK_SQL ="UPDATE Users SET rank = ? WHERE id = ?";
    
    final static String DELETE_USER_SQL ="DELETE FROM Users WHERE id = ?";
           
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
        
        Pair<Integer, Object> pair1 = new Pair<>(1, name);
        Pair<Integer, Object> pair2 = new Pair<>(2, zipcode);
        Pair<Integer, Object> pair3 = new Pair<>(3, phone);
        Pair<Integer, Object> pair4 = new Pair<>(4, email);
        Pair<Integer, Object> pair5 = new Pair<>(5, password);
        try
        (
            PreparedStatement pstm = createPreparedStatement(connection, CREATE_USER_SQL, Statement.RETURN_GENERATED_KEYS, pair1, pair2, pair3, pair4, pair5);
            ResultSet rs = updateAndGetKeys(pstm);
        )
        {
            if (rs.next())
                result = rs.getInt(1);
        }
        
        
        
//        try(PreparedStatement pstm = connection.prepareStatement(CREATE_USER_SQL, Statement.RETURN_GENERATED_KEYS))
//        {
//            pstm.setString(1, name);
//            pstm.setInt(2, zipcode);
//            pstm.setInt(3, phone);
//            pstm.setString(4, email);
//            pstm.setString(5, password);
//            // udfør opdatering.
//            pstm.executeUpdate();
//            // Hent id for netop oprettet bruger.
//            try (ResultSet rs = pstm.getGeneratedKeys())
//            {
//                if (rs.next())
//                    result = rs.getInt(1);
//            }
//        }
        catch(Exception e)
        {
            if (result == 0)
                throw new FogException("Bruger kunne ikke blive oprettet.", e.getMessage(), e);
            else // her er bruger oprettet.
            {
                // log fejlen.
            }
        }
                
        // Returner nyoprettet id eller 0.
        return result;
    }
    
    /**
     * Logger en bruger ind med angivet email og password.
     * Bruges til, at find ud af om man har opretter sig på siden
     * Hvis man findes i db så vil man få adgang til siden.
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
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int zip = rs.getInt("zip");
                    int phone = rs.getInt("phone");
                    String emailValue = rs.getString("email");
                    int rank = rs.getInt("rank"); 

                    //id, name, zip, phone, email, rank
                    user = new UsersDTO(id, phone, rank, zip, name, emailValue);
                    user.setId(id);
                    return user;
                }
            }
        }
        catch(Exception e)
        {   
            // Fandt vi ingen user, kaster vi exception videre.            
            throw new FogException("Kunne ikke logge ind.. ", e.getMessage(), e);            
        }        
        return user;
    }
    
    /**
     * Den her gør det muligt at slet bruger.
     * @param userid
     * @return
     * @throws FogException 
     */
    public boolean deleteUser(int userid) throws FogException
    {
        boolean success = false;
    
        try {
            PreparedStatement pstm;
            
            pstm = connection.prepareStatement(DELETE_USER_SQL);
            pstm.setInt(1, userid);
            
            success = pstm.executeUpdate() == 1;
        }
        catch(Exception e)
        {
            if (!success)
                throw new FogException("Kunne ikke slette bruger.", e.getMessage(), e);
        }
        return success;
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
            throw new FogException("Rank blev ikke opdateret.", e.getMessage(), e);
        }
    }
    
    /**
     * Skal tildele bruger med denne email ny adgangskode.
     * @param email
     * @return
     * @throws FogException 
     */
    public boolean forgotPassword(String email, String password) throws FogException
    {
        boolean success = false;
        try
        {
            //Henter vores random som sender tal + bogstaver tilbage som bruges til password.
            if(password == null)
                password = randomPassword();
            
            PreparedStatement pstm;
            
            pstm = connection.prepareStatement(UPDATE_USER_PASSWORD_SQL);

            pstm.setString(1, password);
            pstm.setString(2, email);
            
            success = pstm.executeUpdate() == 1;
        }
        catch(Exception e)
        {            
            throw new FogException("Kodeord blev ikke nulstillet.", e.getMessage(), e);            
        }
        return success;
    }
    
    /**
     * Henter alle brugere i systemet.
     * @return List af UsersDTO objekter. Hvis ingen brugere findes, returneres en tom liste.
     * @throws FogException 
     */
    public List<UsersDTO> getAllUsers() throws FogException
    {
        List<UsersDTO> users = new ArrayList<UsersDTO>();
        try
        (   
            PreparedStatement pstm = createPreparedStatement(connection, GET_ALL_USERS_SQL, Statement.NO_GENERATED_KEYS, null);
            ResultSet rs = pstm.executeQuery();
        )
        {
            while(rs.next())
                {
                    users.add(new UsersDTO(rs.getInt("id"),rs.getInt("phone"),rs.getInt("rank"),rs.getInt("zip"),rs.getString("name"),rs.getString("email")));
                }
        }
//        try {
//            PreparedStatement pstm = connection.prepareStatement(GET_ALL_USERS_SQL);
//            try(ResultSet rs = pstm.executeQuery())
//            {
//                while(rs.next())
//                {
//                    users.add(new UsersDTO(rs.getInt("id"), rs.getInt("rank"), rs.getString("name"), rs.getString("email")));
//                }
//            }                      
//        }
        catch(Exception e)
        {
            throw new FogException("Brugere blev ikke fundet.", e.getMessage(), e);
        }
        return users;

    }
    /**
     * Bruges til, at kun giv bruger et "user" ny adgangskode.
     * @return 
     */
    public String randomPassword()
    {
        Random rand = new Random();
        int max = rand.nextInt(10) + 5; //Hvis nextInt bliver "0" så vil den altid tilføj 5.
        
        String uniqueText = UUID.randomUUID().toString(); // 0f8fad5bd9cb-469f-a165-70867728950e
        return uniqueText.replace("-", "").substring(0, max);
    }
}
