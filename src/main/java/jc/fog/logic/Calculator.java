/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jc.fog.exceptions.FogException;
import static jc.fog.logic.RuleCalculator.materials;

/**
 *
 * @author Claus
 */
public class Calculator
{
    private static ArrayList<RuleCalculator> rules;
    
    
    /**
     * Samling af konstanter for div. materialetyper.
     */
    private static enum MaterialtypeId
    {
        
    }    
    
    
    
    /**
     * Filtrerer en liste af MaterialDTO objekter på dets materialetypes id.
     * @param list
     * @param typeId
     * @return 
     */
    private static List<MaterialDTO> filter(List<MaterialDTO> list, int typeId)
    {
        // Filtrer listen og husk at tage højde for evt. nullpointer fra getMaterialtypeDTO().
        Stream<MaterialDTO> stream = list.stream().filter(m -> (m.getMaterialtypeDTO() == null ? 0 == typeId : m.getMaterialtypeDTO().getId() == typeId));
        // Konverter tilbage til List og returner.
        List<MaterialDTO> result = stream.collect(Collectors.toList());
        return result;
    }
    
    private static void initializeRules(List<MaterialDTO> materials)
    {
        rules = new ArrayList<RuleCalculator>();
        
        rules.add(new RuleCalculatorHead(materials)); // Udregner rem.
        rules.add(new RuleCalculatorPost(materials)); // Udregner stolper.
        rules.add(new RuleCalculatorRafters(materials)); // Udregner spær.
        rules.add(new RuleCalculatorShed(materials)); // Udregner skurets beklædning.
        rules.add(new RuleCalculatorBattens(materials)); // Udregner lægter.
        rules.add(new RuleCalculatorRoof(materials)); // Udregner tagbelægning.
    }
    
        
    protected static List<BillItem> calculateBill(CarportRequestDTO carportRequest, List<MaterialDTO> materials) throws FogException
    {           
        // Opret instanser af RuleCalculators.         
        initializeRules(materials);
        
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