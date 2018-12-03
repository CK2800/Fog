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
 * Udvidelse af RulesCalculator for udregning af lægter v. tag med hældning.
 * @author Claus
 */
public class RulesCalculatorBattens extends RulesCalculator
{        
    @Override
    protected List<BillItem> calculate(CarportRequestDTO carportRequest) throws FogException
    {
        try
        {
            ArrayList<BillItem> result = new ArrayList();
            if (carportRequest.getSlope() > 0)
            {
                // Find materialer
                List<MaterialDTO> battens = materials.get(Materialtype.BATTENS.name()); // filter(materials, Rules.BATTENS_TYPE_ID);
                // Find korteste lægte og antal.
                MaterialCount materialCount = findShortest(battens, carportRequest.getLength());
                // Find skrånende tagflades bredde, afrund op:
                int slopeWidth = (int)Math.ceil(calculateSlopedWidth(carportRequest.getWidth()/2, carportRequest.getSlope()));                        
                // Business rule: ca. 30 cm ml. lægter.
                int noBattensRows = (int)Math.floor(slopeWidth / Rules.BATTENS_SPACING);
                // Returner liste med bill items.            
                result.add(new BillItem(materialCount.getMaterialDTO(), materialCount.getCount() * noBattensRows * 2, CarportPart.BATTENS, "lægte tekst"));                       
            }
            return result;
        }
        catch(Exception e)
        {
            throw new FogException("Lægter kunne ikke beregnes.", e.getMessage());
        }
    }    
}
