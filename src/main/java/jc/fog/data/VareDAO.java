/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.data;

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
        
    }
}
