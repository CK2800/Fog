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
 * Udvidelse af RulesCalculator for udregning af stolper.
 * @author Claus
 */
public class RulesCalculatorPost extends RulesCalculator
{    
    @Override
    protected List<BillItem> calculate(CarportRequestDTO carportRequest) throws FogException
    {
        try
        {
            ArrayList<BillItem> result = new ArrayList();
            // Find materialer.       
            List<MaterialDTO> posts = materials.get(Materialtype.POST.name());             
            List<MaterialDTO> rafters = materials.get(Materialtype.RAFTERS.name()); // spær
            MaterialCount mcRafters = findShortest(rafters, carportRequest.getWidth()); // Antal træ til 1 spær.
            MaterialCount mcHeads = findShortest(rafters, carportRequest.getLength()); // Antal træ til 1 rem.
            MaterialDTO post = posts.get(0);
            int noHeads = 2; // 2 remme som minimum.
            int noPostsPerHead = 2; // 2 stolper pr. rem som minimum.
            int count = 0; // antal stolper.
            
            // Tjek antal brud på rem (count > 1) og læg til stolpeantal pr. rem.
            noPostsPerHead += (mcHeads.getCount()-1);
            
            // Kun v. fladt tag skal vi tjekke for brud på spær.
            if (carportRequest.getSlope()==0)
            {
                // Læg ekstra rem til for hvert brud på spær (count > 1).
                noHeads += (mcRafters.getCount()-1);
            }
            
            // antal stolper er antal remme * stolper/rem.
            count = noHeads * noPostsPerHead;            
            
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
            // Returner liste med bill items.
            result.add(new BillItem(post, count, CarportPart.POST, "stolpetekst"));
            return result;                         
        }
        catch(Exception e)
        {
            throw new FogException("Stolper kunne ikke beregnes.", e.getMessage());
        }
    }    
}
