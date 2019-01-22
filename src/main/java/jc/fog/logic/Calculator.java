/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import jc.fog.logic.dto.CarportRequestDTO;
import jc.fog.logic.dto.MaterialDTO;
import jc.fog.logic.calculators.RulesCalculatorPost;
import jc.fog.logic.calculators.RulesCalculatorShed;
import jc.fog.logic.calculators.RulesCalculatorHead;
import jc.fog.logic.calculators.RulesCalculatorRafters;
import jc.fog.logic.calculators.RulesCalculatorBattens;
import jc.fog.logic.calculators.RulesCalculatorRoof;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import jc.fog.exceptions.FogException;

/**
 *
 * @author Claus
 */
public class Calculator
{
    private List<RulesCalculator> calculators;
    private List<Callable<List<BillItem>>> callables;
    
    public Calculator(List<MaterialDTO> materials) throws FogException
    {
        // Initialiserer RulesCalculator's hashmap.
        RulesCalculator.initializeMaterials(materials);
        // Opret instanser af RulesCalculators.         
        initializeRulesCalculators();
    }
    
    private void initializeRulesCalculators()
    {        
        calculators = new ArrayList<RulesCalculator>();
        
        calculators.add(new RulesCalculatorHead()); // Udregner rem.
        calculators.add(new RulesCalculatorPost()); // Udregner stolper.
        calculators.add(new RulesCalculatorRafters()); // Udregner spær.
        calculators.add(new RulesCalculatorShed()); // Udregner skurets beklædning.
        calculators.add(new RulesCalculatorBattens()); // Udregner lægter.
        calculators.add(new RulesCalculatorRoof()); // Udregner tagbelægning.
        
    }
            
    protected List<BillItem> calculateBill(CarportRequestDTO carportRequest) throws FogException
    {                           
        // Opret tom stykliste.
        ArrayList<BillItem> bill = new ArrayList<>();
        // Opret tom liste af callables.
        callables = new ArrayList<>();        
        
        // Trådede calculators, Q&D, da showbillcommand først kan returnere når alt er udregnet vha. blokerende invokeAll.
        // I v2 laver vi eks. et command som udregner styklisten via ajax fra siden (Pages.BILL).
        // Opret executor service
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        
        // Da vi gerne vil kunne fange FogExceptions forårsaget i RulesCalculators, må vi bruge Callable istedet for Runnable.
        for(RulesCalculator calculator : calculators)
        {            
            callables.add(new CallableRulesCalculator(calculator, carportRequest));
        }
        
        try
        {
                        
            // Af demo-hensyn, laver vi et blokerende kald (invokeAll) som reelt overflødiggør flere tråde. 
            // I v2 kunne man forestille sig at ajax/xhr kunne hente resultatet når det foreligger.
            List<Future<List<BillItem>>> futures = executorService.invokeAll(callables);            
            executorService.shutdown();
            try
            {
                // Gennemløb alle Future<T> objekter, hent resultatet vha. get() som returnerer List<BillItem>.
                for(Future<List<BillItem>> future : futures)
                    bill.addAll(future.get()); // Er der opstået exception i en af beregnerne, kastes ExecutionException ved kald til get().
            }
            // Den opståede Exception, som kan være en FogException, findes i ExecutionException getCause().
            catch(ExecutionException e)
            {
                if (e.getCause() instanceof FogException)
                {
                    FogException f = (FogException)e.getCause();
                    throw f;
                }
                else
                {
                    throw new FogException("Delberegning fejlede.", e.getMessage(), e);
                }
            }
            // Opstår hvis tråden er blevet afbrudt.
            catch(InterruptedException i)
            {
                throw new FogException("Delberegningen blev afbrudt.", i.getMessage(), i);
            }
            
        }
        // hvis executorService.invokeAll() afbrydes, fanges det her.
        catch(InterruptedException e)
        {
            throw new FogException("Beregningen blev afbrudt, prøv igen.", e.getMessage(), e);
        }
        
           
        
        
//        // Gennemløb alle rule calculators.        
//        for(RulesCalculator calculator : calculators)
//        {
//            bill.addAll(calculator.calculate(carportRequest));
//        }

        return bill;
    }
}