/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.logic;

/**
 *
 * @author Jespe
 */
public class UsersDTO {
    private int id, phone, rank, zip;
    private String name, email, password;

    
    /**
     * Konstruktør bruges til, at fremvis en list over dvs bruger oplysninger.
     * @param id
     * @param rank
     * @param name
     * @param email 
     */
    public UsersDTO(int id, int rank, String name, String email) {
        this.id = id;
        this.rank = rank;
        this.name = name;
        this.email = email;
    }

    /**
     * Konstruktør til konto område ved vise af navn
     * @param name 
     */
    public UsersDTO(String name) {
        this.name = name;
    }
    
    /**
     * Konstruktør bruges til login området. 
     * @param id
     * @param rank 
     */
    public UsersDTO(int id, int rank) {
        this.id = id;
        this.rank = rank;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
}
