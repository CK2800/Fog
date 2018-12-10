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
import jc.fog.exceptions.FogException;
import jc.fog.logic.ZipcodeDTO;

/**
 *
 * @author Jespe
 */
public class ZipcodeDAO extends AbstractDAO {
    
    static final String GET_ZIPCODES_SQL = "SELECT zip, city FROM Zipcodes";    
    
    public ZipcodeDAO(Connection connection) throws FogException {
        super(connection);
    }
    
    
    
    public List<ZipcodeDTO> getZipcodes() throws FogException
    {
        
        List<ZipcodeDTO> getZipcodes = new ArrayList();
        
        try
        (
            PreparedStatement pstm = connection.prepareStatement(GET_ZIPCODES_SQL);
            ResultSet rs = pstm.executeQuery();
        )
        {
            while(rs.next())
            {
                getZipcodes.add(new ZipcodeDTO(rs.getInt("zip"), rs.getString("city"))); 
            }
        }                      
        
        catch(Exception e)
        {
            throw new FogException("Kunne ikke fremvise Postnr: " + e.getMessage());
        }
        
        return getZipcodes;
    }
    
}
