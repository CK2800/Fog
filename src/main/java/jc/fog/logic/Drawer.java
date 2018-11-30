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
    private CarportRequestDTO carportRequest;
    
    public Drawer(List<MaterialDTO> materials, CarportRequestDTO carportRequest) throws FogException
    {
        this.carportRequest = carportRequest;
        // // Initialiserer RulesCalculator's hashmap.
        RulesCalculator.initializeMaterials(materials);
        
        // Initialiser RuleDrawers
        initializeRuleDrawers();
    }
    private void initializeRuleDrawers() throws FogException
    {
        // Hent udregning fra RulesCalculatorHead og giv til RulesDrawerHead.
        List<BillItem> bill = new RulesCalculatorHead().calculate(carportRequest);
        drawers.add(new RulesDrawerHead(bill.get(0)));
    }
    
    public List<Rectangle> draw()
    {
        rectangles = new ArrayList();
        
        // Gennemløb drawers og tegn.
        for(RulesDrawer drawer : drawers)
            rectangles.addAll(drawer.draw(carportRequest));
        
        return rectangles;
    }
    
}
