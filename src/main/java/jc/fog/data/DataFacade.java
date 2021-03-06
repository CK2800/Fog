/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.dto.CarportRequestDTO;
import jc.fog.logic.dto.MaterialDTO;
import jc.fog.logic.dto.RooftypeDTO;
import jc.fog.logic.dto.UsersDTO;
import jc.fog.logic.dto.ZipcodeDTO;

/**
 *
 * @author Claus
 */
public interface DataFacade
{
    public List<CarportRequestDTO> getCarports() throws FogException;
    public CarportRequestDTO getCarport(int id) throws FogException;
    public int createUser(String email, String password, String name, int phone, int zipcode) throws FogException;
    public MaterialDTO getMaterial(int id) throws FogException;
    public List<MaterialDTO> getMaterials() throws FogException;
    public boolean createMaterial(int materialeTypeId, String name, int length, String unit, float price) throws FogException;
    public List<RooftypeDTO> getRooftypes() throws FogException;
    public boolean updateRequest(int id, int shedId, String shedCheck, int slope, int width, int length, int shedWidth, int shedLength, int rooftypeId, String remark) throws FogException;
    public int createCarPort(int rooftypeId, int slope, int width, int length, int height, int shedWidth, int shedLength, String remark) throws FogException;
    public List<ZipcodeDTO> getZipcodes() throws FogException;
    public UsersDTO login(String email, String password) throws FogException;
    public boolean forgotPassword(String email, String password) throws FogException;
    public boolean setNewRankUser(int id, int rank) throws FogException;
    public List<UsersDTO> getAllUsers() throws FogException;
    public boolean deleteUser(int id) throws FogException;
}