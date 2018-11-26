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
    private MaterialDTO material;
    private int count;
    private String remarks;
    
    public MaterialDTO getMaterialDTO(){return material;}
    public int getCount(){return count;}
    public String getRemarks(){return remarks;}
    
    public BillItem(MaterialDTO material, int count, String remarks)
    {
        this.material = material;
        this.count = count;
        this.remarks = remarks;
    }
    
    /**
     * Adds value to count.
     * @param value 
     */
    public void add(int value){this.count += value;}
    
}
