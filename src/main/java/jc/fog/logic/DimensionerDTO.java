/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Claus
 */
public class DimensionerDTO
{
    private int id;
    /**
     * Længde i cm.
     */
    private int laengde;
    
    /**
     * Konstruktør som kræver argumenter for alle attributter.
     * @param id
     * @param laengde 
     */
    public DimensionerDTO(int id, int laengde)
    {
        this.id = id;
        this.laengde = laengde;
    }
    
    // getters
    public int getId() { return id; }
    public int getLaengde() { return laengde; }
    
    // setters
    public void setId(int value) { id = value; }
    public void setLaengde(int value) { laengde = value; }
    
    /**
     * Mapper en tuple fra databasen til DimensionerDTO.
     * @param rs ResultSet med tupel.
     * @return DimensionerDTO
     * @throws SQLException 
     */
    public static DimensionerDTO mapDimensioner(ResultSet rs) throws SQLException
    {
        return new DimensionerDTO(rs.getInt("id"), rs.getInt("laengde"));
    }
    
}
