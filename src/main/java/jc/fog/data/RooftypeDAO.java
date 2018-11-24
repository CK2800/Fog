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
import jc.fog.logic.MaterialeDTO;
import jc.fog.logic.RooftypeDTO;

/**
 *
 * @author Claus
 */
public class RooftypeDAO
{
    private static Connection connection;
    
    /**
     * Henter tupler i Rooftype og relateret data fra Materiale og Materialetype.
     * Tupler sorteres på Rooftype.id så ResultSet kan gennemløbes og liste af
     * MaterialDTO objekter dannes til hvert RooftypeDTO objekt.
     */
    private static final String GET_ROOFTYPES_SQL = "SELECT rt.id, rt.type, m.id as materialeId, m.materialetypeId, m.navn, m.laengde, m.enhed, mt.type as materialeType " +
                                                    "FROM Rooftype rt INNER JOIN RooftypeMaterial rm ON rt.id = rm.rooftypeId " + 
                                                    "INNER JOIN Materiale m ON rm.materialId = m.id " + 
                                                    "INNER JOIN Materialetype mt ON m.materialetypeId = mt.id " +
                                                    "ORDER BY rt.id ASC"; 
    
    /**
     * Henter tagtyper fra databasen.
     * @return 
     */
    protected static List<RooftypeDTO> getRooftypes() throws FogException
    {
        try
        {
            connection = DbConnection.getConnection();
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
    private static List<RooftypeDTO> mapRooftypes(ResultSet rs) throws SQLException
    {
        RooftypeDTO rooftype = null;
        List<RooftypeDTO> rooftypes = new ArrayList<>();
        List<MaterialeDTO> materials = null;
                
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
            materials.add(new MaterialeDTO(
                    rs.getInt("materialeId"), 
                    rs.getInt("materialetypeId"), 
                    rs.getString("navn"),
                    rs.getInt("laengde"),
                    rs.getString("enhed"),
                    rs.getString("materialeType")
            ));                
        }
        return rooftypes;                    
    }
}
