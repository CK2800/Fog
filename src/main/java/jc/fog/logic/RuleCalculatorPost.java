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
 * Udvidelse af RuleCalculator for udregning af stolper.
 * @author Claus
 */
public class RuleCalculatorPost extends RuleCalculator
{

    @Override
    protected int calculate(ForesporgselDTO forespoergsel, List<MaterialeDTO> materialer, List<BillItem> stykliste) throws FogException
    {
        // Find materialet.       
        List<MaterialeDTO> stolper = filter(materialer, 5);        
        MaterialeDTO stolpe = stolper.get(0);
        // en stolpe i hvert hjørne.
        int count = 4;
        // Skal der bygges skur, kræves flere stolper.
        ShedDTO skur = forespoergsel.getSkurDTO();
        if (skur != null)
        {            
            // Er skuret i fuld bredde?
            if (skur.getBredde() == forespoergsel.getBredde())
                count += BusinessRules.POSTS_SHED_FULL_WIDTH;
            else
                count += BusinessRules.POSTS_SHED;                
        }
        
        stykliste.add(new BillItem(stolpe, count, "stolpetekst"));
        
        // 1 nyt item på styklisten.
        return 1;        
    }
    
}
