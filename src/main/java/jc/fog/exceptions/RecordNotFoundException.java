/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Exception som bruges, når en record ikke findes i databasen.
 * @author Claus
 */
public class RecordNotFoundException extends Exception
{
    /**
     * Logger for RecordNotFoundException klassen. 
     * Erklæret statisk, så alle instanser deler samme logger.
     */
    //private static final Logger LOGGER = FogLogger.getLogger(jc.fog.exceptions.RecordNotFoundException.class.getName(), false); ej nødv., da vi logger i FogException og disse konverteres dertil.
    
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
        super("Record i tabel '$1' med $2 = $3 ej fundet.".replace("$1", table.name()).replace("$2", column).replace("$3", criteria.toString()));        
        
        this.table = table;    
        this.criteria = criteria;
        this.column = column;
        
        // Log undtagelsen.
        //LOGGER.log(Level.SEVERE, this.getMessage());
    }
}
