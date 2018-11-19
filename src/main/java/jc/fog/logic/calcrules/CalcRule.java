/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic.calcrules;

import java.util.List;
import jc.fog.logic.VareDTO;

/**
 *
 * @author Claus
 */
public interface CalcRule
{
    public int calculate(ForesporgselDTO forespoergsel, List<MaterialeDTO> varer, List<StyklisteItem> stykliste);
            
}
