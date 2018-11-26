/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.MaterialeDTO;
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
    public static List<CarportRequestDTO> getCarPorts() throws FogException
    {
        return CarportDAO.getCarportRequests();
    }
    
    public static CarportRequestDTO getCarPort(int id) throws FogException
    {
        return CarportDAO.getCarportRequest(id);
    }
    
    public static MaterialeDTO getMaterial(int id) throws FogException
    {
        return MaterialDAO.getSingleMaterial(id);        
    }
    
    public static List<MaterialeDTO> getMaterials() throws FogException
    {
        return MaterialDAO.getMaterials();
    }
    
    public static boolean createMaterial(int materialeTypeId, String navn, int laengde, String enhed) throws FogException
    {
        return MaterialDAO.createMaterial(materialeTypeId, navn, laengde, enhed);
    }
    
    public static List<RooftypeDTO> getRooftypes() throws FogException
    {
        return RooftypeDAO.getRooftypes();
    }
}
