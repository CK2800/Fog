/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic.calculators;

import jc.fog.logic.RulesCalculator;
import jc.fog.logic.Rules;
import java.util.ArrayList;
import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.BillItem;
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.MaterialDTO;

/**
 *
 * @author Claus
 */
public class RulesCalculatorRoof extends RulesCalculator        
{    
    @Override
    protected List<BillItem> calculate(CarportRequestDTO carportRequestDTO) throws FogException
    {        
        
        // Hent tagryg- og tagfladematerialer.
        List<MaterialDTO> ridges = materials.get(Materialtype.RIDGE.name());
        List<MaterialDTO> sheetings = materials.get(Materialtype.SHEETING.name());
        List<BillItem> result = new ArrayList();
        
        // Fladt tag, her skal vi blot bruge materialer fra sheetings.
        if (carportRequestDTO.getSlope() == 0)
        {   
            try
            {
                // Sorter på længden faldende.
                sortLengthDesc(sheetings);

                MaterialDTO longest = null, shortest = null;
                // Find længste materiale for denne tagtype.
                for(MaterialDTO m : sheetings)
                {
                    if( m.getRooftypeId() == carportRequestDTO.getRooftypeId() &&
                        m.getLength() <= carportRequestDTO.getLength() )
                    {
                        longest = m;
                        break;
                    }
                }
                // Find korteste materiale for denne tagtype.
                for(int i = sheetings.size()-1; i >= 0; i--)
                {
                    MaterialDTO m = sheetings.get(i);
                    if ( m.getRooftypeId() == carportRequestDTO.getRooftypeId() &&
                         m.getLength() >= carportRequestDTO.getLength() - longest.getLength())
                    {
                        shortest = m;
                        break;
                    }
                }
                // Find antal.
                int count = (int)Math.ceil(carportRequestDTO.getWidth() / 109F); // ecolite plader er 109 cm.
                result.add(new BillItem(longest, count, CarportPart.SHEETING,  "lang tagflade"));
                result.add(new BillItem(shortest, count, CarportPart.SHEETING, "kort tagflade"));
                return result;
                
            }
            catch(Exception e)
            {
                throw new FogException("Tagbelægning, fladt tag, kan ikke beregnes.", e.getMessage(), e);
            } 
        }
        else // taget har hældning.
        {
            // Find tagfladens vidde:
            double slopedWidth = calculateSlopedWidth(carportRequestDTO.getWidth()/2, carportRequestDTO.getSlope());
            // Find antal teglsten, husk at teglstens længde skal bruges i tagets bredde.
            int noRows = (int)Math.ceil(slopedWidth / Rules.ROOFTILE_LENGTH);
            int noCols = (int)Math.ceil(carportRequestDTO.getLength() / Rules.ROOFTILE_WIDTH);
            // Find antal rygningsten:
            int noRidge = (int)Math.ceil(carportRequestDTO.getLength() / Rules.RIDGETILE_LENGTH);
            
            // Opret bill items for hver sten.
            result.add(new BillItem(sheetings.get(0), noRows*noCols, CarportPart.SHEETING, "teglsten."));
            result.add(new BillItem(ridges.get(0), noRidge, CarportPart.RIDGE, "rygning sten."));
            return result;            
        }        
    }
    
}
