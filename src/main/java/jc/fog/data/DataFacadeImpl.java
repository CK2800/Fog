/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import jc.fog.data.dao.MaterialDAO;
import jc.fog.data.dao.UserDAO;
import jc.fog.data.dao.RooftypeDAO;
import jc.fog.data.dao.CarportRequestDAO;
import jc.fog.data.dao.ZipcodeDAO;
import java.sql.Connection;
import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.dto.CarportRequestDTO;
import jc.fog.logic.dto.MaterialDTO;
import jc.fog.logic.dto.RooftypeDTO;
import jc.fog.logic.dto.UsersDTO;
import jc.fog.logic.dto.ZipcodeDTO;

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

    /**
     * 
     * Henter alle carport forespørgsler.
     * @return - List af CarportRequestDTO objekter. Fandtes ingen forespørgsler, returneres tom liste.
     * @throws jc.fog.exceptions.FogException     
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
    
    @Override
    public CarportRequestDTO getCarport(int id) throws FogException
    {
        return carportRequestDAO.getCarportRequest(id);
    }
    
    @Override
    public MaterialDTO getMaterial(int id) throws FogException
    {
        return materialDAO.getMaterial(id);        
    }
    
    /**
     * Henter alle materialer.
     * @return List af MaterialDTO objekter. Hvis ingen materialer findes, returneres tom liste.     
     * @throws jc.fog.exceptions.FogException     
     */
    @Override
    public List<MaterialDTO> getMaterials() throws FogException
    {
        return materialDAO.getMaterials();
    }
    
    /**
     * Opretter et materiale i databasen.
     * @param materialeTypeId id for materialets type.
     * @param name materialets navn.
     * @param length
     * @param unit stk, mtr, kg osv.
     * @param price enhedspris.
     * @return true hvis oprettelse lykkedes, ellers false.
     * @throws jc.fog.exceptions.FogException     
     */
    @Override
    public boolean createMaterial(int materialeTypeId, String name, int length, String unit, float price) throws FogException
    {
        return materialDAO.createMaterial(materialeTypeId, name, length, unit, price);
    }
    
    /**
     * Henter tagtyper fra databasen.
     * Findes ingen tagtyper, returneres en tom liste.
     * @return 
     */
    @Override
    public List<RooftypeDTO> getRooftypes() throws FogException
    {
        return rooftypeDAO.getRooftypes();
    }
    
    @Override
    public boolean updateRequest(int id, int shedId, String shedCheck, int slope, int width, int length, int shedWidth, int shedLength, int rooftypeId, String remark) throws FogException
    {
        return carportRequestDAO.updateCarPortRequestAndShed(id, shedId, shedCheck, slope, width, length, shedWidth, shedLength, rooftypeId, remark);
    }
    
    @Override
    public int createCarPort(int rooftypeId, int slope, int width, int length, int height, int shedWidth, int shedLength, String remark) throws FogException
    {
        return carportRequestDAO.createCarportRequestAndShed(rooftypeId, slope, width, height, length, shedWidth, shedLength, remark);
    }
    
    @Override
    public int createUser(String email, String password, String name, int phone, int zipcode) throws FogException
    {
        return userDAO.createUser(email, password, name, phone, zipcode);
    }
    
    @Override
    public UsersDTO login(String email, String password) throws FogException
    {
        return userDAO.login(email, password);
    }

    public boolean deleteUser(int id) throws FogException
    {
        return userDAO.deleteUser(id);
    }
    
    public boolean setNewRankUser(int id, int rank) throws FogException
    {
        return userDAO.setNewRankUser(id, rank);
    }
    
    public List<UsersDTO> getAllUsers() throws FogException
    {
        return userDAO.getAllUsers();
    }
    
    public List<ZipcodeDTO> getZipcodes() throws FogException
    {
        return zipcodeDAO.getZipcodes();
    }

    
    @Override    
    public boolean forgotPassword(String email, String password) throws FogException {
        return userDAO.forgotPassword(email, password);
    }
    
    
}
