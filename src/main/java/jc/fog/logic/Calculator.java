/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.ArrayList;
import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;
import jc.fog.logic.MaterialeDTO;
import jc.fog.logic.BillItem;

/**
 *
 * @author Claus
 */
public class Calculator
{
    private static ArrayList<CalculatorRule> rules;
    
    private static void initializeRules()
    {
        rules = new ArrayList<CalculatorRule>();
        
        rules.add(new HeadCalculatorRule());
        rules.add(new PostCalculatorRule());
        rules.add(new RaftersCalculatorRule());
        rules.add(new ShedCalculatorRule());
        rules.add(new BattensCalculatorRule());
    }
    
    protected static List<BillItem> beregnStykliste(ForesporgselDTO forespoergsel, List<MaterialeDTO> materialer) throws FogException
    {
        if (rules == null)
            initializeRules();
        
        ArrayList<BillItem> stykliste = new ArrayList<>();
        
        
        for(CalculatorRule rule : rules)
        {
            rule.calculate(forespoergsel, materialer, stykliste);
        }

        return stykliste;
    }
}