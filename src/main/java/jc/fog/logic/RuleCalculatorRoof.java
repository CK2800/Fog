/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;

/**
 *
 * @author Claus
 */
public class RuleCalculatorRoof extends RuleCalculator        
{
    public RuleCalculatorRoof(List<MaterialDTO> materials)
    {
        super(materials);
    }

    @Override
    protected int calculate(CarportRequestDTO carportRequestDTO, List<BillItem> bill) throws FogException
    {        
        /**
         * PSEUDO:
         Hent forespørgslens tagtype.
         * Hent materialer
         * hvis forespørgslen IKKE har hældning...
         */
        // Hent tagryg- og tagfladematerialer.
        List<MaterialDTO> ridges = materials.get(MaterialtypeId.RIDGE.name());
        List<MaterialDTO> sheetings = materials.get(MaterialtypeId.SHEETING.name());
        
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
                bill.add(new BillItem(longest, count, "lang tagflade"));
                bill.add(new BillItem(shortest, count, "kort tagflade"));
                return 2;
                
            }
            catch(Exception e)
            {
                throw new FogException("Tagbelægning, fladt tag, kan ikke beregnes.", e.getMessage());
            } 
        }
        
        
        return 0;
    }
    
}
