/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;

/**
 * Udvidelse af RulesCalculator for udregning af stolper.
 * @author Claus
 */
public class RulesCalculatorPost extends RulesCalculator
{    
    @Override
    protected int calculate(CarportRequestDTO carportRequest, List<BillItem> bill) throws FogException
    {
        // Find materialet.       
        List<MaterialDTO> posts = materials.get(Materialtype.POST.name()); //filter(materials, Rules.POST_TYPE_ID);        
        MaterialDTO post = posts.get(0);
        // en stolpe i hvert hjørne.
        int count = 4;
        // Skal der bygges skur, kræves flere stolper.
        ShedDTO shed = carportRequest.getShedDTO();
        if (shed != null)
        {            
            // Er skuret i fuld bredde?
            if (shed.getWidth() == carportRequest.getWidth())
                count += Rules.POSTS_SHED_FULL_WIDTH;
            else
                count += Rules.POSTS_SHED;                
        }
        
        bill.add(new BillItem(post, count, CarportPart.POST, "stolpetekst"));
        
        // 1 nyt item på styklisten.
        return 1;        
    }
    
}
