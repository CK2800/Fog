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
    public static List<ForesporgselDTO> getRequests() throws FogException
    {
        return ForesporgselDAO.getForesporgsel();
    }
    
    public static ForesporgselDTO getRequest(int id) throws FogException
    {
        return ForesporgselDAO.getForesporgselSingle(id);
    }
    
    public static MaterialeDTO getMateriale(int id) throws FogException
    {
        return MaterialeDAO.getSingleMateriale(id);        
    }
    
    public static List<MaterialeDTO> getMaterialer() throws FogException
    {
        return MaterialeDAO.getMaterialer();
    }
    
    // her skal der være nogle kald til materiale dao -> create metoder også. 
}
