/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

/**
 * Pure fabrication som kan fortælle hvor mange MaterialDTO objekter der skal bruges
 * for at dække en given længde ifm. udregninger af styklister.
 * @author Claus
 */
public class MaterialCount
{
    private int count;
    private MaterialDTO material;
    
    public int getCount(){return count;}
    public MaterialDTO getMaterialDTO(){return material;}
    
    public MaterialCount(int count, MaterialDTO material)
    {        
        this.count = count;
        this.material = material;
    }
}
