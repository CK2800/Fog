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
public class DimensionDTO
{
    private int id;
    /**
     * Længde i cm.
     */
    private int length;
    private int width;
    private int height;
    
    /**
     * Konstruktør som kræver argumenter for alle attributter.
     * @param id
     * @param length 
     * @param width 
     * @param height 
     */
    public DimensionDTO(int id, int length, int width, int height)
    {
        this.id = id;
        this.length = length;
        this.width = width;
        this.height = height;
    }
    
    // getters
    public int getId() { return id; }
    public int getLength() { return length; }
    public int getWidth() { return width;}
    public int getHeight(){return height;}
    
    // setters
    public void setId(int value) { id = value; }
    public void setLength(int value) { length = value; }
    public void setWidth(int value){width =value;}
    public void setHeight(int value){height = value;}
    
    /**
     * Mapper en tuple fra databasen til DimensionDTO.
     * @param rs ResultSet med tupel.
     * @return DimensionDTO
     * @throws SQLException 
     */
    public static DimensionDTO mapDimension(ResultSet rs) throws SQLException
    {
        return new DimensionDTO(rs.getInt("id"), rs.getInt("length"), rs.getInt("width"), rs.getInt("height"));
    }
    
}
