/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.sql.Connection;
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
    
    
}
