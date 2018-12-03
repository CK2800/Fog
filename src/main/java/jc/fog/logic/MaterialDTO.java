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
    private int length;    // todo, implementeres vha. dimensionDTO ?
    private String unit;
    private MaterialtypeDTO materialtypeDTO;
    private int rooftypeId;
    /** Enhedspris */       
    private float price; // decimal (6,2) i db.
    //private List<DimensionerDTO> dimensioner;
    
    // getters
    public int getId() { return id; }
    //public int getMaterialetypeId() { return materialetypeId; }
    public String getName() { return name; }
    public int getLength() { return length; } // todo, implementeres vha. dimensionDTO ?
    public int getWidth(){return 0;} // todo, implementeres vha. dimensionDTO ?
    public int getHeight(){return 0;} // todo, implementeres vha. dimensionDTO ?
    public String getUnit() { return unit; }
    public MaterialtypeDTO getMaterialtypeDTO(){return materialtypeDTO;}
    public int getRooftypeId(){return rooftypeId;}
    /** Henter enhedspris for materialet */
    public float getPrice(){return price;}
    
    
    //public List<DimensionerDTO> getDimensioner() { return dimensioner; }
    
    // setters
    public void setId(int value) { id = value; }
    //public void setMaterialetypeId(int value) { materialetypeId = value; }
    public void setName(String value) { name = value; }
    public void setLength(int value) { length = value;}
    public void setUnit(String value) { unit = value;}
    public void setMaterialtypeDTO(MaterialtypeDTO value){materialtypeDTO = value;}
    public void setRooftypeId(int value){rooftypeId = value;}
    /** Sæt enhedspris */
    public void setPrice(float value){price = value;}
    /**
     * Konstruktør hvor alle argumenter kendes.
     * @param id
     * @param materialtypeId
     * @param name
     * @param length
     * @param unit
     * @param materialtype
     * @param rooftypeId Angiver hvilken tagtype, dette materiale indgår i.
     * @param price Materialets enhedspris.
     * 
     */
    public MaterialDTO(int id, int materialtypeId, String name, int length, String unit, String materialtype, int rooftypeId, float price)
    {
        this.id = id;
        this.materialtypeId = materialtypeId;
        this.name = name;
        this.length = length;
        this.unit = unit;
        this.materialtypeDTO = new MaterialtypeDTO(materialtypeId, materialtype);
        this.rooftypeId = rooftypeId;
        this.price = price;
    }

    /**
     * Sammenligning sker på materialetypens id.
     * Hvis materialtypeDTO er null på et eller begge objekter der sammenlignes,
     * sættes dets/deres materialetypeid til 0 og sammenligning udføres.
     * @param o
     * @return 
     */
    @Override    
    public int compareTo(MaterialDTO o)
    {
        int materialetype1 = this.getMaterialtypeDTO() == null ? 0 : this.getMaterialtypeDTO().getId();
        int materialetype2 = o.getMaterialtypeDTO() == null ? 0 : o.getMaterialtypeDTO().getId();
                   
        return Integer.compare(materialetype1, materialetype2);
        
        
    }
    
    
}
