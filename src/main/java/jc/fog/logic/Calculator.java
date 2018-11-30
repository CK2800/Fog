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
 *
 * @author Claus
 */
public class Calculator
{
    private ArrayList<RulesCalculator> calculators;
    
    public Calculator(List<MaterialDTO> materials)
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
        
        // Gennemløb alle rule calculators.        
        for(RulesCalculator calculator : calculators)
        {
            bill.addAll(calculator.calculate(carportRequest));
        }

        return bill;
    }
}