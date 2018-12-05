/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;
import java.util.List;
import jc.fog.exceptions.FogException;

/**
 * RulesDrawer til tegning af svg-elementer baseret på div. business rules.
 * @author Claus
 */
public interface RulesDrawer
{        
    /**
     * Metode som danner en liste af Rectangle objekter baseret på materialets tykkelse, længde og antal.     
     * @param carportRequest
     * @return 
     */
    public List<Rectangle> draw(CarportRequestDTO carportRequest) throws FogException;
}
