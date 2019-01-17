/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

import jc.fog.exceptions.FogException;
import jc.fog.logic.dto.MaterialDTO;
import jc.fog.logic.Rules.CarportPart;

/**
 * A pure fabrication of a collection of data comprising a line in the bill of goods.
 * @author Claus
 */
public class BillItem
{
    private MaterialDTO material;
    private int count;
    private String remarks;
    /** Indikerer hvilken del af carporten, dette bill item bruges til. */
    private CarportPart carportPart;
        
    
    // Getters
    /** Materialet der skal bruges. */
    public MaterialDTO getMaterialDTO() { return material; }
    /** Antal af materialet, der skal bruges. */
    public int getCount() { return count; }
    /** Indikerer hvilken del af carporten, dette bill item bruges til. */
    public CarportPart getCarportPart() { return carportPart; }
    /** Instruks for bygning af denne del af carporten. */
    public String getRemarks() { return remarks; }
    
    /**
     * Angiver hvilket materiale og hvor meget samt til hvilken del på carporten.
     * @param material
     * @param count
     * @param carportPart
     * @param remarks 
     * @throws FogException Hvis material er null, er materialet ikke fundet, derfor ikke en gyldig BillItem.
     */
    public BillItem(MaterialDTO material, int count, CarportPart carportPart, String remarks) throws FogException
    {
        // material kan ikke være null.
        if (material == null)
            throw new FogException("Materialet blev ikke fundet.", "BillItem fik MaterialDTO null pointer i konstruktøren.", null);
        this.material = material;
        this.count = count;
        this.carportPart = carportPart;
        this.remarks = remarks;
    }    
}
