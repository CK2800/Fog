/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;

/**
 * Facade som tilbyder offentlige metoder mod Logic laget.
 * Facaden er tilstandsløs (indtil videre), og kunne tilbyde 
 * metoderne som klassemetoder.
 * @author Claus
 */
public class LogicFacade
{    
    public static List<BillItem> calculateBill(CarportRequestDTO carportRequest, List<MaterialDTO> materials) throws FogException
    {
        Calculator calculator = new Calculator(materials);
        return calculator.calculateBill(carportRequest);        
    }
    
    public static List<Rectangle> drawCarport(CarportRequestDTO carportRequest, List<MaterialDTO> materials) throws FogException
    {
        Drawer drawer = new Drawer(materials);
        return drawer.drawCarport(carportRequest);
    }
}
