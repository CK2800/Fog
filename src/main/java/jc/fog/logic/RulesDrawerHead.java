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
    
    public RulesDrawerHead(CarportRequestDTO carportRequestDTO, BillItem billItem)
    {
        super(carportRequestDTO, billItem);
    }
    @Override
    protected List<Rectangle> draw()
    {
        List<Rectangle> result = new ArrayList();
       // Tegn hele carportens tag.
       result.add(new Rectangle(0,0,carportRequestDTO.getLength(), carportRequestDTO.getWidth(), "eaea00"));
       // Rem tegnes forskudt fra tagets kant med udhængets længde.
       result.add(new Rectangle(Rules.OVERHANG, Rules.OVERHANG, carportRequestDTO.getLength()-(2*Rules.OVERHANG), 10, "ffeebb"));
       result.add(new Rectangle(Rules.OVERHANG, carportRequestDTO.getWidth()-Rules.OVERHANG, carportRequestDTO.getLength()-(2*Rules.OVERHANG), 10, "ffeebb"));
       
       return result;
    }
}
