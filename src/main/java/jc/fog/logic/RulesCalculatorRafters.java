/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.ArrayList;
import java.util.List;
import jc.fog.exceptions.FogException;

/**
 * Udvidelse af RulesCalculator for udregning af spær.
 * @author Claus
 */
public class RulesCalculatorRafters extends RulesCalculator implements RulesDrawer
{
    /**
     * Angiver hvor mange spær, carporten skal have.
     * Værdien udregnes i calculateMaterials().
     */
    private int noRafters;
    /**
     * Længde på hvert stk. træ i et spær.
     * Værdien udregnes i calculateMaterials().
     */
    private int raftersLength;
    /** 
     * Angiver udregnet mellemrum ml. spær.
     */
    private int raftersSpacing;
    /**
     * Tagets længde.
     */
    private int roofLength;
    
    @Override
    protected List<BillItem> calculate(CarportRequestDTO carportRequest) throws FogException
    {
        List<BillItem> result = new ArrayList();
        MaterialCount materialCount = calculateMaterials(carportRequest);
        if (carportRequest.getSlope() == 0) // fladt tag.
        {
            result.add(new BillItem(materialCount.getMaterialDTO(), noRafters*materialCount.getCount(), CarportPart.RAFTERS, "spær fladt tag"));                        
        }
        else
        {
            result.add(new BillItem(materialCount.getMaterialDTO(), noRafters, CarportPart.PRE_FAB_RAFTERS, "byg selv spær"));  
        }
        return result;
    }
    
//    @Override
//    protected List<BillItem> calculate(CarportRequestDTO carportRequest) throws FogException
//    {
//        try
//        {
//            List<MaterialDTO> rafters;
//            List<BillItem> result = new ArrayList();
//            // er taget med rejsning?
//            if (carportRequest.getSlope() > 0) 
//            {
//                rafters = materials.get(Materialtype.PRE_FAB_RAFTERS.name());
//                // udhæng er 30 cm i hver ende.
//                int roofLength = carportRequest.getLength() - 2 * Rules.OVERHANG;
//                // Spær placeres højst 0,89 m fra hinanden.
//                int noRafters = (int)Math.ceil(roofLength / Rules.RAFTER_SPACING_SLOPED_ROOF);
//                MaterialDTO material = rafters.get(0);
//
//                if (material != null)
//                {                
//                    result.add(new BillItem(material, noRafters, CarportPart.PRE_FAB_RAFTERS, "byg selv spær"));                
//                }
//                else
//                    throw new FogException("Byg selv spær kunne ikke findes for denne carport.", "Ingen materialer med materialetype 24 (byg selv spær)");
//            }
//            else // fladt tag.
//            {
//                // Find materialer i hashmap.            
//                rafters = materials.get(Materialtype.RAFTERS.name());
//                // Hent antal korteste træ nødvendigt.
//                MaterialCount materialCount = findShortest(rafters, carportRequest.getWidth());            
//                // Find tagets længde og udregn antal spær, adskilt 55 cm fra hinanden.
//                int roofLength = carportRequest.getLength();            
//                int noRafters = (int)Math.ceil(roofLength / Rules.RAFTER_SPACING);                                    
//                //bill.add(new BillItem(material, noRafters*noRequired, "spær fladt tag"));
//                result.add(new BillItem(materialCount.getMaterialDTO(), noRafters*materialCount.getCount(), CarportPart.RAFTERS, "spær fladt tag"));                        
//            }
//            return result;
//        }
//        catch(Exception e)
//        {
//            throw new FogException("Spær kunne ikke beregnes.", e.getMessage());
//        }
//    }    

    @Override
    public List<Rectangle> draw(CarportRequestDTO carportRequest) throws FogException
    {
        try
        {
            rectangles = new ArrayList();
            // Udregn antal materialer og deres længde pr. spær
            MaterialCount materialCount = calculateMaterials(carportRequest);
            // antal træ pr. spær.
            int count = materialCount.getCount();
            // længde 
            int materialLength = carportRequest.getWidth() / count;                        
            
            for(int i = 0; i < noRafters; i++)
            {
                for(int a = 0; a < count; a++)
                {
                    // y position er i 0 + udhæng + mellemrum ml. spær - halv spær tykkelse.
                    int yPos = Rules.OVERHANG + (i * raftersSpacing) + (int)HEAD_HEIGHT/2; 
                    if (i == noRafters-1) // sidste spær lægges så bagkant flugter rem.
                        yPos = carportRequest.getLength() - Rules.OVERHANG - HEAD_HEIGHT;
                    
                    rectangles.add(new Rectangle(a * materialLength, yPos, HEAD_HEIGHT, materialLength, "995522"));
                }                    
            }           
            
            return rectangles;
        }
        catch(Exception e)
        {
            throw new FogException("Spær kan ikke tegnes.", e.getMessage());
        }
    }
    
    private MaterialCount calculateMaterials(CarportRequestDTO carportRequest) throws FogException
    {
        try
        {
            List<MaterialDTO> rafters;            
            // udhæng er 30 cm i hver ende.
            roofLength = carportRequest.getLength() - 2 * Rules.OVERHANG;            
            // er taget med rejsning?
            if (carportRequest.getSlope() > 0) 
            {
                // Find materialer i hashmap.           
                rafters = materials.get(Materialtype.PRE_FAB_RAFTERS.name());                
                // Spær placeres ca 0,89 m fra hinanden.
                noRafters = (int)Math.ceil(roofLength / Rules.RAFTER_SPACING_SLOPED_ROOF) + 1; // Et spær ekstra, da vi har udregnet antal mellemrum.
                // Gem aktuel afstand ml. spær.
                raftersSpacing = (int)Math.ceil(roofLength / (float)(noRafters-1)); // Husk at fratrække slut spær for at få nok mellemrum.
                MaterialDTO material = rafters.get(0);

                return new MaterialCount(1, material);
            }
            else // fladt tag.
            {
                // Find materialer i hashmap.            
                rafters = materials.get(Materialtype.RAFTERS.name());
                // Find tagets længde og udregn antal spær, adskilt 55 cm fra hinanden.                
                noRafters = (int)Math.ceil(roofLength / Rules.RAFTER_SPACING) + 1;// Et spær ekstra, da vi har udregnet antal mellemrum.     
                // Gem aktuel afstand ml. spær.
                raftersSpacing = (int)Math.ceil(roofLength / (float)(noRafters-1));// Husk at fratrække slut spær for at få nok mellemrum.
                // Hent antal korteste træ nødvendigt pr. spær.
                MaterialCount materialCount = findShortest(rafters, carportRequest.getWidth());            
                
                return materialCount;
            }            
        }
        catch(Exception e)
        {
            throw new FogException("Materialer til spær kan ikke udregnes.", e.getMessage());
        }
    }
    
}
