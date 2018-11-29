/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.logic.Rules.CarportPart;

/**
 * RulesDrawer til tegning af svg-elementer baseret på div. business rules.
 * @author Claus
 */
public abstract class RulesDrawer
{
    protected CarportPart carportPart;
    protected MaterialDTO material;
    protected int length;
    protected int count;
    public RulesDrawer(CarportPart carportPart, MaterialDTO material, int length, int count)
    {
        this.carportPart = carportPart;
        this.material = material;
        this.length = length;
        this.count = count;
    }
    /**
     * Metode som danner en liste af Rectangle objekter baseret på materialets tykkelse, længde og antal.     
     * @return 
     */
    protected abstract List<Rectangle> draw();
}
