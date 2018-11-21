/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

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
public abstract class CalculatorRule
{
    protected abstract int calculate(ForesporgselDTO forespoergsel, List<MaterialeDTO> materialer, List<StyklisteItem> stykliste) throws FogException;            
    
    /**
     * Filters a List of MaterialeDTO on the type id.
     * @param list
     * @param typeId
     * @return 
     */
    protected List<MaterialeDTO> filter(List<MaterialeDTO> list, int typeId)
    {
        Stream<MaterialeDTO> stream = list.stream().filter(m -> m.getMaterialetypeDTO().getId() == typeId);
        List<MaterialeDTO> result = stream.collect(Collectors.toList());
        return result;
    }
    
    /**
     * Sorts the List of MaterialeDTO on laengde in descending order.
     * @param list     
     */
    protected void sortLengthDesc(List<MaterialeDTO> list)
    {
        Collections.sort(list, Comparator.comparing(MaterialeDTO::getLaengde));
        Collections.reverse(list);
    }
    
    protected MaterialeDTO findShortest(List<MaterialeDTO> list, int count, int length)
    {
        MaterialeDTO materialeDTO = null;
        for(MaterialeDTO m : list)
        {
            if (m.getLaengde() * count >= length)
                materialeDTO = m;
            else 
                break;
        }
        return materialeDTO;
    }
}
