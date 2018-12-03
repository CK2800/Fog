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
 * Udvidelse af RulesCalculator for udregning af spær.
 * @author Claus
 */
public class RulesCalculatorRafters extends RulesCalculator
{
    @Override
    protected List<BillItem> calculate(CarportRequestDTO carportRequest) throws FogException
    {
        try
        {
            List<MaterialDTO> rafters;
            List<BillItem> result = new ArrayList();
            // er taget med rejsning?
            if (carportRequest.getSlope() > 0) 
            {
                rafters = materials.get(Materialtype.PRE_FAB_RAFTERS.name());
                // udhæng er 30 cm i hver ende.
                int roofLength = carportRequest.getLength() - 2 * Rules.OVERHANG;
                // Spær placeres højst 0,89 m fra hinanden.
                int noRafters = (int)Math.ceil(roofLength / Rules.RAFTER_SPACING_SLOPED_ROOF);
                MaterialDTO material = rafters.get(0);

                if (material != null)
                {                
                    result.add(new BillItem(material, noRafters, CarportPart.PRE_FAB_RAFTERS, "byg selv spær"));                
                }
                else
                    throw new FogException("Byg selv spær kunne ikke findes for denne carport.", "Ingen materialer med materialetype 24 (byg selv spær)");
            }
            else // fladt tag.
            {
                // Find materialer i hashmap.            
                rafters = materials.get(Materialtype.RAFTERS.name());
                // Hent antal korteste træ nødvendigt.
                MaterialCount materialCount = findShortest(rafters, carportRequest.getWidth());            
                // Find tagets længde og udregn antal spær, adskilt 55 cm fra hinanden.
                int roofLength = carportRequest.getLength();            
                int noRafters = (int)Math.ceil(roofLength / Rules.RAFTER_SPACING);                                    
                //bill.add(new BillItem(material, noRafters*noRequired, "spær fladt tag"));
                result.add(new BillItem(materialCount.getMaterialDTO(), noRafters*materialCount.getCount(), CarportPart.RAFTERS, "spær fladt tag"));                        
            }
            return result;
        }
        catch(Exception e)
        {
            throw new FogException("Spær kunne ikke beregnes.", e.getMessage());
        }
    }    
}
