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
 * Udvidelse af RulesCalculator for udregning af rem.
 * @author Claus
 */
public class RulesCalculatorHead extends RulesCalculator
{      
    @Override
    protected List<BillItem> calculate(CarportRequestDTO carportRequest) throws FogException
    {        
        try
        {
            // mindst 2 remme.
            int noHeads = 2; 
            ArrayList<BillItem> result = new ArrayList();
            // Remmen laves af spærtræ, find samlingen i hashmap.
            List<MaterialDTO> heads = materials.get(Materialtype.RAFTERS.name());
            // Remmen bærer taget, som har udhæng 30 cm i hver ende.
            int headLength = carportRequest.getLength() - 2 * Rules.OVERHANG;                        
            // Find korteste materiale og antal.
            MaterialCount materialCount = findShortest(heads, headLength);                                    
            // Find antallet af remme krævet, ved at se hvor mange gange, spær skæres over.
            MaterialCount mcRafters = findShortest(heads, carportRequest.getWidth());
            // Skal der bruges mere end 1 stk træ til et spær, skal overskæringen understøttes med 1 rem.
            noHeads += (mcRafters.getCount()-1);
            
            // Returner liste med bill items.
            
            result.add(new BillItem(materialCount.getMaterialDTO(), materialCount.getCount() * noHeads, CarportPart.HEAD, "rem instruks"));
            return result;                
        }        
        catch(Exception e)
        {
            throw new FogException("Beregning af spærtræ fejlede.", e.getMessage());
        }
        
    }
}
