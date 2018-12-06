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
}
