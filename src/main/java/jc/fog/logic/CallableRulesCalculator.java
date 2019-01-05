/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import java.util.concurrent.Callable;
import jc.fog.exceptions.FogException;
import jc.fog.logic.dto.CarportRequestDTO;

/**
 * Wrapper omkring RulesCalculator nedarvninger, som implementerer Callable interfacet, 
 * så RulesCalculators kan bruges i tråde.
 * Callable istedet for Runnable, fordi vi gerne vil kunne håndtere egne exceptions
 * kastet fra RulesCalculators.
 * @author Claus
 */
public class CallableRulesCalculator implements Callable<List<BillItem>>
{
    /**
     * RulesCalculator instans som er selve del-beregneren der udfører udregninger af materialer mv.
     */
    private RulesCalculator rulesCalculator;
    /**
     * Objekt med data for carporten.
     */
    private CarportRequestDTO carportRequestDTO;
    
    /**
     * Instans som kan tilføjes en ExecutorService.
     * @param rulesCalculator Beregner materialer for en del af en carport.
     * @param carportRequestDTO Carportforespørgsel med information om dens dimensioner mv.
     */
    public CallableRulesCalculator(RulesCalculator rulesCalculator, CarportRequestDTO carportRequestDTO)
    {
        this.rulesCalculator = rulesCalculator;
        this.carportRequestDTO = carportRequestDTO;
    }
    
    /**
     * Kaldes af ExecutorService.
     * @return
     * @throws FogException 
     */
    @Override
    public List<BillItem> call() throws FogException
    {
        return rulesCalculator.calculate(carportRequestDTO);
    }
    
}
