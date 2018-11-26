package jc.fog.data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import jc.fog.exceptions.FogException;
import jc.fog.logic.CarportRequestDTO;

/**
 *
 * @author Jesper
 */
public class CarportRequestDAO extends AbstractDAO{
    
       

    final static String GET_CPREQUESTS_SQL = "SELECT f.id, f.rooftypeId, f.haeldning, f.skurId, f.bredde, f.hoejde, f.laengde, f.bemaerkning, "
                                           + "s.laengde AS skurLaengde, s.bredde AS skurBredde "
                                           + "FROM Forespoergsel f LEFT OUTER JOIN Skur s ON f.skurId = s.id";
    final static String GET_CPREQUEST_SQL = GET_CPREQUESTS_SQL + " WHERE f.id = ?";
    
    final static String CREATE_CPREQUEST_SQL = "INSERT INTO Forespoergsel(rooftypeId, haeldning, skurId, bredde, hoejde, laengde, bemaerkning) VALUES (?, ?, ?, ?, ?, ?, ?)"; 
    
    final static String CREATE_SHED_SQL = "INSERT INTO Skur(laengde, bredde) VALUES(?,?)";
        
    /**
     * Konstruktør som fordrer en DbConnector instans.     
     * @param connection
     * @throws FogException 
     */
    public CarportRequestDAO(Connection connection) throws FogException
    {          
        super(connection);        
    }
    
    /**
     * Den skal hente den enkelt forespørgsel.
     * @param id - Skal være angivet en værdi for, at kunne hente den enkelt.
     * @return Den henter enkelt forespørgsel. Det kan være fx nr 1.
     */
    public CarportRequestDTO getCarportRequest(int id) throws FogException
    {
        CarportRequestDTO carportRequest = null;
        try
        {          
            
             PreparedStatement pstm = connection.prepareStatement(GET_CPREQUEST_SQL);
             pstm.setInt(1, id);

             //try with ressources.
             try(ResultSet rs = pstm.executeQuery())
             {
                 if(rs.next())
                 {
                     //System.out.println("rs:" + rs.toString());
                     carportRequest = new CarportRequestDTO(
                                rs.getInt("id"),
                                rs.getInt("rooftypeId"),
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
       
       return carportRequest;
    }
    
    /**
     * Den henter alt i databasen hvor alt sammen bliver brugt senere.
     * @return - Alle de forespørgsel der findes i databasen.
     */
    public ArrayList<CarportRequestDTO> getCarportRequests() throws FogException{
        
        //kan være den skal laves om.
        ArrayList<CarportRequestDTO> carportRequests = new ArrayList<CarportRequestDTO>();
        
        try{
            
            //Forsøg at hente forespørgsel ud fra Sql'en
            PreparedStatement pstm = connection.prepareStatement(GET_CPREQUESTS_SQL);
            
            //try with ressources.
            try(ResultSet rs = pstm.executeQuery())
            {
                while(rs.next())//Løber alle igennem
                {
                    System.out.println("rs:" + rs.toString());
                     carportRequests.add(new CarportRequestDTO(
                                rs.getInt("id"),
                                rs.getInt("rooftypeId"),
                                rs.getInt("haeldning"),
                                rs.getInt("skurId"),
                                rs.getInt("bredde"),
                                rs.getInt("hoejde"),
                                rs.getInt("laengde"),
                                rs.getString("bemaerkning"),
                                rs.getInt("skurLaengde"),
                                rs.getInt("skurBredde")
                     ));
                }
            }            
            
            return carportRequests;
       } 
       catch(Exception e)
       {
           //Der er sket en fejl her
           throw new FogException("Error:" + e.getMessage(), e.getMessage());
       }  
       
    }

    /**
     * Skal kun opret forespørgsel.
     * @param productId
     * @param slope
     * @param width
     * @param height
     * @param length
     * @param shedLength
     * @param shedWidth
     * @param remark - den kan evt være kommentar til Fog.
     * @return
     * @throws SQLException 
     * Bemærk: skurlaengde + bredde skal videre giv Skurs id over til forespørgsel.
     */
    public boolean createCarportRequest(int productId, int slope, int width, int height, int length, int shedLength, int shedWidth, String remark) throws FogException
    {
        //Den "space removed" i siderne
        remark = remark.trim();
        
        try
        {

            PreparedStatement pstm;
            int shedId = 0;
            // Først oprettes skur, hvis det ønskes.
            if (shedLength > 0 && shedWidth > 0) // skur ønskes.
                shedId = createShed(shedLength, shedWidth);                
            
            // Opret forespørgsel, evt. med skur.
            pstm = connection.prepareStatement(CREATE_CPREQUEST_SQL);
            pstm.setInt(1, productId);
            pstm.setInt(2, slope);           
            // Hvis skur ønskes, sættes dets id i sql...
            if (shedId != 0)
                pstm.setInt(3, shedId);                
            else // ... intet skur medfører null i sql.
                pstm.setNull(3, Types.INTEGER);
            
            pstm.setInt(4, width);
            pstm.setInt(5, height);
            pstm.setInt(6, length);
            pstm.setString(7, remark);
                            
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
     * Hjælpemetode som opretter skuret. 
     * @param length skurets længde.
     * @param width skurets bredde.
     * @return id på det nyoprettede skur.
     * @throws FogException 
     */
    private int createShed(int length, int width) throws FogException
    {
        try
        {
            PreparedStatement pstm = connection.prepareStatement(CREATE_SHED_SQL, Statement.RETURN_GENERATED_KEYS );
            pstm.setInt(1, length);
            pstm.setInt(2, width);
            pstm.executeUpdate();
            ResultSet rs = pstm.getGeneratedKeys();
            // Move the cursor to first record.
            rs.next();            
            return rs.getInt(1); 
        }
        catch(Exception e)
        {
            throw new FogException("Skur blev ikke oprettet.", e.getMessage());
        }
    }
}
