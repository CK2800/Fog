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
import jc.fog.logic.MaterialDTO;

/**
 *
 * @author Claus
 */
public class MaterialDAO extends AbstractDAO
{      
    /**
     * SQL som henter materialer og deres materialetyper.
     */
    public static final String GET_MATERIALS_SQL = "SELECT m.id, materialetypeId, m.navn, mt.type, laengde, enhed " +
                                                                 "FROM Materiale m INNER JOIN Materialetype mt ON m.materialetypeId = mt.id";
    
    //Det er til, at opret i databasen.
    
    /**
     * SQL som opretter materiale i databasen.
     */
    public static final String CREATE_MATERIAL_SQL = "INSERT INTO Materiale(materialetypeId, navn, laengde, enhed) VALUES (?, ?, ?, ?)";
    
    //Bruger GET_VARER_TIL_BEREGNING_SQL så der fremkommer de rigtig værdier.
    
    
    /**
     * SQL som henter et enkelt materiale baseret på id.
     */
    public static final String GET_MATERIAL_SQL = GET_MATERIALS_SQL + " WHERE m.id = ?";

    /**
     * Konstruktør som kræver et Connection objekt.
     * Connection objektet gemmes i AbstractDAO.
     * @param connection
     * @throws FogException 
     */
    public MaterialDAO(Connection connection) throws FogException
    {
       super(connection);
    }
    
    /**
     * Skal træk alt fra databasen ud i listen så det giver bruger/FOG mulighed for, at se materialer.
     * @return
     * @throws FogException 
     */
    public List<MaterialDTO> getMaterials() throws FogException
    {
        /*
        Pseudo:
        Hent materialer og deres dimensioner
        Hent første vare fra recordset
        Sålænge vareid er ens, tilføj til dimensioner
        */ 
        try
        {
            PreparedStatement pstm = connection.prepareStatement(GET_MATERIALS_SQL);
            ResultSet rs = pstm.executeQuery();
            
            return mapMaterials(rs);
        }
        catch(Exception e)
        {
            throw new FogException("Systemet kan ikke finde materialer.", e.getMessage());
        }
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
            rs.getInt("materialetypeId"), 
            rs.getString("navn"), 
            rs.getInt("laengde"),
            rs.getString("enhed"),
            rs.getString("type")
        );
    }
    
    /**
     * Henter værdier fra ResultSet tuples til liste af MaterialDTO.
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
     * Skal opret Materiale med navn, længde, enhed og hvilke materialType man har valgt.
     * @param materialtypeId
     * @param name
     * @param length
     * @param unit
     * @return - Skal bare fortælle om man har opret eller ej?
     * @throws FogException 
     */
    public boolean createMaterial(int materialtypeId, String name, int length, String unit) throws FogException
    {
        //Den "space removed" i siderne
        name = name.trim();
        unit = unit.trim();
        
        try
        {            
            //Forsøg at hente forespørgsel ud fra Sql'en
            PreparedStatement pstm = connection.prepareStatement(CREATE_MATERIAL_SQL, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, materialtypeId);
            pstm.setString(2, name);
            pstm.setInt(3, length);
            pstm.setString(4, unit);
            
            return pstm.executeUpdate() == 1;
        }
        catch(Exception e)
        {
            throw new FogException("Materiale blev ikke oprettet.", e.getMessage());
        }
    }
    
    
    /**
     * Skal hente dens angivet indhold ud fra det som passer med getMaterialId.
     * @param materialId
     * @return - Skal sende dens indhold tilbage. Så det er muligt at arbejde med det.
     * @throws FogException 
     */
    public MaterialDTO getMaterial(int materialId) throws FogException
    {
        MaterialDTO material = null;
        try
        {
            PreparedStatement pstm = connection.prepareStatement(GET_MATERIAL_SQL);
            pstm.setInt(1, materialId);

            //try with ressources.
            try(ResultSet rs = pstm.executeQuery())
            {
                if(rs.next())
                {
                    material = mapMaterial(rs);                                        
                }
            }
        }
        catch(Exception e)
        {
            throw new FogException("Systemet kan ikke finde det ønskede materiale.", e.getMessage());
        }
        return material;
    }
}
