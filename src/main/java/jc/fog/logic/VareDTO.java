/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Claus
 */
public class VareDTO
{
    private int id;    
    private int varetypeId;
    private String navn;
    /**
     * Tekst som vises i styklisten til 
     * hjælp for kunden v. konstruktion mv.
     */
    private String hjaelpetekst;    
    private float pris; // decimal (6,2) i db.
    private List<DimensionerDTO> dimensioner;
    
    /**
     * Konstruktør hvor alle argumenter kendes.
     * @param id 
     * @param navn 
     * @param hjaelpetekst
     * @param pris 
     */
    public VareDTO(int id, int varetypeId, String navn, String hjaelpetekst, float pris)
    {
        this.id = id;
        this.varetypeId = varetypeId;
        this.navn = navn;
        this.hjaelpetekst = hjaelpetekst;
        this.pris = pris;
    }
    
    // getters
    public int getId() { return id; }
    public int getVaretypeId() { return varetypeId; }
    public String getNavn() { return navn; }
    public String getHjaelpetekst() { return hjaelpetekst; }
    public float getPris() { return pris; }
    public List<DimensionerDTO> getDimensioner() { return dimensioner; }
    
    // setters
    public void setId(int value) { id = value; }
    public void setVaretypeId(int value) { varetypeId = value; }
    public void setNavn(String value) { navn = value; }
    public void setHjaelpetekst(String value) { hjaelpetekst = value; }
    public void setPris(float value) { pris = value; }
    public void setDimensioner(List<DimensionerDTO> value) { dimensioner = value; }
}
