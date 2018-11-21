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
import jc.fog.logic.SkurDTO;
import jc.fog.logic.BillItem;

/**
 * Udvidelse af CalculatorRule for udregning af stolper.
 * @author Claus
 */
public class PostCalculatorRule extends CalculatorRule
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
        SkurDTO skur = forespoergsel.getSkurDTO();
        if (skur != null)
        {            
            // Er skuret i fuld bredde?
            if (skur.getBredde() == forespoergsel.getBredde())
                count += 3;
            else
                count += 4;                
        }
        
        stykliste.add(new BillItem(stolpe, count, "stolpetekst"));
        
        // 1 nyt item på styklisten.
        return 1;        
    }
    
}
