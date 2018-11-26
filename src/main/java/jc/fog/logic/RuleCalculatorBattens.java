/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;

/**
 * Udvidelse af RuleCalculator for udregning af lægter.
 * @author Claus
 */
public class RuleCalculatorBattens extends RuleCalculator
{

    @Override
    protected int calculate(CarportRequestDTO carportRequest, List<MaterialDTO> materials, List<BillItem> bill) throws FogException
    {
        // Find materialer - typen har id 7
        List<MaterialDTO> battens = filter(materials, BusinessRules.BATTENS_TYPE_ID);
        // Sorter 
        sortLengthDesc(battens);
        int count = (int)Math.ceil((float)carportRequest.getLength() / battens.get(0).getLength());
        // Find korteste lægte
        MaterialDTO materiale = findShortest(battens, count, carportRequest.getLength());
        // Find tagets længde:
        int halfCarportWidth = carportRequest.getWidth()/2;
        int hypothenuse = (int)Math.ceil(halfCarportWidth / Math.cos(Math.toRadians(carportRequest.getSlope())));
        // Business rule: ca. 30 cm ml. lægter.
        int noBattenRows = (int)Math.floor(hypothenuse / BusinessRules.BATTENS_SPACING);
        bill.add(new BillItem(materiale, count * noBattenRows * 2, "lægte tekst"));
        return 1;
    }    
}
