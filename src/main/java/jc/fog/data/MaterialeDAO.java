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
public class MaterialeDAO
{
    private static Connection connection;
    /**
     * Sql som henter alle de materialer i varetabellen der har dimensioner.
     * Således skelnes mellem materialer der indgår i carport beregningen og øvrige materialer.
 Sortering sker på Vare.id ASC, Dimensioner.laengde ASC.
     */
    public static final String GET_VARER_TIL_BEREGNING_SQL = "SELECT v.id, v.varetypeId, v.navn, v.hjaelpetekst, v.pris, " + 
                                                            "d.laengde, d.id AS dimensionId, vt.type " +
                                                            "FROM Vare v INNER JOIN Varetype vt ON v.varetypeId = vt.id " +
                                                            "INNER JOIN VareDimensioner vd ON v.id = vd.vareId " +
                                                            "INNER JOIN Dimensioner d ON vd.dimensionerId = d.id    " +
                                                            "ORDER BY v.id ASC, laengde ASC;";
    

    public static final String GET_MATERIALER_SQL = "SELECT m.id, materialetypeId, m.navn, mt.type, laengde, enhed " +
                                                                 "FROM Materiale m INNER JOIN Materialetype mt ON m.materialetypeId = mt.id";
        
    public static final String GET_CREATE_MATERIALE_SQL = "INSERT INTO Materiale(materialeTypeId, navn, laengde, enhed) VALUES (?, ?, ?, ?)";
    
    public static final String GET_MATERIALE_SQL = GET_MATERIALER_SQL + " WHERE m.id = ?";

    
    public static List<MaterialeDTO> getMaterialer() throws FogException
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
            PreparedStatement pstm = connection.prepareStatement(GET_MATERIALER_SQL);
            ArrayList<MaterialeDTO> materialer = new ArrayList<MaterialeDTO>();
            MaterialeDTO vareDTO = null;
            // try with ressources
            try(ResultSet rs = pstm.executeQuery())
            {
                while(rs.next())
                {                    
                    materialer.add(mapMateriale(rs));
                }                
            }
            return materialer;
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
    private static MaterialeDTO mapMateriale(ResultSet rs) throws SQLException
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
    
//    public static VareDTO getAllProducts() throws FogException
//    {
//        try
//        {
//                        
//        }
//        catch(Exception e)
//        {
//            throw new FogException("Fik desværre lavet fejl ved at hente alle vare. - ", e.getMessage());
//        }
//        return null;
//    }
    
    /**
     * Den henter alt i databasen hvor alt sammen.
     * @return Alle de "Materiale" der findes i db.
     * @throws FogException 
     */
//    public static ArrayList<MaterialeDTO> getMaterialer() throws FogException
//    {
//        ArrayList<MaterialeDTO> materialer = new ArrayList<MaterialeDTO>();
//        try
//        {
//            //laver connection
//            connection = DbConnection.getConnection();
//            
//            //Forsøg at hente forespørgsel ud fra Sql'en
//            PreparedStatement pstm = connection.prepareStatement(GET_MATERIALER_SQL);
//            
//            try(ResultSet rs = pstm.executeQuery())
//            {
//                while(rs.next())//Løber alle igennem
//                {
//                    materialer.add(new MaterialeDTO(
//                            rs.getInt("id"),
//                            rs.getInt("materialetypeId"),
//                            rs.getString("navn"),
//                            rs.getInt("laengde"),
//                            rs.getString("enhed"),
//                            rs.getString("materialetype")
//                    ));
//                }
//            }
//        }
//        catch(Exception e)
//        {
//            throw new FogException("Systemet kan ikke finde varer.", e.getMessage());
//        }
//        return materialer;
//    }
    
    public static boolean createMateriale(int materialeTypeId, String navn, int laengde, String enhed) throws FogException
    {
        //Den "space removed" i siderne
        navn = navn.trim();
        enhed = enhed.trim();
        
        try
        {
            //laver connection
            connection = DbConnection.getConnection();
            
            //Forsøg at hente forespørgsel ud fra Sql'en
            PreparedStatement pstm = connection.prepareStatement(GET_CREATE_MATERIALE_SQL, Statement.RETURN_GENERATED_KEYS);
            
            pstm.setInt(1, materialeTypeId);
            pstm.setString(2, navn);
            pstm.setInt(3, laengde);
            pstm.setString(4, enhed);
            
            return pstm.executeUpdate() == 1;
        }
        catch(Exception e)
        {
            throw new FogException("Forespørgsel blev ikke gemt.", e.getMessage());
        }
    }
    
    public static MaterialeDTO getSingleMateriale(int getMaterialeId) throws FogException
    {
        MaterialeDTO materiale = null;
        try
        {
            connection = DbConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement(GET_MATERIALE_SQL);
            pstm.setInt(1, getMaterialeId);

            //try with ressources.
            try(ResultSet rs = pstm.executeQuery())
            {
                if(rs.next())
                {
                    materiale = mapMateriale(rs);                                        
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
