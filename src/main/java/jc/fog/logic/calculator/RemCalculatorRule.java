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
import jc.fog.logic.StyklisteItem;

/**
 *
 * @author Claus
 */
public class RemCalculatorRule implements CalculatorRule
{    
    @Override
    public int calculate(ForesporgselDTO forespoergsel, List<MaterialeDTO> materialer, List<StyklisteItem> stykliste) throws FogException
    {
        try
        {
            // Remmen bærer taget, udhæng 30 cm i hver ende.
            int remLength = forespoergsel.getLaengde() - 60;

            // Remmen laves af spærtræ som har varetypeid 4
            Stream<MaterialeDTO> spaerTrae = materialer.stream().filter(m -> m.getMaterialetypeDTO().getId() == 4);        
            // Konverter tilbage til liste.
            List<MaterialeDTO> spaerTraeList = spaerTrae.collect(Collectors.toList());
            // Sorter på laengde, faldende.
            Collections.sort(spaerTraeList, Comparator.comparing(MaterialeDTO::getLaengde));
            Collections.reverse(spaerTraeList);
            // Hent længste træ.
            MaterialeDTO materiale = spaerTraeList.get(0);
            // Hvor mange stk. træ skal der min. bruges?
            int antal = (int)Math.ceil((float)remLength/materiale.getLaengde());            
            // Find korteste spærtræ med krævet længde.            
            for(MaterialeDTO m : spaerTraeList)            
            {
                if (m.getLaengde() * antal >= remLength)                    
                    materiale = m;
                else 
                    break;                    
            }
            
            stykliste.add(new StyklisteItem(materiale, antal, "spær instruks"));
            // 1 nyt item på styklisten.
            return 1;            
        }        
        catch(Exception e)
        {
            throw new FogException("Beregning af spærtræ fejlede.", e.getMessage());
        }
    }
}
