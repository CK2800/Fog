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
public class MaterialeDTO implements Comparable<MaterialeDTO>
{
    private int id;    
    private int materialetypeId;
    private String navn;
    private int laengde;
    private String enhed;
    private MaterialetypeDTO materialetypeDTO;
    //private float pris; // decimal (6,2) i db.
    //private List<DimensionerDTO> dimensioner;
    
    // getters
    public int getId() { return id; }
    //public int getMaterialetypeId() { return materialetypeId; }
    public String getNavn() { return navn; }
    public int getLaengde() { return laengde; }
    public String getEnhed() { return enhed; }
    public MaterialetypeDTO getMaterialetypeDTO(){return materialetypeDTO;}
    
    //public List<DimensionerDTO> getDimensioner() { return dimensioner; }
    
    // setters
    public void setId(int value) { id = value; }
    //public void setMaterialetypeId(int value) { materialetypeId = value; }
    public void setNavn(String value) { navn = value; }
    public void setLaengde(int value) { laengde = value;}
    public void setEnhed(String value) { enhed = value;}
    public void setMaterialetypeDTO(MaterialetypeDTO value){materialetypeDTO = value;}
    
    /**
     * Konstruktør hvor alle argumenter kendes.
     * @param id 
     * @param materialetypeId 
     * @param navn 
     * @param laengde
     * @param enhed
     * @param materialetype
     */
    public MaterialeDTO(int id, int materialetypeId, String navn, int laengde, String enhed, String materialetype)
    {
        this.id = id;
        this.materialetypeId = materialetypeId;
        this.navn = navn;
        this.laengde = laengde;
        this.enhed = enhed;
        this.materialetypeDTO = new MaterialetypeDTO(materialetypeId, materialetype);
    }
    
    
    
//    /**
//     * Tilføj en dimension til varens samling af dimensioner.
//     * @param dimensionId
//     * @param laengde 
//     */
//    public void addDimension(int dimensionId, int laengde)
//    {
//        if (dimensioner == null)
//            dimensioner = new ArrayList<DimensionerDTO>();
//        
//        dimensioner.add(new DimensionerDTO(dimensionId, laengde));
//    }

    @Override
    public int compareTo(MaterialeDTO o)
    {
        return Integer.compare(this.getLaengde(), o.getLaengde());
        
    }
    
    
}
