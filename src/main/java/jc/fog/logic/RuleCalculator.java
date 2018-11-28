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
    /**
     * Samling af konstanter med materialetype id for lettere opdeling af List til HashMap.
     * Eksempel: HEAD (4).
     * @see initializeMaterials
     */
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
     * Initialiserer et statisk HashMap med kombinationer af String og List (af MaterialDTO objekter).
     * Hashmap tilgås af de forskellige beregnere, der til sammen udregner styklisten.
     * @param materialList 
     */
    public static void initializeMaterials(List<MaterialDTO> materialList)
    {   
        // Opret ny hashmap.
        materials = new HashMap<>();
        // Gennemløb enum og opret HashMap key-value pair med subset af listen, hvor 
        // hvert MaterialDTO objekts materialtypeId svarer til værdien i enum.
        for(MaterialtypeId mtId : MaterialtypeId.values())
        {           
            materials.put(mtId.name(), filter(materialList, mtId.getMaterialtypeId()));
        }        
    }
    
    /**
     * Filtrerer en List af MaterialDTO på materialtype id.
     * F.eks. en liste af MaterialDTO objekter hvor materialtypeId er 1, dvs. en liste af spærtræ.
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
     * Sorts the List of MaterialDTO on length in descending order.
     * @param list     
     */
    protected void sortLengthDesc(List<MaterialDTO> list)
    {
        Collections.sort(list, Comparator.comparing(MaterialDTO::getLength));
        Collections.reverse(list);
    }
    
    /**
     * Finder korteste materiale i listen af MaterialDTO objekter som skal være sorteret på længden.
     * @param list Listen af MaterialDTO objekter som skal gennemsøges.
     * @param count Antal af det krævede materiale.
     * @param length Længden som materialets længde * count skal dække.
     * @return 
     */
    //protected MaterialDTO findShortest(List<MaterialDTO> list, int count, int length)
    protected MaterialCount findShortest(List<MaterialDTO> list, int length)
    {
        MaterialDTO materialeDTO = null;
        int actualCount = 0, count;        
        for(MaterialDTO m : list)
        {
            count = (int)Math.ceil((double)length / m.getLength());
            
            // Hvis aktuelt antal ikke er sat endnu, gøres det.
            if (actualCount == 0)
            {
                actualCount = count;
                materialeDTO = m;
            }
            // Hvis aktuelt antal er større, har vi fundet et længere materiale der duer.
            else if (actualCount > count)
            {
                actualCount = count;
                materialeDTO = m;                
            }
            // Hvis
            else if (actualCount == count)
            {
                if (materialeDTO != null && materialeDTO.getLength() > m.getLength())
                    materialeDTO = m;
            }                
        }
        return new MaterialCount(actualCount, materialeDTO);        
    }
}
