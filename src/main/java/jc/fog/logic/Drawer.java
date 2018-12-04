/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.ArrayList;
import java.util.List;
import jc.fog.exceptions.FogException;

/**
 *
 * @author Claus
 */
public class Drawer
{
    private List<RulesDrawer> drawers = new ArrayList();
    private List<Rectangle> rectangles;    
    
    public Drawer(List<MaterialDTO> materials) throws FogException
    {        
        // // Initialiserer RulesCalculator's hashmap.
        RulesCalculator.initializeMaterials(materials);
        
        // Initialiser RuleDrawers
        initializeRuleDrawers();
    }
    private void initializeRuleDrawers() throws FogException
    {
        
        // Hent udregning fra RulesCalculatorHead og giv til RulesDrawerHead.        
        drawers.add(new RulesCalculatorHead()); 
        drawers.add(new RulesCalculatorRafters());
        drawers.add(new RulesCalculatorPost());
    }
    
    public List<Rectangle> drawCarport(CarportRequestDTO carportRequest) throws FogException
    {
        rectangles = new ArrayList();
        
        // Gennemløb drawers og tegn.
        for(RulesDrawer drawer : drawers)
            rectangles.addAll(drawer.draw(carportRequest));
        
        return rectangles;
    }
    
}
