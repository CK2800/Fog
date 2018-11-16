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
public class VaretypeDTO
{
    private int id;
    private String type;
    
    // getters
    public int getId() { return id; }
    public String getType() { return type; }
    
    // setters.
    public void setId(int value) { id = value; }
    public void setType(String value) { type = value; }
    
    /**
     * Konstruktør som kræver argumenter for alle attributter.
     * @param id
     * @param type 
     */
    public VaretypeDTO(int id, String type)
    {
        this.id = id;
        this.type = type;
    }
    
    /**
     * Mapper værdier fra ResultSet tuple til VareDTO.
     * @param rs ResultSet med tuple.
     * @return VaretypeDTO
     * @throws SQLException 
     */
    public static VaretypeDTO mapVaretype(ResultSet rs) throws SQLException
    {
        return new VaretypeDTO(rs.getInt("id"), rs.getString("type"));
    }
    
}
