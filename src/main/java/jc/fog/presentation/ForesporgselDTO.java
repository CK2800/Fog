package jc.fog.presentation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jesper
 */
public class ForesporgselDTO {
    private int Id, tagId, skurId, bredde, hoejde, laengde, dimensionerId;
    private String bemaerkning;

    public ForesporgselDTO(int Id, int tagId, int skurId, int bredde, int hoejde, int laengde, String bemaerkning) {
        this.Id = Id;
        this.tagId = tagId;
        this.skurId = skurId;
        this.bredde = bredde;
        this.hoejde = hoejde;
        this.laengde = laengde;
        this.bemaerkning = bemaerkning;
    }
    
    
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getSkurId() {
        return skurId;
    }

    public void setSkurId(int skurId) {
        this.skurId = skurId;
    }

    public int getBredde() {
        return bredde;
    }

    public void setBredde(int bredde) {
        this.bredde = bredde;
    }

    public int getHoejde() {
        return hoejde;
    }

    public void setHoejde(int hoejde) {
        this.hoejde = hoejde;
    }

    public int getLaengde() {
        return laengde;
    }

    public void setLaengde(int laengde) {
        this.laengde = laengde;
    }

    public int getDimensionerId() {
        return dimensionerId;
    }

    public void setDimensionerId(int dimensionerId) {
        this.dimensionerId = dimensionerId;
    }

    public String getBemaerkning() {
        return bemaerkning;
    }

    public void setBemaerkning(String bemaerkning) {
        this.bemaerkning = bemaerkning;
    }
    


    
    
    
}
