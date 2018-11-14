package jc.fog.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import jc.fog.logic.ForesporgselDTO;

/**
 *
 * @author Jesper
 */
public class ForesporgselDAO {
    
    private static Connection connection;
    
    final static String singleForesporgsel = "SELECT * FROM Forespoergsel WHERE id = ?";
    final static String allForesporgsel = "SELECT * FROM Forespoergsel";

    final static String createForesporgsel = "INSERT INTO Forespoergsel(vareId, haeldning, skurId, bredde, hoejde, laengde, bemaerkning) VALUES (?, ?, ?, ?, ?, ?, ?)";
    final static String createSkur = "INSERT INTO Skur(laengde, bredde) VALUES(?,?)";

    /*
        *Henter den enkelt forespørgsel fx nr 1 eller et andet nr
        *Skal som udgangspunkt vise det indhold som fx nr 1 har her.
    */
    public static ArrayList<ForesporgselDTO> getForesporgselSingle(int getForesporgselId)
    {
        ArrayList<ForesporgselDTO> foresporgsel = new ArrayList<ForesporgselDTO>();
        try{
             connection = DbConnection.getConnection();
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
            // Her skal vi have vores egen exception handling...
            System.out.println("Error:" + e.getMessage());
        }
       
       return foresporgsel;
    }
    
    /*
        * Den skal hente alle de foresporgsel der er i databasen.
    */


    public static ArrayList<ForesporgselDTO> getForesporgsel()
    {        
        //kan være den skal laves om.
        ArrayList<ForesporgselDTO> foresporgsel = new ArrayList<ForesporgselDTO>();
        
        try{

            connection = DbConnection.getConnection();
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

       return foresporgsel;
    }

    
    
    /*
        * Skal opret forspørgsel til databasen
    */

    
    /**
     * Metode som opretter forespørsel i databasen.
     * @param vareId
     * @param haeldning Fladt tag angives med 0 grader.
     * @param bredde
     * @param hoejde
     * @param laengde
     * @param skurLaengde 
     * @param skurBredde
     * @param bemaerkning
     * @return boolean true hvis forespørgsel oprettes, ellers false.
     */
    
    public static boolean createForesporgsel(int vareId, int haeldning, int bredde, int hoejde, int laengde, int skurLaengde, int skurBredde, String bemaerkning) throws SQLException
    {

        //Den "space removed" i siderne
        bemaerkning = bemaerkning.trim();
        
        /*
        PSEUDO:
        Opret skur hvis det ønskes
        Hent skurets id
        opret forespørgsel med skurets id hvis det blev oprettet.
        
        */
        
        try
        {
            connection = DbConnection.getConnection();

            PreparedStatement pstm;
            int skurId = 0;
            // Først oprettes skur, hvis det ønskes.
            if (skurLaengde > 0 && skurBredde > 0) // skur ønskes.
            {
                pstm = connection.prepareStatement(createSkur, Statement.RETURN_GENERATED_KEYS );
                pstm.setInt(1, skurLaengde);
                pstm.setInt(2, skurBredde);
                pstm.executeUpdate();
                ResultSet rs = pstm.getGeneratedKeys();
                // Move the cursor to first record.
                rs.next();
                //Q&D - her skal vi have noget error checking...
                skurId = rs.getInt(1);
            }
            // Opret forespørgsel, evt. med skur.
            pstm = connection.prepareStatement(createForesporgsel);

            pstm.setInt(1, vareId);
            pstm.setInt(2, haeldning);           
            
            if (skurId != 0)
                pstm.setInt(3, skurId);                
            else
                pstm.setNull(3, Types.INTEGER);
            
            pstm.setInt(4, bredde);
            pstm.setInt(5, hoejde);
            pstm.setInt(6, laengde);
            pstm.setString(7, bemaerkning);
                            
            // If exactly one row was affected, return true.
            return pstm.executeUpdate() == 1;                
            
        }
        // Her skal vi have egen exception handling
        catch(Exception e)
        {
            System.out.println("Kunne ikke opret pga " + e.getMessage());
        }
        return false;
    }
}
