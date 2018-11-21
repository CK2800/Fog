/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;

/**
 * A pure fabrication of a collection of data comprising a line in the bill of goods.
 * @author Claus
 */
public class BillItem
{
    private MaterialeDTO materiale;
    private int count;
    private String hjaelpetekst;
    
    public MaterialeDTO getMaterialeDTO(){return materiale;}
    public int getCount(){return count;}
    public String getHjaelpetekst(){return hjaelpetekst;}
    
    public BillItem(MaterialeDTO materiale, int count, String hjaelpetekst)
    {
        this.materiale = materiale;
        this.count = count;
        this.hjaelpetekst = hjaelpetekst;
    }
    
    /**
     * Adds value to count.
     * @param value 
     */
    public void add(int value){this.count += value;}
    
}
