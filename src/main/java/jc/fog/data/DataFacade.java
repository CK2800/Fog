/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;
import jc.fog.logic.MaterialeDTO;

/**
 * Facade to expose subsystem methods to clients.
 * 
 * @author Claus
 */
public class DataFacade
{
    /**
     * Get all requests (forespørgsler).
     * @return List of ForesporgselDTO objects.
     */
    public static List<ForesporgselDTO> getCarPorts() throws FogException
    {
        return CarPortDAO.getCarPorts();
    }
    
    public static ForesporgselDTO getCarPort(int id) throws FogException
    {
        return CarPortDAO.getCarPortSingle(id);
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
}
