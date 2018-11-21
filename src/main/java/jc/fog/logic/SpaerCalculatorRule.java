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
import jc.fog.logic.StyklisteItem;

/**
 *
 * @author Claus
 */
public class SpaerCalculatorRule extends CalculatorRule
{

    @Override
    protected int calculate(ForesporgselDTO forespoergsel, List<MaterialeDTO> materialer, List<StyklisteItem> stykliste) throws FogException
    {
        // er taget med rejsning?
        if (forespoergsel.getHaeldning() > 0) 
        {
            // udhæng er 30 cm i hver ende.
            int tagLaengde = forespoergsel.getLaengde() - 60; // skal udhæng medregnes her? 
            // Spær placeres højst 0,89 m fra hinanden.
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
            List<MaterialeDTO> spaerTraeList = filter(materialer, 4);
            sortLengthDesc(spaerTraeList);
            
            // Hent længste træ.
            MaterialeDTO materiale = spaerTraeList.get(0);
            // Hvor mange stk. træ skal der min. bruges?
            int spaerBredde = forespoergsel.getBredde();
            int antalBraedder = (int)Math.ceil((float)spaerBredde/materiale.getLaengde());            
            // Find korteste spærtræ med krævet længde.            
            materiale = findShortest(spaerTraeList, antalBraedder, spaerBredde);
                        
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
