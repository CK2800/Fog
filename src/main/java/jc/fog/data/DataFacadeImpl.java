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
import jc.fog.logic.UsersDTO;
import jc.fog.logic.ZipcodeDTO;

/**
 * Facade to expose subsystem methods to clients.
 * 
 * @author Claus
 */
public class DataFacadeImpl implements DataFacade
{
    private CarportRequestDAO carportRequestDAO;
    private MaterialDAO materialDAO;
    private RooftypeDAO rooftypeDAO;
    private ZipcodeDAO zipcodeDAO;
    private UserDAO userDAO;
    
    
    public DataFacadeImpl(Connection connection) throws FogException
    {        
        carportRequestDAO = new CarportRequestDAO(connection);
        materialDAO = new MaterialDAO(connection);
        rooftypeDAO = new RooftypeDAO(connection);
        zipcodeDAO = new ZipcodeDAO(connection);
        userDAO = new UserDAO(connection);
    }
    public boolean deleteUser(int id)
    {
        // todo by jespe.
        throw new UnsupportedOperationException("kommer snart");
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
    
    public boolean updateRequest(int id, int shedId, String shedCheck, int slope, int width, int length, int shedWidth, int shedLength, int rooftypeId, String remark) throws FogException
    {
        return carportRequestDAO.updateCarPortRequest(id, shedId, shedCheck, slope, width, length, shedWidth, shedLength, rooftypeId, remark);
    }
    
    public boolean createCarPort(int rooftypeId, int slope, int width, int length, int height, int shedWidth, int shedLength, String remark) throws FogException
    {
        return carportRequestDAO.createCarportRequest(rooftypeId, slope, width, height, length, shedWidth, shedLength, remark);
    }
    
    public int createUser(String email, String password, String name, int phone, int zipcode) throws FogException
    {
        return userDAO.createUser(email, password, name, phone, zipcode);
    }
    
    public UsersDTO login(String email, String password) throws FogException
    {
        return userDAO.login(email, password);
    }
    
    public boolean forgotPassword(String email) throws FogException
    {
        return userDAO.forgotPassword(email);
    }
    
    public List<ZipcodeDTO> getZipcodes() throws FogException
    {
        return zipcodeDAO.getZipcodes();
    }
    
//    public List<UsersDTO> getAllUsers() throws FogException
//    {
//        return userDAO.
//    }
}
