/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Claus
 */
public class RulesDrawerHead extends RulesDrawer
{
    /**
     * Initierer instans af RulesDrawer implementation.
     *      
     * @param billItem Indeholder dimensioner for de materialer der skal tegnes.
     */
    public RulesDrawerHead(BillItem billItem)
    {
        super(billItem);
    }
    /**
     * Opretter en List af Rectangle objekter på baggrund af carportens og materialernes dimensioner.
     * @param carportRequestDTO Indeholder dimensioner for carport.     
     * @return 
     */
    @Override
    protected List<Rectangle> draw(CarportRequestDTO carportRequestDTO)
    {
        List<Rectangle> result = new ArrayList();
       // Tegn hele carportens tag.
       result.add(new Rectangle(0,0,carportRequestDTO.getLength(), carportRequestDTO.getWidth(), "eaea00"));
       // Rem tegnes forskudt fra tagets kant med udhængets længde.
       result.add(new Rectangle(Rules.OVERHANG, Rules.OVERHANG, carportRequestDTO.getLength()-(2*Rules.OVERHANG), 10, "ffeebb"));
       result.add(new Rectangle(carportRequestDTO.getWidth()-Rules.OVERHANG-10,Rules.OVERHANG, carportRequestDTO.getLength()-(2*Rules.OVERHANG), 10, "ffeebb"));
       
       return result;
    }
}
