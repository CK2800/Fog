/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic.calculators;

import jc.fog.logic.RulesDrawer;
import jc.fog.logic.RulesCalculator;
import jc.fog.logic.Rules;
import java.util.ArrayList;
import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.BillItem;
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.MaterialDTO;
import jc.fog.logic.Rectangle;
import jc.fog.logic.ShedDTO;

/**
 * Udvidelse af RulesCalculator for udregning af skurets beklædning.
 * @author Claus
 */
public class RulesCalculatorShed extends RulesCalculator implements RulesDrawer
{    
    @Override
    protected List<BillItem> calculate(CarportRequestDTO carportRequest) throws FogException
    {  
        try
        {
            List<BillItem> result = new ArrayList();
            ShedDTO shed = carportRequest.getShedDTO();
            if (shed != null)
            {
                // Find materiale til skuret => trykimp. brædder, 19x100mm i 210 cm                        
                List<MaterialDTO> planks = materials.get(Materialtype.PLANKS.name());
                MaterialDTO plank = null;
                for(MaterialDTO m : planks)
                {
                    if ("19x100 mm.".equals(m.getName()) && m.getLength() == 210)
                        plank = m;
                }                

                // Find dimensioner for skur.
                int length = shed.getLength();
                int width = shed.getWidth();                        

                // antal brædder i skurets længderetning, inderste brædder placeres med 6 cm mellemrum.
                int noLength = (int)Math.ceil(length / (Rules.PLANK_SPACING + Rules.PLANK_WIDTH)) * 2;
                // antal brædder i skurets bredde med overlap på 1 cm i hver side.
                int noWidth = (int)Math.ceil(width / (Rules.PLANK_SPACING + Rules.PLANK_WIDTH)) * 2;
                
                // Fandt vi materiale, returner nyt billitem.
                if (plank != null)
                    result.add(new BillItem(plank, noLength + noWidth, CarportPart.PLANKS, "skur brædder 1 på 2"));
                else // Intet materiale medfører exception.
                    throw new FogException("Skurbeklædning kunne ikke beregnes.", "Ingen planker fundet.");
                
            }
            return result;
        }
        catch(Exception e)
        {
            throw new FogException("Skurbeklædning kunne ikke beregnes.", e.getMessage());
        }
    }

    @Override
    public List<Rectangle> draw(CarportRequestDTO carportRequest) throws FogException
    {
        try
        {
            List<Rectangle> rectangles = new ArrayList();
            // Læg blot rektangel i samlingen, som har dimensioner som skuret i forespørgslen.
            if (carportRequest.getShedDTO() != null)
            {
                int halfPostHeight = (int)Math.ceil(Rules.POST_HEIGHT/2);
                rectangles.add(new Rectangle(Rules.OVERHANG - halfPostHeight, Rules.OVERHANG, carportRequest.getShedDTO().getLength(), carportRequest.getShedDTO().getWidth(), "000000"));
            }
            return rectangles;
        }
        catch(Exception e)
        {
            throw new FogException("Skur kan ikke tegnes.", e.getMessage());
        }
    }
    
}
