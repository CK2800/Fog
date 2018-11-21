/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic.calculator;

import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;
import jc.fog.logic.MaterialeDTO;
import jc.fog.logic.SkurDTO;
import jc.fog.logic.StyklisteItem;

/**
 *
 * @author Claus
 */
public class StolpeCalculatorRule extends CalculatorRule
{

    @Override
    public int calculate(ForesporgselDTO forespoergsel, List<MaterialeDTO> materialer, List<StyklisteItem> stykliste) throws FogException
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
        
        stykliste.add(new StyklisteItem(stolpe, count, "stolpetekst"));
        
        // 1 nyt item på styklisten.
        return 1;        
    }
    
}
