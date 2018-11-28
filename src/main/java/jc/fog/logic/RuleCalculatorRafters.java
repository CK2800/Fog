/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;

/**
 * Udvidelse af RuleCalculator for udregning af spær.
 * @author Claus
 */
public class RuleCalculatorRafters extends RuleCalculator
{
    @Override
    protected int calculate(CarportRequestDTO carportRequest, List<BillItem> bill) throws FogException
    {
        List<MaterialDTO> rafters;
        // er taget med rejsning?
        if (carportRequest.getSlope() > 0) 
        {
            rafters = materials.get(MaterialtypeId.PRE_FAB_RAFTERS.name());
            // udhæng er 30 cm i hver ende.
            int roofLength = carportRequest.getLength() - 2 * BusinessRules.OVERHANG; // skal udhæng medregnes her? 
            // Spær placeres højst 0,89 m fra hinanden.
            int noRafters = (int)Math.ceil(roofLength / BusinessRules.RAFTER_SPACING_SLOPED_ROOF);
            MaterialDTO material = rafters.get(0);
//            for(MaterialDTO m : rafters)
//            {
//                if (m.getMaterialtypeDTO().getId() == BusinessRules.PRE_FAB_RAFTERS_TYPE_ID) // byg selv spær
//                {
//                    material = m;
//                    break;
//                }
//            }
            if (material != null)
            {
                bill.add(new BillItem(material, noRafters, "byg selv spær"));
                // 1 nyt item i stykliste.
                return 1;
            }
            else
                throw new FogException("Byg selv spær kunne ikke findes for denne carport.", "Ingen materialer med materialetype 24 (byg selv spær)");
        }
        else // fladt tag.
        {
            // spær laves af spærtræ som har varetypeid 4
            //rafters = filter(materials, 4);
            rafters = materials.get(MaterialtypeId.HEAD.name());
            sortLengthDesc(rafters);
            
            // Hent længste træ.
            MaterialDTO material = rafters.get(0);
            // Hvor mange stk. træ skal der min. bruges?
            int raftersWidth = carportRequest.getWidth();
            int noRequired = (int)Math.ceil((float)raftersWidth/material.getLength());            
            // Find korteste spærtræ med krævet længde.            
            material = findShortest(rafters, noRequired, raftersWidth);                        
            
            int roofLength = carportRequest.getLength();
            // Spær placeres højst 0,55 cm fra hinanden.
            int noRafters = (int)Math.ceil(roofLength / BusinessRules.RAFTER_SPACING);                        
            
            bill.add(new BillItem(material, noRafters*noRequired, "spær fladt tag"));
            
            // Et item tilføjet stykliste.
            return 1;            
        }
    }
    
}
