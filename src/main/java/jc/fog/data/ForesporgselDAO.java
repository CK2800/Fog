package jc.fog.data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;

/**
 *
 * @author Jesper
 */
public class ForesporgselDAO {
    
    private static Connection connection;
    
    final static String singleForesporgsel = "SELECT f.id, f.vareId, f.haeldning, f.skurId, f.bredde, f.hoejde, f.laengde, f.bemaerkning, "
                                           + "s.laengde AS skurLaengde, s.bredde AS skurBredde "
                                           + "FROM Forespoergsel f LEFT OUTER JOIN Skur s ON f.skurId = s.id WHERE f.id = ?";
    final static String allForesporgsel = "SELECT * FROM Forespoergsel";
    final static String createForesporgsel = "INSERT INTO Forespoergsel(vareId, haeldning, skurId, bredde, hoejde, laengde, bemaerkning) VALUES (?, ?, ?, ?, ?, ?, ?)";
    final static String createSkur = "INSERT INTO Skur(laengde, bredde) VALUES(?,?)";

    /**
     * Den skal hente den enkelt forespørgsel.
     * @param getForesporgselId - Skal være angivet en værdi for, at kunne hente den enkelt.
     * @return Den henter enkelt forespørgsel. Det kan være fx nr 1.
     */
    public static ForesporgselDTO getForesporgselSingle(int getForesporgselId) throws FogException
    {
        ForesporgselDTO foresporgsel = null;
        try{
             connection = DbConnection.getConnection();
             PreparedStatement pstm = connection.prepareStatement(singleForesporgsel);
             pstm.setInt(1, getForesporgselId);

             //try with ressources.
             try(ResultSet rs = pstm.executeQuery())
             {
                 if(rs.next())
                 {
                     //System.out.println("rs:" + rs.toString());
                     foresporgsel = new ForesporgselDTO(
                                rs.getInt("id"),
                                rs.getInt("vareId"),
                                rs.getInt("haeldning"),
                                rs.getInt("skurId"),
                                rs.getInt("bredde"),
                                rs.getInt("hoejde"),
                                rs.getInt("laengde"),
                                rs.getString("bemaerkning"),
                                rs.getInt("skurLaengde"),
                                rs.getInt("skurBredde")
                     );
                     
                 }
             }
        } 
        catch(Exception e)
        {
            // Her skal vi have vores egen exception handling...
            throw new FogException("Carport forespørgsel ej fundet.", e.getMessage());
        }
       
       return foresporgsel;
    }
    
    /**
     * Den henter alt i databasen hvor alt sammen bliver brugt senere.
     * @return - Alle de forespørgsel der findes i databasen.
     */
    public static ArrayList<ForesporgselDTO> getForesporgsel(){
        
        //kan være den skal laves om.
        ArrayList<ForesporgselDTO> foresporgsel = new ArrayList<ForesporgselDTO>();
        
        try{
            //laver connection
            connection = DbConnection.getConnection();
            //Forsøg at hente forespørgsel ud fra Sql'en
            PreparedStatement pstm = connection.prepareStatement(allForesporgsel);
            
            //try with ressources.
            try(ResultSet rs = pstm.executeQuery())
            {
                while(rs.next())//Løber alle igennem
                {
                    System.out.println("rs:" + rs.toString());
                     foresporgsel.add(new ForesporgselDTO(
                                rs.getInt("id"),
                                rs.getInt("bredde"),
                                rs.getInt("hoejde"),
                                rs.getInt("laengde"))
                     );
                }
            }
       } 
       catch(Exception e)
       {
           //Der er sket en fejl her
           System.out.println("Error:" + e.getMessage());
       }   
       return foresporgsel;
    }

    /**
     * Skal kun opret forespørgsel.
     * @param vareId
     * @param haeldning
     * @param bredde
     * @param hoejde
     * @param laengde
     * @param skurLaengde
     * @param skurBredde
     * @param bemaerkning - den kan evt være kommentar til Fog.
     * @return
     * @throws SQLException 
     * Bemærk: skurlaengde + bredde skal videre giv Skurs id over til forespørgsel.
     */
    public static boolean createForesporgsel(int vareId, int haeldning, int bredde, int hoejde, int laengde, int skurLaengde, int skurBredde, String bemaerkning) throws FogException
    {
        //Den "space removed" i siderne
        bemaerkning = bemaerkning.trim();
        
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
            throw new FogException("Forespørgsel blev ikke gemt.", e.getMessage());
        }        
    }
    
    /**
     * Få det lavet hele i en html table.
     * @return 
     */
    public static String getForesporgselTable(){
        String html = "<table class='table table-striped'><tr><th>id</th><th>Bredde</th><th>Højde</th><th>Længde</th></tr>";
        ArrayList<ForesporgselDTO> foresporgselList = getForesporgsel();
        for(ForesporgselDTO value : foresporgselList)
        {
            html += getListToTable(value);
        }
        html += "</table>";
        return html;
    }
    
    /**
     * Den fremviser Id, bredde, højde og længde på en pæn måde i html
     * @param foresporgselDTO - Den modtager værdi som kommer fra "For" og ligger det ind de angivet steder her.
     * @return Den returner indholdet på en pæn måde som gør det læsbar fra bruger.
     */
    public static String getListToTable(ForesporgselDTO foresporgselDTO){
        String html = "<tr><td>" +  foresporgselDTO.getId() + "</a></td><td>" + foresporgselDTO.getBredde()+ 
                      "</a></td><td>"+ foresporgselDTO.getHoejde()+ "</a></td><td>" + foresporgselDTO.getLaengde()+ "</a></td></tr>";;
        return html;
    }
}
