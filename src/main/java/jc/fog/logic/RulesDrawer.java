/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;

/**
 * RulesDrawer til tegning af svg-elementer baseret på div. business rules.
 * @author Claus
 */
public abstract class RulesDrawer
{        
    protected BillItem billItem;
    public RulesDrawer(BillItem billItem)
    {        
        this.billItem = billItem;
    }
    /**
     * Metode som danner en liste af Rectangle objekter baseret på materialets tykkelse, længde og antal.     
     * @return 
     */
    protected abstract List<Rectangle> draw(CarportRequestDTO carportRequest);
}
