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
    final static String CREATE_USER = "INSERT INTO Users (name, zip, phone, email, password) VALUES (?,?,?,?,?)";
       
    
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
        try {
            
            PreparedStatement pstm;
            
            pstm = connection.prepareStatement(CREATE_USER);
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
}
