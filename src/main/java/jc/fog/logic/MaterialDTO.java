/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Claus
 */
public class MaterialDTO implements Comparable<MaterialDTO>
{
    private int id;    
    private int materialtypeId;
    private String name;
    private int length;
    private String unit;
    private MaterialtypeDTO materialtypeDTO;
    //private float pris; // decimal (6,2) i db.
    //private List<DimensionerDTO> dimensioner;
    
    // getters
    public int getId() { return id; }
    //public int getMaterialetypeId() { return materialetypeId; }
    public String getName() { return name; }
    public int getLength() { return length; }
    public String getUnit() { return unit; }
    public MaterialtypeDTO getMaterialtypeDTO(){return materialtypeDTO;}
    
    //public List<DimensionerDTO> getDimensioner() { return dimensioner; }
    
    // setters
    public void setId(int value) { id = value; }
    //public void setMaterialetypeId(int value) { materialetypeId = value; }
    public void setName(String value) { name = value; }
    public void setLength(int value) { length = value;}
    public void setUnit(String value) { unit = value;}
    public void setMaterialtypeDTO(MaterialtypeDTO value){materialtypeDTO = value;}
    
    /**
     * Konstruktør hvor alle argumenter kendes.
     * @param id 
     * @param materialtypeId 
     * @param name 
     * @param length
     * @param unit
     * @param materialtype
     */
    public MaterialDTO(int id, int materialtypeId, String name, int length, String unit, String materialtype)
    {
        this.id = id;
        this.materialtypeId = materialtypeId;
        this.name = name;
        this.length = length;
        this.unit = unit;
        this.materialtypeDTO = new MaterialtypeDTO(materialtypeId, materialtype);
    }
    
    
    
//    /**
//     * Tilføj en dimension til varens samling af dimensioner.
//     * @param dimensionId
//     * @param length 
//     */
//    public void addDimension(int dimensionId, int length)
//    {
//        if (dimensioner == null)
//            dimensioner = new ArrayList<DimensionerDTO>();
//        
//        dimensioner.add(new DimensionerDTO(dimensionId, length));
//    }

    @Override
    public int compareTo(MaterialDTO o)
    {
        return Integer.compare(this.getLength(), o.getLength());
        
    }
    
    
}
