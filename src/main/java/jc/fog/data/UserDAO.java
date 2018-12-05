/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import jc.fog.exceptions.FogException;

/**
 *
 * @author Jespe
 */
public class UserDAO extends AbstractDAO
{
    final static String CREATE_USER = "INSERT INTO Users (name, zip, phone, email, password, rank) VALUES (?,?,?,?,?,?)";
       
    
    public UserDAO(Connection connection) throws FogException {
        super(connection);
    } 
    
    
    /**
     * createUser skal være med til, at opret 'user' i db.
     * @param name
     * @param zip
     * @param phone
     * @param email
     * @param password
     * @param rank
     * @return
     * @throws FogException 
     */
    public boolean createUser(String name, int zip, int phone, String email, String password, int rank) throws FogException
    {
        name = name.trim();
        try {
            
            PreparedStatement pstm;
            
            pstm = connection.prepareStatement(CREATE_USER);
            pstm.setString(1, name);
            pstm.setInt(2, zip);
            pstm.setInt(3, phone);
            pstm.setString(4, email);
            pstm.setString(5, password);
            pstm.setInt(6, rank);
            
            return pstm.executeUpdate() == 1;
            
        }
        catch(Exception e)
        {
            throw new FogException("Bruger kunne ikke blive oprettet." + e.getMessage());
        }
    }
}
