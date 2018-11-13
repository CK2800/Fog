<<<<<<< HEAD
=======
package jc.fog.logic;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import jc.fog.presentation.ForesporgselDTO;

>>>>>>> develop
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
<<<<<<< HEAD
package jc.fog.logic;

/**
 *
 * @author Jespe
 */
public class ForesporgselDAO {
    
=======

/**
 *
 * @author Jesper
 */
public class ForesporgselDAO {
    
    //private static Connection connection;
    
    final static String singleForesporgsel = "SELECT * FROM Forespoergsel WHERE id = ?";
    final static String allForesporgsel = "SELECT * FROM Forespoergsel";
    final static String createForesporgsel = "INSerT INTO Forespoergsel(tagId, bredde, hoejde, laengde, bemaerkning) VALUES (?, ?, ?, ?, ?)";
    
    /*
        *Henter den enkelt forespørgsel fx nr 1 eller et andet nr
        *Skal som udgangspunkt vise det indhold som fx nr 1 har her.
    */
    public static ArrayList<ForesporgselDTO> getForesporgselSingle(int getForesporgselId)
    {
        ArrayList<ForesporgselDTO> foresporgsel = new ArrayList<ForesporgselDTO>();
       try{
            //connection = DBConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement(singleForesporgsel);
            pstm.setInt(1, getForesporgselId);
            
            //try with ressources.
            try(ResultSet rs = pstm.executeQuery())
            {
                //Hvad skal der være her?
            }
       } 
       catch(Exception e)
       {
           //Der er sket en fejl her
           System.out.println("Error:" + e.getMessage());
       }
       
       return foresporgsel;
    }
    
    /*
        * Den skal hente alle de foresporgsel der er i databasen.
    */
    public static ArrayList<ForesporgselDTO> getForesporgsel(){
        
        //kan være den skal laves om.
        ArrayList<ForesporgselDTO> foresporgsel = new ArrayList<ForesporgselDTO>();
        
        try{
            //connection = DBConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement(allForesporgsel);
            
            //try with ressources.
            try(ResultSet rs = pstm.executeQuery())
            {
                //skal hente disse værdier
            }
       } 
       catch(Exception e)
       {
           //Der er sket en fejl her
           System.out.println("Error:" + e.getMessage());
       }        
    }
    
    
    /*
        * Skal opret forspørgsel til databasen
    */
    public static boolean createForesporgsel(int tagId, int bredde, int hoejde, int laengde, String bemaerkning){
        //Den "space removed" i siderne
        bemaerkning = bemaerkning.trim();
        
        try
        {
            connection = DBConnection.getConnection();
            PreparedStatement pstm = connection.prepareStatement(createForesporgsel);
            pstm.setInt(1, tagId);
            pstm.setInt(2, bredde);
            pstm.setInt(3, hoejde);
            pstm.setInt(4, laengde);
            pstm.setString(5, bemaerkning);
            
            // If exactly one row was affected, return true.
            return pstm.executeUpdate() == 1;
        }
        catch(Exception e)
        {
            System.out.println("Kunne ikke opret pga " + e.getMessage());
        }
        
    }
>>>>>>> develop
}
