/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

/**
 *
 * @author Claus
 */
public class ShedDTO
{
    private int id;
    private int length;
    private int width;
    
    // getters
    public int getId(){ return id; }
    public int getLength() { return length; }
    public int getWidth() { return width; }
    
    // setters
    public void setId(int value) { id = value; }
    public void setLength(int value) { length = value; }
    public void setWidth(int value) { width = value; }
    
    public ShedDTO(int id, int length, int width)
    {
        this.id = id;
        this.length = length;
        this.width = width;
    }
}
