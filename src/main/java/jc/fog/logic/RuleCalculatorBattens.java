/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;

/**
 * Udvidelse af RuleCalculator for udregning af lægter.
 * @author Claus
 */
public class RuleCalculatorBattens extends RuleCalculator
{

    @Override
    protected int calculate(ForesporgselDTO forespoergsel, List<MaterialeDTO> materialer, List<BillItem> stykliste) throws FogException
    {
        // Find materialer - typen har id 7
        List<MaterialeDTO> laegter = filter(materialer, BusinessRules.BATTENS_TYPE_ID);
        // Sorter 
        sortLengthDesc(laegter);
        int count = (int)Math.ceil((float)forespoergsel.getLaengde() / laegter.get(0).getLaengde());
        // Find korteste lægte
        MaterialeDTO materiale = findShortest(laegter, count, forespoergsel.getLaengde());
        // Find tagets længde:
        int halvCarportBredde = forespoergsel.getBredde()/2;
        int hypotenuse = (int)Math.ceil(halvCarportBredde / Math.cos(Math.toRadians(forespoergsel.getHaeldning())));
        // Business rule: ca. 30 cm ml. lægter.
        int antalLaegteRaekker = (int)Math.floor(hypotenuse / BusinessRules.BATTENS_SPACING);
        stykliste.add(new BillItem(materiale, count * antalLaegteRaekker * 2, "lægte tekst"));
        return 1;
    }    
}
