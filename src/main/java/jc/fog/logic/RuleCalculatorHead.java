/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.MaterialDTO;
import jc.fog.logic.BillItem;

/**
 * Udvidelse af RuleCalculator for udregning af rem.
 * @author Claus
 */
public class RuleCalculatorHead extends RuleCalculator
{    
    @Override
    protected int calculate(CarportRequestDTO carportRequest, List<MaterialDTO> materials, List<BillItem> bill) throws FogException
    {
        try
        {
            // Remmen laves af spærtræ, varetypeid står i business rules.
            List<MaterialDTO> heads = filter(materials, BusinessRules.HEAD_TYPE_ID);
            // Sorter på laengde, faldende.
            sortLengthDesc(heads);
            // Hent længste træ.
            MaterialDTO material = heads.get(0);
            
            // Remmen bærer taget, udhæng 30 cm i hver ende.
            int headLength = carportRequest.getLength() - 2 * BusinessRules.OVERHANG;                        
            // Hvor mange stk. træ skal der min. bruges?
            int count = (int)Math.ceil((float)headLength/material.getLength());            
            // Find korteste spærtræ med krævet længde.
            material = findShortest(heads, count, headLength);
                        
            bill.add(new BillItem(material, count, "spær instruks"));
            // 1 nyt item på styklisten.
            return 1;            
        }        
        catch(Exception e)
        {
            throw new FogException("Beregning af spærtræ fejlede.", e.getMessage());
        }
    }
}
