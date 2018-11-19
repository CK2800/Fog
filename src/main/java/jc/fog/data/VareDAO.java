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

/**
 *
 * @author Claus
 */
public class VareDAO
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
    
<<<<<<< HEAD
    public static final String GET_ALL_PRODUCT_SQL = "SELECT ";
    
    public static List<VareDTO> VarerTilBeregning() throws FogException
=======
    public static List<MaterialeDTO> materialerTilBeregning() throws FogException
>>>>>>> FeatureClaus
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
            PreparedStatement pstm = connection.prepareStatement(GET_VARER_TIL_BEREGNING_SQL);
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
            rs.getInt("varetypeId"), 
            rs.getString("navn"), 
            rs.getInt("laengde"),
            rs.getString("enhed")             
        );
    }
    
    public static VareDTO getAllProducts() throws FogException
    {
        try
        {
                        
        }
        catch(Exception e)
        {
            throw new FogException("Fik desværre lavet fejl ved at hente alle vare. - ", e.getMessage());
        }
        return null;
    }
}
