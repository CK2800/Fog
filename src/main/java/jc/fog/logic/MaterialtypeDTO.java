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
public class MaterialtypeDTO
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
    public MaterialtypeDTO(int id, String type)
    {
        this.id = id;
        this.type = type;
    }
    
    /**
     * Mapper værdier fra ResultSet tuple til MaterialtypeDTO.
     * @param rs ResultSet med tuple.
     * @return MaterialtypeDTO
     * @throws SQLException 
     */
    public static MaterialtypeDTO mapMaterialtype(ResultSet rs) throws SQLException
    {
        return new MaterialtypeDTO(rs.getInt("id"), rs.getString("type"));
    }
    
}
