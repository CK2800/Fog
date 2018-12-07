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
public class MaterialtypeDTO
{
    private int id;
    private String type;
    
    // getters
    public int getId() { return id; }
    public String getType() { return type; }
    
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
