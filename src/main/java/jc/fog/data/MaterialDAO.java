/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.exceptions.RecordNotFoundException;
import jc.fog.exceptions.RecordNotFoundException.Table;
import jc.fog.logic.MaterialDTO;

/**
 *
 * @author Claus
 */
public class MaterialDAO extends AbstractDAO
{      
    /**
     * SQL som henter materialer, deres materialetype og evt. tagtype.
     */
    public static final String GET_MATERIALS_SQL = "SELECT m.id, m.materialtypeId, m.name, mt.type, m.length, m.unit, m.price, rm.rooftypeId " +
                                                                 "FROM Materials m INNER JOIN Materialtypes mt ON m.materialtypeId = mt.id LEFT JOIN RooftypeMaterials rm ON m.id = rm.materialId";
        
    /**
     * SQL som opretter materiale i databasen.
     */
    public static final String CREATE_MATERIAL_SQL = "INSERT INTO Materials(materialtypeId, name, length, unit, price) VALUES (?, ?, ?, ?, ?)";
    
    
    /**
     * SQL som henter et enkelt materiale baseret på id.
     */
    public static final String GET_MATERIAL_SQL = GET_MATERIALS_SQL + " WHERE m.id = ?";

    /**
     * Konstruktør som kræver et Connection objekt.
     * Connection objektet gemmes i AbstractDAO.
     * @param connection     
     */
    public MaterialDAO(Connection connection)
    {
       super(connection);
    }
    
    /**
     * Henter alle materialer.
     * @return List af MaterialDTO objekter. Hvis ingen materialer findes, returneres tom liste.     
     * @throws jc.fog.exceptions.FogException     
     */
    public List<MaterialDTO> getMaterials() throws FogException
    {        
        List<MaterialDTO> materials = new ArrayList();
        try(PreparedStatement pstm = connection.prepareStatement(GET_MATERIALS_SQL);
            ResultSet rs = pstm.executeQuery();
        ) 
        {
            materials = mapMaterials(rs);            
        }
        catch(Exception e)
        {
            throw new FogException("Der er sket en fejl og materialerne kunne ikke hentes.", e.getMessage(), e);
        }
                
        return materials;
    }
    
    /**
     * Mapper værdier fra ResultSet tuple til MaterialDTO.
     * @param rs ResultSet med tuple.
     * @return MaterialDTO
     * @throws SQLException 
     */
    private MaterialDTO mapMaterial(ResultSet rs) throws SQLException
    {        
        return new MaterialDTO
        (
            rs.getInt("id"), 
            rs.getInt("materialtypeId"), 
            rs.getString("name"), 
            rs.getInt("length"),
            rs.getString("unit"),
            rs.getString("type"),
            rs.getInt("rooftypeId"),
            rs.getFloat("price")
        );
    }
    
    /**
     * Henter værdier fra ResultSet tuples til liste af MaterialDTO.
     * PRE: ResultSet rs er åbent.     
     * @param rs
     * @return
     * @throws SQLException 
     */
    private List<MaterialDTO> mapMaterials(ResultSet rs) throws SQLException
    {
        List<MaterialDTO> materials = new ArrayList<MaterialDTO>();
        
        while(rs.next())
        {
            materials.add(mapMaterial(rs));
        }
        
        if (materials.isEmpty())
            return null;
        return materials;
    }
    
    /**
     * Opretter et materiale i databasen.
     * @param materialtypeId id for materialets type.
     * @param name materialets navn.
     * @param length
     * @param unit stk, mtr, kg osv.
     * @param price enhedspris.
     * @return true hvis oprettelse lykkedes, ellers false.
     * @throws jc.fog.exceptions.FogException     
     */
    public boolean createMaterial(int materialtypeId, String name, int length, String unit, float price) throws FogException
    {
        // Fjern leading og trailing white space.
        name = name.trim();
        unit = unit.trim();
        
        boolean success = false;
        try(PreparedStatement pstm = connection.prepareStatement(CREATE_MATERIAL_SQL, Statement.RETURN_GENERATED_KEYS);)
        {            
            pstm.setInt(1, materialtypeId);
            pstm.setString(2, name);
            pstm.setInt(3, length);
            pstm.setString(4, unit);
            pstm.setFloat(5, price);
            
            success = pstm.executeUpdate() == 1;            
        }
        catch(SQLException s)
        {
            if (!success)
                throw new FogException("Materialet blev ikke oprettet.", s.getMessage(), s);
        }
        
        return success;
    }
    
    
    /**
     * Henter materiale med det ønskede id.
     * @param id
     * @return - Returnerer MaterialDTO objekt hvis det findes, ellers null.
     * @throws FogException 
     */
    public MaterialDTO getMaterial(int id) throws FogException
    {
        MaterialDTO material = null;
        
        try(PreparedStatement pstm = connection.prepareStatement(GET_MATERIAL_SQL);)
        {
            pstm.setInt(1, id);

            //try with ressources.
            try(ResultSet rs = pstm.executeQuery()) // rs lukkes automatisk, da ResultSet implementerer AutoCloseable.
            {
                if(rs.next())
                {
                    material = mapMaterial(rs);                                        
                }
                else
                {
                    
                    throw new RecordNotFoundException(Table.SHEDS, "id", id);
                }
            }
        }
        
        catch(Exception e)
        {
            throw new FogException("Systemet kan ikke finde det ønskede materiale.", e.getMessage(), e);
        }
        
        return material;
    }
}
