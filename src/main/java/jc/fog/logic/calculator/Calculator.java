/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic.calculator;

import java.util.ArrayList;
import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;
import jc.fog.logic.MaterialeDTO;
import jc.fog.logic.StyklisteItem;

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
        
        rules.add(new RemCalculatorRule());
        rules.add(new StolpeCalculatorRule());
        rules.add(new SpaerCalculatorRule());
    }
    
    public static List<StyklisteItem> beregnStykliste(ForesporgselDTO forespoergsel, List<MaterialeDTO> materialer) throws FogException
    {
        if (rules == null)
            initializeRules();
        
        ArrayList<StyklisteItem> stykliste = new ArrayList<>();
        
        
        for(CalculatorRule rule : rules)
        {
            rule.calculate(forespoergsel, materialer, stykliste);
        }

        return stykliste;
    }