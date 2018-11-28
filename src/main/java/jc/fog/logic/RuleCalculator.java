/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jc.fog.exceptions.FogException;

/**
 *
 * @author Claus
 */
public abstract class RuleCalculator
{
    protected static enum MaterialtypeId
    {
        HEAD (BusinessRules.HEAD_TYPE_ID), // Materiale type id for spær træ.
        POST (BusinessRules.POST_TYPE_ID), // Materiale type id for stolper.
        BATTENS (BusinessRules.BATTENS_TYPE_ID), // Materiale type id for lægter.
        PLANKS (BusinessRules.PLANKS_TYPE_ID), // Beklædningsplanker til skur.
        PRE_FAB_RAFTERS (BusinessRules.PRE_FAB_RAFTERS_TYPE_ID), // Materiale type id byg selv spær.       
        RIDGE (BusinessRules.ROOF_RIDGE_TYPE_ID), // Materialetype id for tagrygbelægning.
        SHEETING (BusinessRules.ROOF_SHEETING_TYPE_ID); // Materialetype id for tagfladebelægning.
        private final int materialtypeId;
        
        private MaterialtypeId(int materialtypeId)
        {
            this.materialtypeId = materialtypeId;
        }
        public int getMaterialtypeId(){return materialtypeId;}
    }
    
    protected static HashMap<String, List<MaterialDTO>> materials;
    protected abstract int calculate(CarportRequestDTO carportRequest, List<BillItem> bill) throws FogException;            
    
    /**
     * Initialiserer krævede attributter inden brugen af nedarvninger af denne klasse.
     * 
     * Konstruktør i abstrakt klasse kan ikke kaldes.
     * Nedarvninger må kalde denne konstruktør.
     * @param materialList
     * @param carportRequest
     * @param materials 
     */
    public RuleCalculator(List<MaterialDTO> materialList)
    {
        if (materials == null)        
            initializeMaterials(materialList);            
        
    }
    /**
     * Initialiserer et statisk HashMap med kombinationer af String og List (af MaterialDTO objekter).
     * Hashmap tilgås af de forskellige beregnere, der til sammen udregner styklisten.
     * @param materialList 
     */
    private static void initializeMaterials(List<MaterialDTO> materialList)
    {        
        // Sort the list ascending on materialtypeId.
        //materialList.sort((a,b) -> a.compareTo(b));
        // Opret ny hashmap.
        materials = new HashMap<>();

        for(MaterialtypeId mtId : MaterialtypeId.values())
        {           
            materials.put(mtId.name(), filter(materialList, mtId.getMaterialtypeId()));
        }        
    }
    
    /**
     * Filters a List of MaterialDTO on the type id.
     * @param list
     * @param typeId
     * @return 
     */
    private static List<MaterialDTO> filter(List<MaterialDTO> list, int typeId)
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
//    protected void sortLengthDesc(List<MaterialDTO> list)
//    {
//        Collections.sort(list, Comparator.comparing(MaterialDTO::getLength));
//        Collections.reverse(list);
//    }
    HERTIL
    protected MaterialDTO findShortest(List<MaterialDTO> list, int count, int length)
    {
        MaterialDTO materialeDTO = null;
        for(MaterialDTO m : list)
        {
            if (m.getLength() * count >= length && materialeDTO != null && materialeDTO.getLength() > m.getLength())
                materialeDTO = m;            
        }
        return materialeDTO;
    }
}
