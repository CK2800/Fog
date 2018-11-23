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
import jc.fog.logic.MaterialeDTO;

/**
 *
 * @author Claus
 */
public class MaterialDAO
{
    private static Connection connection;
    /**
     * Sql som henter alle de materialer i varetabellen der har dimensioner.
     * Således skelnes mellem materialer der indgår i carport beregningen og øvrige materialer.
     * Sortering sker på Vare.id ASC, Dimensioner.laengde ASC.
     */
//    public static final String GET_PRODUCT_TO_CALCULATION_SQL = "SELECT v.id, v.varetypeId, v.navn, v.hjaelpetekst, v.pris, " + 
//                                                            "d.laengde, d.id AS dimensionId, vt.type " +
//                                                            "FROM Vare v INNER JOIN Varetype vt ON v.varetypeId = vt.id " +
//                                                            "INNER JOIN VareDimensioner vd ON v.id = vd.vareId " +
//                                                            "INNER JOIN Dimensioner d ON vd.dimensionerId = d.id    " +
//                                                            "ORDER BY v.id ASC, laengde ASC;";

    //det er for, at fremvise de enkelt værdier.
    
    /**
     * SQL som henter materialer og deres materialetyper.
     */
    public static final String GET_MATERIALS_SQL = "SELECT m.id, materialetypeId, m.navn, mt.type, laengde, enhed " +
                                                                 "FROM Materiale m INNER JOIN Materialetype mt ON m.materialetypeId = mt.id";
    
    //Det er til, at opret i databasen.
    
    /**
     * SQL som opretter materiale i databasen.
     */
    public static final String CREATE_MATERIAL_SQL = "INSERT INTO Materiale(materialeTypeId, navn, laengde, enhed) VALUES (?, ?, ?, ?)";
    
    //Bruger GET_VARER_TIL_BEREGNING_SQL så der fremkommer de rigtig værdier.
    
    /**
     * SQL som henter et enkelt materiale baseret på id.
     */
    public static final String GET_MATERIAL_SQL = GET_MATERIALS_SQL + " WHERE m.id = ?";

    /**
     * Skal træk alt fra databasen ud i listen så det giver bruger/FOG mulighed for, at se materialer.
     * @return
     * @throws FogException 
     */
    public static List<MaterialeDTO> getMaterials() throws FogException
    {
        /*
        Pseudo:
        Hent materialer og deres dimensioner
        Hent første vare fra recordset
        Sålænge vareid er ens, tilføj til dimensioner
        */ 
        try
        {
            connection = DbConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement(GET_MATERIALS_SQL);
            ArrayList<MaterialeDTO> materials = new ArrayList<MaterialeDTO>();
            MaterialeDTO vareDTO = null;//Skal vi ikke bare slet det her?
            
            // try with ressources
            try(ResultSet rs = pstm.executeQuery())
            {
                while(rs.next())
                {                    
                    materials.add(mapMaterial(rs));
                }                
            }
            return materials;
        }
        catch(Exception e)
        {
            throw new FogException("Systemet kan ikke finde varer til styklisteberegning.", e.getMessage());
        }
    }
    
    /**
     * Mapper værdier fra ResultSet tuple til MaterialeDTO.
     * @param rs ResultSet med tuple.
     * @return MaterialeDTO
     * @throws SQLException 
     */
    private static MaterialeDTO mapMaterial(ResultSet rs) throws SQLException
    {        
        return new MaterialeDTO
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
     * Skal opret Materiale med navn, længde, enhed og hvilke materialType man har valgt.
     * @param materialTypeId
     * @param name
     * @param length
     * @param unit
     * @return - Skal bare fortælle om man har opret eller ej?
     * @throws FogException 
     */
    public static boolean createMaterial(int materialTypeId, String name, int length, String unit) throws FogException
    {
        //Den "space removed" i siderne
        name = name.trim();
        unit = unit.trim();
        
        try
        {
            //laver connection
            connection = DbConnection.getConnection();
            
            //Forsøg at hente forespørgsel ud fra Sql'en
            PreparedStatement pstm = connection.prepareStatement(CREATE_MATERIAL_SQL, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, materialTypeId);
            pstm.setString(2, name);
            pstm.setInt(3, length);
            pstm.setString(4, unit);
            
            return pstm.executeUpdate() == 1;
        }
        catch(Exception e)
        {
            throw new FogException("Forespørgsel blev ikke gemt.", e.getMessage());
        }
    }
    
    
    /**
     * Skal hente dens angivet indhold ud fra det som passer med getMaterialId.
     * @param getMaterialId
     * @return - Skal sende dens indhold tilbage. Så det er muligt at arbejde med det.
     * @throws FogException 
     */
    public static MaterialeDTO getSingleMaterial(int getMaterialId) throws FogException
    {
        MaterialeDTO materiale = null;
        try
        {
            connection = DbConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement(GET_MATERIAL_SQL);
            pstm.setInt(1, getMaterialId);

            //try with ressources.
            try(ResultSet rs = pstm.executeQuery())
            {
                if(rs.next())
                {
                    materiale = mapMaterial(rs);                                        
                }
            }
        }
        catch(Exception e)
        {
            throw new FogException("Systemet kan ikke finde den enkelte varer.", e.getMessage());
        }
        return materiale;
    }
}