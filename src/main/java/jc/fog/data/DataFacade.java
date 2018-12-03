/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.sql.Connection;
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
    private CarportRequestDAO carportRequestDAO;
    private MaterialDAO materialDAO;
    private RooftypeDAO rooftypeDAO;
    
    public DataFacade(Connection connection) throws FogException
    {        
        carportRequestDAO = new CarportRequestDAO(connection);
        materialDAO = new MaterialDAO(connection);
        rooftypeDAO = new RooftypeDAO(connection);
    }
    /**
     * Get all requests (forespørgsler).
     * @return List of CarportRequestDTO objects.
     */
    public List<CarportRequestDTO> getCarports() throws FogException
    {
        try
        {
            return carportRequestDAO.getCarportRequests();
        }
        catch(FogException f)
        {
            throw f;
        }
    }
    
    public CarportRequestDTO getCarport(int id) throws FogException
    {
        return carportRequestDAO.getCarportRequest(id);
    }
    
    public MaterialDTO getMaterial(int id) throws FogException
    {
        return materialDAO.getMaterial(id);        
    }
    
    public List<MaterialDTO> getMaterials() throws FogException
    {
        return materialDAO.getMaterials();
    }
    
    public boolean createMaterial(int materialeTypeId, String name, int length, String unit, float price) throws FogException
    {
        return materialDAO.createMaterial(materialeTypeId, name, length, unit, price);
    }
    
    public List<RooftypeDTO> getRooftypes() throws FogException
    {
        return rooftypeDAO.getRooftypes();
    }
    
    public boolean updateCarPort(int id, int shedId, String shedCheck, int slope, int width, int length, int shedWidth, int shedLength, int rooftypeId, String remark) throws FogException
    {
        return carportRequestDAO.updateCarPortRequest(id, shedId, shedCheck, slope, width, length, shedWidth, shedLength, rooftypeId, remark);
    }
    
    public boolean createCarPort(int rooftypeId, int slope, int width, int length, int height, int shedWidth, int shedLength, String remark) throws FogException
    {
        return carportRequestDAO.createCarportRequest(rooftypeId, slope, width, height, length, shedWidth, shedLength, remark);
    }
}
