/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic.calculators;

import jc.fog.logic.RulesDrawer;
import jc.fog.logic.Rules;
import java.util.ArrayList;
import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.BillItem;
import jc.fog.logic.dto.CarportRequestDTO;
import jc.fog.logic.MaterialCount;
import jc.fog.logic.dto.MaterialDTO;
import jc.fog.logic.Rectangle;

/**
 * Udvidelse af RulesCalculator for udregning af rem.
 * @author Claus
 */
public class RulesCalculatorHead extends RulesCalculator implements RulesDrawer
{    
    /**
     * Antal remme for carporten.      
     */
    private int noHeads;
    /**
     * Remmens længde.
     * Remmens længde er carportens længde minus evt. udhæng.     
     */
    private int headLength;
    /**
     * Afstand mellem remme, hvis spær er brudt.     
     */
    private int headSpacing;
    
    @Override
    public List<BillItem> calculate(CarportRequestDTO carportRequest) throws FogException
    {        
        try
        {            
            ArrayList<BillItem> result = new ArrayList();
            MaterialCount materialCount = calculateMaterials(carportRequest);
            
            // Returner liste med bill items.            
            result.add(new BillItem(materialCount.getMaterialDTO(), materialCount.getCount() * noHeads, CarportPart.HEAD, "rem instruks"));
            return result;                
        }        
        catch(Exception e)
        {
            throw new FogException("Beregning af spærtræ fejlede.", e.getMessage(), e);
        }        
    }    
    
    /**
     * Udregner længden på rem, materialeforbrug pr. rem og antallet af remme.
     * Længden på remmen lagres i this.headLength.
     * Antallet af remme lagres i this.noHeads.
     * @param carportRequest
     * @return MaterialCount objekt med data om materiale og antal pr. rem.
     * @throws FogException 
     */
    public MaterialCount calculateMaterials(CarportRequestDTO carportRequest) throws FogException
    {
        noHeads = 2; // 2 remme som minimum.
        // Remmen laves af spærtræ, find samlingen i hashmap.
        List<MaterialDTO> heads = materials.get(Materialtype.RAFTERS.name());
        // Remmen bærer taget, som har udhæng 30 cm i hver ende.
        headLength = carportRequest.getLength() - 2 * Rules.OVERHANG;                        
        // Find korteste materiale og antal.
        MaterialCount materialCount = findShortest(heads, headLength);
        
        // Find antallet af remme krævet, ved at se hvor mange gange, spær skæres over, kun ved fladt tag.
        if (carportRequest.getSlope() == 0)
        {
            MaterialCount mcRafters = findShortest(heads, carportRequest.getWidth());
            // Skal der bruges mere end 1 stk træ til et spær, skal overskæringen understøttes med 1 rem.
            noHeads += (mcRafters.getCount()-1);
            // Afstand ml. yderligere remme udregnes.
            headSpacing = (int)Math.ceil(carportRequest.getWidth() / (float)mcRafters.getCount());
        }
        
        return materialCount;
    }

    @Override
    public List<Rectangle> draw(CarportRequestDTO carportRequest) throws FogException
    {
        try
        {
            rectangles = new ArrayList();
            // Udregn antal materialer og deres længde pr. rem
            MaterialCount materialCount = calculateMaterials(carportRequest);
            // Antal 
            int count = materialCount.getCount();
            // Længde
            int materialLength = headLength/count;
            
            // Tegn remme med de krævede stykker træ.
            for(int i=0; i < count; i++)
            {
                // Første rem.
                rectangles.add(new Rectangle(Rules.OVERHANG - HEAD_HEIGHT/2, Rules.OVERHANG + i * materialLength, materialLength, HEAD_HEIGHT, "ffeebb"));
                
                // Sidste rem.
                rectangles.add(new Rectangle(carportRequest.getWidth() - Rules.OVERHANG - HEAD_HEIGHT/2, Rules.OVERHANG + i * materialLength, materialLength, HEAD_HEIGHT, "ffeebb"));
                
                // Evt. remme hvor spær er brudt.
                for(int a = 2; a < noHeads; a++)
                {
                    // udregn x pos for rem.
                    int xPos = (noHeads-a) * headSpacing;
                    rectangles.add(new Rectangle(xPos-HEAD_HEIGHT/2, Rules.OVERHANG + i * materialLength, materialLength, HEAD_HEIGHT, "33aa99"));
                }
            }        
            
            return rectangles;
        }
        catch(Exception e)
        {
            throw new FogException("Rektangler for rem kan ikke udregnes.", e.getMessage(), e);
        }
    }
}
