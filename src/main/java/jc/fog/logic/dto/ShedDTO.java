/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic.dto;

/**
 *
 * @author Claus
 */
public class ShedDTO
{
    private int id;
    private int carportRequestId;
    private int length;
    private int width;
    
    
    // getters
    public int getId(){ return id; }
    public int carportRequestId(){return carportRequestId;}
    public int getLength() { return length; }
    public int getWidth() { return width; }
    
    /**
     * Konstruktør for skur.
     * @param id
     * @param carportRequestId
     * @param length
     * @param width 
     */
    public ShedDTO(int id, int carportRequestId, int length, int width)
    {
        this.id = id;
        this.carportRequestId = carportRequestId;
        this.length = length;
        this.width = width;
    }
    
    
}
