/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.MaterialDTO;
import jc.fog.logic.ShedDTO;
import jc.fog.logic.BillItem;

/**
 * Udvidelse af RuleCalculator for udregning af stolper.
 * @author Claus
 */
public class RuleCalculatorPost extends RuleCalculator
{

    @Override
    protected int calculate(CarportRequestDTO carportRequest, List<MaterialDTO> materials, List<BillItem> bill) throws FogException
    {
        // Find materialet.       
        List<MaterialDTO> posts = filter(materials, BusinessRules.POST_TYPE_ID);        
        MaterialDTO post = posts.get(0);
        // en stolpe i hvert hjørne.
        int count = 4;
        // Skal der bygges skur, kræves flere stolper.
        ShedDTO shed = carportRequest.getShedDTO();
        if (shed != null)
        {            
            // Er skuret i fuld bredde?
            if (shed.getWidth() == carportRequest.getWidth())
                count += BusinessRules.POSTS_SHED_FULL_WIDTH;
            else
                count += BusinessRules.POSTS_SHED;                
        }
        
        bill.add(new BillItem(post, count, "stolpetekst"));
        
        // 1 nyt item på styklisten.
        return 1;        
    }
    
}
