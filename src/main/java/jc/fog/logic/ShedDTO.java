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
    private int laengde;
    private int bredde;
    
    // getters
    public int getId(){ return id; }
    public int getLaengde() { return laengde; }
    public int getBredde() { return bredde; }
    
    // setters
    public void setId(int value) { id = value; }
    public void setLaengde(int value) { laengde = value; }
    public void setBredde(int value) { bredde = value; }
    
    public ShedDTO(int id, int laengde, int bredde)
    {
        this.id = id;
        this.laengde = laengde;
        this.bredde = bredde;
    }
}
