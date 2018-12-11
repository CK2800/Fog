/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.sql.Connection;

/**
 *
 * @author Claus
 */
public class AbstractDAO
{    
    protected static Connection connection = null;
    
    public AbstractDAO(Connection connection) 
    {
        this.connection = connection;
    }   
}
