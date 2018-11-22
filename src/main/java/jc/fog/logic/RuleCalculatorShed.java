/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;
import jc.fog.logic.MaterialeDTO;
import jc.fog.logic.ShedDTO;
import jc.fog.logic.BillItem;

/**
 * Udvidelse af RuleCalculator for udregning af skurets beklædning.
 * @author Claus
 */
public class RuleCalculatorShed extends RuleCalculator
{

    @Override
    protected int calculate(ForesporgselDTO forespoergsel, List<MaterialeDTO> materialer, List<BillItem> stykliste) throws FogException
    {        
        ShedDTO skur = forespoergsel.getSkurDTO();
        if (skur != null)
        {
            // Find materiale til skuret => trykimp. brædder, 19x100mm i 210 cm (id 9)                        
            MaterialeDTO materiale = materialer.get(8);
                        
            // Find dimensioner for skur.
            int laengde = skur.getLaengde();
            int bredde = skur.getBredde();                        
            
            // antal brædder i skurets længderetning, inderste brædder placeres med 6 cm mellemrum.
            int laengdeAntal = (int)Math.ceil(laengde / (BusinessRules.PLANK_SPACING + BusinessRules.PLANK_WIDTH)) * 2;
            // antal brædder i skurets bredde med overlap på 1 cm i hver side.
            int breddeAntal = (int)Math.ceil(bredde / (BusinessRules.PLANK_SPACING + BusinessRules.PLANK_WIDTH)) * 2;
            
            stykliste.add(new BillItem(materiale, laengdeAntal + breddeAntal, "skur brædder 1 på 2"));
            return 1; // 1 nyt item på styklisten.
        }
        else 
            return 0; // Ingen styklisteitem tilføjet.
    }
    
}
