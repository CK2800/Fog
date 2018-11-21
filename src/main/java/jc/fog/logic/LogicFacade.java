/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;

/**
 *
 * @author Claus
 */
public class LogicFacade
{
    public static List<StyklisteItem> beregnStykliste(ForesporgselDTO forespoergsel, List<MaterialeDTO> materialer) throws FogException
    {
        return Calculator.beregnStykliste(forespoergsel, materialer);
    }
}
