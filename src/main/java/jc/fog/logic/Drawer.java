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
public class Drawer
{
    private List<RulesDrawer> drawers;
    private List<Rectangle> rectangles;
    
    public Drawer(List<BillItem> bill)
    {
        // Opret tomme lister 
        rectangles = new ArrayList();
        drawers = new ArrayList();
        // Fyld RulesDrawer's i listen.
        for(BillItem b : bill)
        {
            switch(b.getMaterialDTO().)
        drawers.add(new RulesDrawerHead()
        
    }
}
