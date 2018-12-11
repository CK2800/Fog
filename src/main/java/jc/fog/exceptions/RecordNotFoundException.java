/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.exceptions;

/**
 * Exception som bruges, når en record ikke findes i databasen.
 * @author Claus
 */
public class RecordNotFoundException extends Exception
{
    /** Databasens tabeller */
    public static enum Table { CARPORTREQUESTS, MATERIALS, REMARKS, ROOFTYPEMATERIALS, ROOFTYPES, SHEDS, USERS, ZIPCODES }
    
    private Table table;
    /**
     * Indikerer i hvilken tabel der blev søgt.
     * @return 
     */
    public Table getTable(){return table;}
    private Object criteria;
    /**
     * Returnerer kriteriet, der blev søgt efter.
     * @return 
     */
    public Object getCriteria(){return criteria;}
    private String column;
    /**
     * Returnerer den kolonne der blev søgt i.
     * @return 
     */
    public String getColumn(){return column;}
           
    /**
     * Opretter exception for fejlslagen søgning.
     * @param table Tabel, søgning blev foretaget i.     
     * @param column Kolonne i hvilken kriteriet blev søgt efter.
     * @param criteria Kriterie der blev søgt efter.
     */    
    public RecordNotFoundException(Table table, String column, Object criteria)
    {
        super("Record ej fundet");
        this.table = table;    
        this.criteria = criteria;
        this.column = column;
    }
}
