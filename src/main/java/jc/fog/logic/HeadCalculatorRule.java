/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;
import jc.fog.logic.MaterialeDTO;
import jc.fog.logic.BillItem;

/**
 * Udvidelse af CalculatorRule for udregning af rem.
 * @author Claus
 */
public class HeadCalculatorRule extends CalculatorRule
{    
    @Override
    protected int calculate(ForesporgselDTO forespoergsel, List<MaterialeDTO> materialer, List<BillItem> stykliste) throws FogException
    {
        try
        {
            // Remmen laves af spærtræ som har varetypeid 4
            List<MaterialeDTO> spaerTraeList = filter(materialer, 4);
            // Sorter på laengde, faldende.
            sortLengthDesc(spaerTraeList);
            // Hent længste træ.
            MaterialeDTO materiale = spaerTraeList.get(0);
            
            // Remmen bærer taget, udhæng 30 cm i hver ende.
            int remLength = forespoergsel.getLaengde() - 60;                        
            // Hvor mange stk. træ skal der min. bruges?
            int antal = (int)Math.ceil((float)remLength/materiale.getLaengde());            
            // Find korteste spærtræ med krævet længde.
            materiale = findShortest(spaerTraeList, antal, remLength);
                        
            stykliste.add(new BillItem(materiale, antal, "spær instruks"));
            // 1 nyt item på styklisten.
            return 1;            
        }        
        catch(Exception e)
        {
            throw new FogException("Beregning af spærtræ fejlede.", e.getMessage());
        }
    }
}
