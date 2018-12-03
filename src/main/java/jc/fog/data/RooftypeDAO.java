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
import java.util.ArrayList;
import java.util.List;
import jc.fog.exceptions.FogException;
import jc.fog.logic.MaterialDTO;
import jc.fog.logic.RooftypeDTO;

/**
 *
 * @author Claus
 */
public class RooftypeDAO extends AbstractDAO
{   
    /**
     * Henter tupler i Rooftype og relateret data fra Materiale og Materialetype.
     * Tupler sorteres på Rooftype.id så ResultSet kan gennemløbes og liste af
     * MaterialDTO objekter dannes til hvert RooftypeDTO objekt.
     */
    private static final String GET_ROOFTYPES_SQL = "SELECT rt.id, rt.type, m.id as materialId, m.materialtypeId, m.name, m.length, m.unit, m.price, mt.type as materialType " +
                                                    "FROM Rooftypes rt INNER JOIN RooftypeMaterials rm ON rt.id = rm.rooftypeId " + 
                                                    "INNER JOIN Materials m ON rm.materialId = m.id " + 
                                                    "INNER JOIN Materialtypes mt ON m.materialtypeId = mt.id " +
                                                    "ORDER BY rt.id ASC"; 
    
    public RooftypeDAO(Connection connection) throws FogException
    {
        super(connection);        
    }
    
    /**
     * Henter tagtyper fra databasen.
     * @return 
     */
    protected List<RooftypeDTO> getRooftypes() throws FogException
    {
        try
        {            
            List<RooftypeDTO> rooftypes = new ArrayList<RooftypeDTO>();
            // Opret sql og kør.
            PreparedStatement pstm = connection.prepareStatement(GET_ROOFTYPES_SQL);
            try(ResultSet rs = pstm.executeQuery())
            {
                rooftypes = mapRooftypes(rs);                
            }
            return rooftypes;
        }
        catch(Exception e)
        {
            throw new FogException("Systemet kan ikke finde tagtyper.", e.getMessage());
        }
    }
    
    /**
     * Mapper fra ResultSet til List af RooftypeDTO objekter.
     * Da metoden er privat, lader vi kaldende metode fange evt. SQLException.
     * @param rs
     * @return
     * @throws SQLException 
     */
    private List<RooftypeDTO> mapRooftypes(ResultSet rs) throws SQLException
    {
        RooftypeDTO rooftype = null;
        List<RooftypeDTO> rooftypes = new ArrayList<>();
        List<MaterialDTO> materials = null;
                
        while(rs.next())
        {
            // Opret rooftype hvis krævet, start med en tom liste af materialer.
            if (rooftype == null || rooftype.getId() != rs.getInt("id"))
            {
                materials = new ArrayList<>();
                rooftype = new RooftypeDTO(rs.getInt("id"), rs.getString("type"), materials);                    
                rooftypes.add(rooftype);
            }
            // Tilføj materiale til listen i den aktuelle rooftype.
            materials.add(new MaterialDTO(
                    rs.getInt("materialId"), 
                    rs.getInt("materialtypeId"), 
                    rs.getString("name"),
                    rs.getInt("length"),
                    rs.getString("unit"),
                    rs.getString("materialType"),
                    rs.getInt("id"), // rooftype id.
                    rs.getFloat("price")
            ));                
        }
        return rooftypes;                    
    }
}
