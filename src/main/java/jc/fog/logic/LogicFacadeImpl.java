/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import jc.fog.logic.dto.CarportRequestDTO;
import jc.fog.logic.dto.MaterialDTO;
import java.util.List;
import jc.fog.exceptions.FogException;

/**
 * Facade som tilbyder offentlige metoder mod Logic laget.
 * @author Claus
 */
public class LogicFacadeImpl implements LogicFacade
{    
    @Override
    public List<BillItem> calculateBill(CarportRequestDTO carportRequest, List<MaterialDTO> materials) throws FogException
    {
        Calculator calculator = new Calculator(materials);
        return calculator.calculateBill(carportRequest);        
    }
    
    @Override
    public List<Rectangle> drawCarport(CarportRequestDTO carportRequest, List<MaterialDTO> materials) throws FogException
    {
        Drawer drawer = new Drawer(materials);
        return drawer.drawCarport(carportRequest);
    }
}
