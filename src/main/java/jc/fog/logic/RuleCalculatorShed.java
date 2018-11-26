/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.MaterialDTO;
import jc.fog.logic.ShedDTO;
import jc.fog.logic.BillItem;

/**
 * Udvidelse af RuleCalculator for udregning af skurets beklædning.
 * @author Claus
 */
public class RuleCalculatorShed extends RuleCalculator
{

    @Override
    protected int calculate(CarportRequestDTO carportRequest, List<MaterialDTO> materials, List<BillItem> bill) throws FogException
    {        
        ShedDTO shed = carportRequest.getShedDTO();
        if (shed != null)
        {
            // Find materiale til skuret => trykimp. brædder, 19x100mm i 210 cm (id 9)                        
            MaterialDTO material = materials.get(8);
                        
            // Find dimensioner for skur.
            int length = shed.getLength();
            int width = shed.getWidth();                        
            
            // antal brædder i skurets længderetning, inderste brædder placeres med 6 cm mellemrum.
            int noLength = (int)Math.ceil(length / (BusinessRules.PLANK_SPACING + BusinessRules.PLANK_WIDTH)) * 2;
            // antal brædder i skurets bredde med overlap på 1 cm i hver side.
            int noWidth = (int)Math.ceil(width / (BusinessRules.PLANK_SPACING + BusinessRules.PLANK_WIDTH)) * 2;
            
            bill.add(new BillItem(material, noLength + noWidth, "skur brædder 1 på 2"));
            return 1; // 1 nyt item på styklisten.
        }
        else 
            return 0; // Ingen styklisteitem tilføjet.
    }
    
}
