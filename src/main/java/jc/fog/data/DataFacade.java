/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;

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
}