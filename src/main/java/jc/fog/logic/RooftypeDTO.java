/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;

/**
 * Repræsenterer de forskellige tag typer, f.eks. plast tag, rødt tegl tag mv.
 * @author Claus
 */
public class RooftypeDTO
{
    private int id;
    private String type;    
    
    /**
     * Liste af MaterialDTO objekter som indgår i tagtypen, f.eks. røde rygsten, røde tagsten.
     * @return 
     */
    List<MaterialDTO> materials;
    
    /**
     * Tagtypens id.
     * @return 
     */
    public int getId(){return id;}
    /**
     * Tagtypens type, f.eks. plast tag, rødt tegltag osv.
     * @return 
     */    
    public String getType(){return type;}
    /**
     * Liste af MaterialDTO objekter som indgår i tagtypen, f.eks. røde rygsten, røde tagsten.
     * @return 
     */
    public List<MaterialDTO> getMaterials(){return materials;}
        
    /**
     * Initialiserer et RooftypeDTO objekt.
     * @param id
     * @param type
     * @param materials 
     */
    public RooftypeDTO(int id, String type, List<MaterialDTO> materials)
    {
        this.id = id;
        this.type = type;                
        this.materials = materials;
    }
    
}
