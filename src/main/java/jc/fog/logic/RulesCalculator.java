/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import jc.fog.logic.Rules;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import jc.fog.exceptions.FogException;
import jc.fog.logic.BillItem;
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.MaterialCount;
import jc.fog.logic.MaterialDTO;
import jc.fog.logic.Rectangle;

/**
 *
 * @author Claus
 */
public abstract class RulesCalculator extends Rules
{
    /**
     * Opdeling af List af MaterialDTO objekter i subsets baseret på materialets type.
     */
    protected static HashMap<String, List<MaterialDTO>> materials;
    /**
     * Udregner materialeforbrug til stykliste.
     * @param carportRequest
     * @return
     * @throws FogException 
     */
    protected abstract List<BillItem> calculate(CarportRequestDTO carportRequest) throws FogException;            
    /**
     * Samling af Rectangle objekter til tegning af carport.
     */
    protected List<Rectangle> rectangles;

    /**
     * Initialiserer et statisk HashMap med kombinationer af String og List (af MaterialDTO objekter).
     * Hver List indeholder MaterialDTO objekter med samme materiale type id.
     * Hashmap tilgås af de forskellige beregnere, der til sammen udregner styklisten.
     * @param materialList 
     */
    public static void initializeMaterials(List<MaterialDTO> materialList) throws FogException
    {   
        try
        {
            // Opret ny hashmap.
            materials = new HashMap<>();
            // Gennemløb enum og opret HashMap key-value pair med subset af listen, hvor 
            // hvert MaterialDTO objekts materialtypeId svarer til værdien i enum.
            for(Materialtype mt : Materialtype.values())
            {           
                materials.put(mt.name(), filter(materialList, mt.getMaterialtypeId()));
            }
        }
        catch(Exception e)
        {
            throw new FogException("Materialer blev ikke initialiseret", e.getMessage(), e);
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
     * Finder korteste materiale i listen af MaterialDTO objekter.
     * @param list Listen af MaterialDTO objekter som skal gennemsøges.
     * @param length Længden som materialets længde * count skal dække.
     * @return 
     */    
    protected MaterialCount findShortest(List<MaterialDTO> list, int length) throws FogException
    {
        try
        {
            MaterialDTO materialeDTO = null;
            String materialType = "";
            int actualCount = 0, count;        
            for(MaterialDTO m : list)
            {                
                materialType = m.getMaterialtypeDTO().getType();
                // Da vi har promoted dividend og divisor til double v. division for at 
                // kunne runde antal materialer op til nærmeste hele antal, får vi ingen
                // divide by zero - exception, fordi den kun kastes ved heltalsdivision.
                // Derfor tjekker vi "manuelt" om materialet har en brugbar længde.
                if (m.getLength() > 0)
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
            }
            if (actualCount > 0)
                return new MaterialCount(actualCount, materialeDTO);        
            else // Kast en generisk exception om at ønsket materialetype i ønsket længde ej blev fundet.
                throw new Exception("Intet materiale af typen: " + materialType + " fundet, som passer til den krævede længde: " + length);                
        }        
        catch(Exception e)
        {
            throw new FogException("Intet materiale fundet.", e.getMessage(), e);
        }
    }
    
    /**
     * Udregner hypotenusen for en retvinklet trekant, hvor vinkel og hosliggende katetes længde er kendt.
     * F.eks.: carport bredde = 540 cm., taghældning = 15 grader. Hosliggende katete er 270 cm (halv tagbredde).
     * Formel: Hypotenuse = katete / cos(vinkel)
     * @param width Hosliggende katetes længde
     * @param degSlope Vinkel i grader, for hvilken gælder at 0 < degSlope < 90.
     * @return Hypotenusens længde.
     * @throws jc.fog.exceptions.FogException 
     */
    protected double calculateSlopedWidth(double width, int degSlope) throws FogException
    {
        try
        {
            // stopping conditions er <= 0 og >= 90.
            if (degSlope <= 0 || degSlope >= 90)
                throw new Exception("Vidde kan ikke beregnes for vinkler på 0 eller derunder ELLER på 90 eller derover.");
            return width / Math.cos(Math.toRadians(degSlope));
        }
        catch(Exception e)
        {
            throw new FogException("Ugyldig vinkel.", e.getMessage(), e);
        }
    }
}
