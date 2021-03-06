/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.dto.CarportRequestDTO;
import jc.fog.logic.dto.MaterialDTO;

/**
 * Facade interface som tilbyder offentlige metoder mod Logic laget.
 * @author Claus
 */
public interface LogicFacade
{
    List<BillItem> calculateBill(CarportRequestDTO carportRequest, List<MaterialDTO> materials) throws FogException;
    List<Rectangle> drawCarport(CarportRequestDTO carportRequest, List<MaterialDTO> materials) throws FogException;
}
