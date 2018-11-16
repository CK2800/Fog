/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import jc.fog.logic.VareDTO;

/**
 *
 * @author Claus
 */
public class VareDAO
{
    /**
     * Sql som henter alle de varer i varetabellen der har dimensioner.
     * Således skelnes mellem varer der indgår i carport beregningen og øvrige varer.
     * Sortering sker på Vare.id ASC, Dimensioner.laengde ASC.
     */
    public static final String GET_VARER_TIL_BEREGNING_SQL = "SELECT v.id, v.varetypeId, v.navn, v.hjaelpetekst, v.pris, " + 
                                                            "d.laengde, vt.type " +
                                                            "FROM Vare v INNER JOIN Varetype vt ON v.varetypeId = vt.id " +
                                                            "INNER JOIN VareDimensioner vd ON v.id = vd.vareId " +
                                                            "INNER JOIN Dimensioner d ON vd.dimensionerId = d.id" +
                                                            "ORDER BY v.id ASC, laengde ASC;";
    
    public static List<VareDTO> VarerTilBeregning()
    {
        /*
        Pseudo:
        Hent varer og deres dimensioner
        Hent første vare fra recordset
        Sålænge vareid er ens, tilføj til dimensioner
        */ 
        return null;
    }
    
    /**
     * Mapper værdier fra ResultSet tuple til VareDTO.
     * @param rs ResultSet med tuple.
     * @return VareDTO
     * @throws SQLException 
     */
    private static VareDTO mapVare(ResultSet rs) throws SQLException
    {        
        return new VareDTO
        (
            rs.getInt("id"), 
            rs.getInt("varetypeId"), 
            rs.getString("navn"), 
            rs.getString("hjaelpetekst"), 
            rs.getFloat("pris")
        );
    }
}
