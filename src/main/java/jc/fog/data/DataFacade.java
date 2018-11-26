/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.MaterialDTO;
import jc.fog.logic.RooftypeDTO;

/**
 * Facade to expose subsystem methods to clients.
 * 
 * @author Claus
 */
public class DataFacade
{
    /**
     * Get all requests (forespørgsler).
     * @return List of CarportRequestDTO objects.
     */
    public static List<CarportRequestDTO> getCarports() throws FogException
    {
        return CarportDAO.getCarportRequests();
    }
    
    public static CarportRequestDTO getCarport(int id) throws FogException
    {
        return CarportDAO.getCarportRequest(id);
    }
    
    public static MaterialDTO getMaterial(int id) throws FogException
    {
        return MaterialDAO.getMaterial(id);        
    }
    
    public static List<MaterialDTO> getMaterials() throws FogException
    {
        return MaterialDAO.getMaterials();
    }
    
    public static boolean createMaterial(int materialeTypeId, String name, int length, String unit) throws FogException
    {
        return MaterialDAO.createMaterial(materialeTypeId, name, length, unit);
    }
    
    public static List<RooftypeDTO> getRooftypes() throws FogException
    {
        return RooftypeDAO.getRooftypes();
    }
}
