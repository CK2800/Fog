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

    @Override
    protected int calculate(CarportRequestDTO forespoergsel, List<MaterialDTO> materials, List<BillItem> bill) throws FogException
    {        
        /**
         * PSEUDO:
         Hent forespørgslens tagtype.
         * Hent materialer
         * hvis forespørgslen IKKE har hældning...
         */
        return 0;
    }
    
}
