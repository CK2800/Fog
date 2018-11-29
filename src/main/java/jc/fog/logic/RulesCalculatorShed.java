/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;

/**
 * Udvidelse af RulesCalculator for udregning af skurets beklædning.
 * @author Claus
 */
public class RulesCalculatorShed extends RulesCalculator
{    
    @Override
    protected int calculate(CarportRequestDTO carportRequest, List<BillItem> bill) throws FogException
    {        
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
            
            bill.add(new BillItem(plank, noLength + noWidth, CarportPart.PLANKS, "skur brædder 1 på 2"));
            return 1; // 1 nyt item på styklisten.
        }
        else 
            return 0; // Ingen styklisteitem tilføjet.
    }
    
}
