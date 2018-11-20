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
public class SpaerCalculatorRule implements CalculatorRule
{

    @Override
    public int calculate(ForesporgselDTO forespoergsel, List<MaterialeDTO> materialer, List<StyklisteItem> stykliste) throws FogException
    {
        // er taget med rejsning?
        if (forespoergsel.getHaeldning() > 0) 
        {
            // udhæng er 30 cm i hver ende.
            int tagLaengde = forespoergsel.getLaengde() - 60;
            // Spær placeres højst 0,89 cm fra hinanden.
            int antalSpaer = (int)Math.ceil(tagLaengde / 89F);
            MaterialeDTO materiale = null;
            for(MaterialeDTO m : materialer)
            {
                if (m.getMaterialetypeDTO().getId() == 24) // byg selv spær
                {
                    materiale = m;
                    break;
                }
            }
            if (materiale != null)
            {
                stykliste.add(new StyklisteItem(materiale, antalSpaer, "byg selv spær"));
                // 1 nyt item i stykliste.
                return 1;
            }
            else
                throw new FogException("Byg selv spær kunne ikke findes for denne carport.", "Ingen materialer med materialetype 24 (byg selv spær)");
        }
        else // fladt tag.
        {
            // spær laves af spærtræ som har varetypeid 4
            Stream<MaterialeDTO> spaerTrae = materialer.stream().filter(m -> m.getMaterialetypeDTO().getId() == 4);
            // Konverter tilbage til liste.
            List<MaterialeDTO> spaerTraeList = spaerTrae.collect(Collectors.toList());
            // Sorter på laengde, faldende.
            Collections.sort(spaerTraeList, Comparator.comparing(MaterialeDTO::getLaengde));
            Collections.reverse(spaerTraeList);
            // Hent længste træ.
            MaterialeDTO materiale = spaerTraeList.get(0);
            // Hvor mange stk. træ skal der min. bruges?
            int spaerBredde = forespoergsel.getBredde();
            int antalBraedder = (int)Math.ceil((float)spaerBredde/materiale.getLaengde());            
            // Find korteste spærtræ med krævet længde.            
            for(MaterialeDTO m : spaerTraeList)            
            {
                if (m.getLaengde() * antalBraedder >= spaerBredde)                    
                    materiale = m;
                else 
                    break;                    
            }            
            
            // spær skal dække fuld taglængde minus 10 cm.
            int tagLaengde = forespoergsel.getLaengde() - 10;
            // Spær placeres højst 0,55 cm fra hinanden.
            int antalSpaer = (int)Math.ceil(tagLaengde / 55F);                        
            
            stykliste.add(new StyklisteItem(materiale, antalSpaer*antalBraedder, "spær fladt tag"));
            
            // Et item tilføjet stykliste.
            return 1;            
        }
    }
    
}
