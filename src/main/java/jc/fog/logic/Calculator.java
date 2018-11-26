/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.ArrayList;
import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.MaterialDTO;
import jc.fog.logic.BillItem;

/**
 *
 * @author Claus
 */
public class Calculator
{
    private static ArrayList<RuleCalculator> rules;
    
    private static void initializeRules()
    {
        rules = new ArrayList<RuleCalculator>();
        
        rules.add(new RuleCalculatorHead());
        rules.add(new RuleCalculatorPost());
        rules.add(new RuleCalculatorRafters());
        rules.add(new RuleCalculatorShed());
        rules.add(new RuleCalculatorBattens());
    }
    
    protected static List<BillItem> beregnStykliste(CarportRequestDTO carportRequest, List<MaterialDTO> materials) throws FogException
    {
        if (rules == null)
            initializeRules();
        
        ArrayList<BillItem> bill = new ArrayList<>();
        
        
        for(RuleCalculator rule : rules)
        {
            rule.calculate(carportRequest, materials, bill);
        }

        return bill;
    }
}