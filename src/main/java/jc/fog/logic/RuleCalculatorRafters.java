/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.MaterialeDTO;
import jc.fog.logic.BillItem;

/**
 * Udvidelse af RuleCalculator for udregning af spær.
 * @author Claus
 */
public class RuleCalculatorRafters extends RuleCalculator
{

    @Override
    protected int calculate(CarportRequestDTO forespoergsel, List<MaterialeDTO> materialer, List<BillItem> stykliste) throws FogException
    {
        // er taget med rejsning?
        if (forespoergsel.getSlope() > 0) 
        {
            // udhæng er 30 cm i hver ende.
            int tagLaengde = forespoergsel.getLength() - 2 * BusinessRules.OVERHANG; // skal udhæng medregnes her? 
            // Spær placeres højst 0,89 m fra hinanden.
            int antalSpaer = (int)Math.ceil(tagLaengde / BusinessRules.RAFTER_SPACING_SLOPED_ROOF);
            MaterialeDTO materiale = null;
            for(MaterialeDTO m : materialer)
            {
                if (m.getMaterialetypeDTO().getId() == BusinessRules.PRE_FAB_RAFTERS_TYPE_ID) // byg selv spær
                {
                    materiale = m;
                    break;
                }
            }
            if (materiale != null)
            {
                stykliste.add(new BillItem(materiale, antalSpaer, "byg selv spær"));
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
            int spaerBredde = forespoergsel.getWidth();
            int antalBraedder = (int)Math.ceil((float)spaerBredde/materiale.getLaengde());            
            // Find korteste spærtræ med krævet længde.            
            materiale = findShortest(spaerTraeList, antalBraedder, spaerBredde);
                        
            
            int tagLaengde = forespoergsel.getLength();
            // Spær placeres højst 0,55 cm fra hinanden.
            int antalSpaer = (int)Math.ceil(tagLaengde / BusinessRules.RAFTER_SPACING);                        
            
            stykliste.add(new BillItem(materiale, antalSpaer*antalBraedder, "spær fladt tag"));
            
            // Et item tilføjet stykliste.
            return 1;            
        }
    }
    
}
