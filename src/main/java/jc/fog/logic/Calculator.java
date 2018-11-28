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
    private ArrayList<RuleCalculator> rules;
    
    public Calculator(List<MaterialDTO> materials)
    {
        // Initialiserer RuleCalculators hashmap.
        RuleCalculator.initializeMaterials(materials);
        // Opret instanser af RuleCalculators.         
        initializeRules(materials);
    }
    
    private void initializeRules(List<MaterialDTO> materials)
    {        
        rules = new ArrayList<RuleCalculator>();
        
        rules.add(new RuleCalculatorHead()); // Udregner rem.
        rules.add(new RuleCalculatorPost()); // Udregner stolper.
        rules.add(new RuleCalculatorRafters()); // Udregner spær.
        rules.add(new RuleCalculatorShed()); // Udregner skurets beklædning.
        rules.add(new RuleCalculatorBattens()); // Udregner lægter.
        rules.add(new RuleCalculatorRoof()); // Udregner tagbelægning.
    }
            
    protected List<BillItem> calculateBill(CarportRequestDTO carportRequest) throws FogException
    {                           
        // Opret tom stykliste.
        ArrayList<BillItem> bill = new ArrayList<>();
        
        // Gennemløb alle rule calculators.        
        for(RuleCalculator rule : rules)
        {
            rule.calculate(carportRequest, bill);
        }

        return bill;
    }
}