/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;

/**
 * Udvidelse af RuleCalculator for udregning af rem.
 * @author Claus
 */
public class RuleCalculatorHead extends RuleCalculator
{      
    @Override
    protected int calculate(CarportRequestDTO carportRequest, List<BillItem> bill) throws FogException
    {
        try
        {
            // Remmen laves af spærtræ, find samlingen i hashmap.
            List<MaterialDTO> heads = materials.get(MaterialtypeId.BATTENS.name());
            // Remmen bærer taget, udhæng 30 cm i hver ende.
            int headLength = carportRequest.getLength() - 2 * BusinessRules.OVERHANG;                        
            // Find korteste materiale og antal.
            MaterialCount materialCount = findShortest(heads, headLength);                                    
            // Tilføj til styklisten, husk at rem er i begge sider.
            bill.add(new BillItem(materialCount.getMaterialDTO(), materialCount.getCount() * 2, "rem instruks"));
            // 1 nyt item på styklisten.
            return 1;            
        }        
        catch(Exception e)
        {
            throw new FogException("Beregning af spærtræ fejlede.", e.getMessage());
        }
    }
}
