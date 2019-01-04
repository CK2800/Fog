/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic.dto;

/**
 *
 * @author Jespe
 */
public class ZipcodeDTO {
    private int zip;
    private String city;

    public ZipcodeDTO(int zip, String city) {
        this.zip = zip;
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    
    
}
