/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic.calculator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;
import jc.fog.logic.MaterialeDTO;
import jc.fog.logic.StyklisteItem;

/**
 *
 * @author Claus
 */
public class RemCalculatorRule implements CalculatorRule
{    
    @Override
    public List<StyklisteItem> calculate(ForesporgselDTO forespoergsel, List<MaterialeDTO> materialer, List<StyklisteItem> stykliste) throws FogException
    {
        try
        {
            // Remmen bærer taget, udhæng 30 cm i hver ende.
            int remLength = forespoergsel.getLaengde() - 60;

            // Remmen laves af spærtræ som har varetypeid 4
            Stream<MaterialeDTO> spaerTrae = materialer.stream().filter(m -> m.getMaterialetypeDTO().getId() == 4);        
            // Konverter tilbage til liste.
            List<MaterialeDTO> spaerTraeList = spaerTrae.collect(Collectors.toList());
            // Sorter på laengde, faldende.
            Collections.sort(spaerTraeList, Comparator.comparing(MaterialeDTO::getLaengde));
            Collections.reverse(spaerTraeList);
            
            // Find spærtræ med krævet længde.
            int divisor = 1; // Ideelt kan vi dække længden med 1 brædt.
            
            do
            {
                
            }
            while();

            
            return stykliste;

        }        
        catch(Exception e)
        {
            throw new FogException("Beregning af spærtræ fejlede.", e.getMessage());
        }
    }
}
