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
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.MaterialDTO;
import jc.fog.logic.BillItem;

/**
 *
 * @author Claus
 */
public abstract class RuleCalculator
{

    protected abstract int calculate(CarportRequestDTO carportRequest, List<MaterialDTO> materials, List<BillItem> bill) throws FogException;            
    
    /**
     * Filters a List of MaterialDTO on the type id.
     * @param list
     * @param typeId
     * @return 
     */
    protected List<MaterialDTO> filter(List<MaterialDTO> list, int typeId)
    {
        Stream<MaterialDTO> stream = list.stream().filter(m -> m.getMaterialtypeDTO().getId() == typeId);
        List<MaterialDTO> result = stream.collect(Collectors.toList());
        return result;
    }
    
    /**
     * Sorts the List<MaterialeDTO> collection on laengde in descending order.
     * Sorts the List of MaterialDTO on laengde in descending order.
     * @param list     
     */
    protected void sortLengthDesc(List<MaterialDTO> list)
    {
        Collections.sort(list, Comparator.comparing(MaterialDTO::getLength));
        Collections.reverse(list);
    }
    
    protected MaterialDTO findShortest(List<MaterialDTO> list, int count, int length)
    {
        MaterialDTO materialeDTO = null;
        for(MaterialDTO m : list)
        {
            if (m.getLength() * count >= length)
                materialeDTO = m;
            else 
                break;
        }
        return materialeDTO;
    }
}
