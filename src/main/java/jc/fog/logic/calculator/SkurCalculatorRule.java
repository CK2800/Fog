/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic.calculator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;
import jc.fog.logic.MaterialeDTO;
import jc.fog.logic.SkurDTO;
import jc.fog.logic.StyklisteItem;

/**
 *
 * @author Claus
 */
public class SkurCalculatorRule extends CalculatorRule
{

    @Override
    public int calculate(ForesporgselDTO forespoergsel, List<MaterialeDTO> materialer, List<StyklisteItem> stykliste) throws FogException
    {        
        SkurDTO skur = forespoergsel.getSkurDTO();
        if (skur != null)
        {
            // Find materiale til skuret => trykimp. brædder, 19x100mm i 210 cm (id 9)                        
            MaterialeDTO materiale = materialer.get(8);
                        
            // Find dimensioner for skur.
            int laengde = skur.getLaengde();
            int bredde = skur.getBredde();                        
            
            // antal brædder i skurets længderetning, inderste brædder placeres med 6 cm mellemrum.
            int laengdeAntal = (int)Math.ceil(laengde / 16.0) * 2;
            // antal brædder i skurets bredde med overlap på 1 cm i hver side.
            int breddeAntal = (int)Math.ceil(bredde / 16.0) * 2;
            
            stykliste.add(new StyklisteItem(materiale, laengdeAntal + breddeAntal, "skur brædder 1 på 2"));
            return 1; // 1 nyt item på styklisten.
        }
        else 
            return 0; // Ingen styklisteitem tilføjet.
    }
    
}
