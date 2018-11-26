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
 * @author Claus
 */
public class AbstractDAO
{    
    protected static Connection connection;
    
    public AbstractDAO(Connection connection) throws FogException
    {
        this.connection = connection;
    }   
}
