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
    
       

    final static String GET_CPREQUESTS_SQL = "SELECT c.id, c.rooftypeId, c.slope, c.shedId, c.width, c.height, c.length, c.remark, "
                                           + "s.length AS shedLength, s.width AS shedWidth "
                                           + "FROM Carportrequests c LEFT OUTER JOIN Sheds s ON c.shedId = s.id";
    final static String GET_CPREQUEST_SQL = GET_CPREQUESTS_SQL + " WHERE c.id = ?";
    
    final static String CREATE_CPREQUEST_SQL = "INSERT INTO Carportrequests(rooftypeId, slope, shedId, width, height, length, remark) VALUES (?, ?, ?, ?, ?, ?, ?)"; 
    
    final static String CREATE_SHED_SQL = "INSERT INTO Sheds(length, width) VALUES(?,?)";
    
    final static String UPDATE_CARPORT_SQL = "UPDATE Carportrequests SET slope = ?, shedId = ?, width = ?, length = ?, rooftypeId = ?, remark = ? WHERE id = ?";
    
    final static String UPDATE_SHEDID_SQL = "UPDATE Carportrequests SET shedId = ? WHERE id = ?";
    
    final static String UPDATE_SHED_SQL = "UPDATE Sheds SET width = ?, length = ? WHERE id = ?";
              
    final static String DELETE_SHED_SQL = "DELETE FROM Sheds WHERE id = ?";
            
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
                                rs.getInt("slope"),
                                rs.getInt("shedId"),
                                rs.getInt("width"),
                                rs.getInt("height"),
                                rs.getInt("length"),
                                rs.getString("remark"),
                                rs.getInt("shedLength"),
                                rs.getInt("shedWidth")
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
     * Henter alle carport forespørgsler.
     * @return - List af CarportRequestDTO objekter. Fandtes ingen forespørgsler, returneres tom liste.
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
                                rs.getInt("slope"),
                                rs.getInt("shedId"),
                                rs.getInt("width"),
                                rs.getInt("height"),
                                rs.getInt("length"),
                                rs.getString("remark"),
                                rs.getInt("shedLength"),
                                rs.getInt("shedWidth")
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
     * @param rooftypeId
     * @param slope
     * @param width
     * @param height
     * @param length
     * @param shedLength
     * @param shedWidth
     * @param remark - den kan evt være kommentar til Fog.
     * @return
     * @throws FogException 
     * Bemærk: skurlaengde + bredde skal videre giv Skurs id over til forespørgsel.
     */
    public boolean createCarportRequest(int rooftypeId, int slope, int width, int height, int length, int shedLength, int shedWidth, String remark) throws FogException
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
            pstm.setInt(1, rooftypeId);
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
     * Den her skal updater i forhold til den forespørgelse der er i databasen
     * @param id
     * @param shedId
     * @param shedCheck 
     * @param slope
     * @param width
     * @param length
     * @param shedWidth
     * @param shedLength
     * @return
     * @throws FogException 
     */
    public boolean updateCarPortRequest(int id, int shedId, String shedCheck, int slope, int width, int length, int shedWidth, int shedLength, int rooftypeId, String remark) throws FogException
    {
        try
        {
            //Updater shed
            PreparedStatement pstm;
             
            // Har vi skurets id og er skuret tilvalgt, opdater evt. skurets attributter.
            if(shedId > 0 && "on".equals(shedCheck))//Den skal køre hvis Shedid er over 0 og Checkbox er tilføjet
            {   
                // Start transaktion idet vi vil have ALLE opdateringer eller INGEN gennemført.
                connection.setAutoCommit(false);
                //Opdater skur
                
                //Skal opdater værdi hos skur. til de nye værdier.
                updateShed(shedWidth, shedLength, shedId);
                
                //Skal opdater til de nye oplysning.
                updateCarPort(slope, shedId, width, length, rooftypeId, remark, id);
                
                connection.commit();
                connection.setAutoCommit(true);
                return true;
                                
            }
            else if(shedId > 0 && !"on".equals(shedCheck))
            {
                // Start transaktion idet vi vil have ALLE opdateringer eller INGEN gennemført.
                connection.setAutoCommit(false);
                //Slet Skur
                               
                //Skal slet det enkelt skur.
                deleteShed(shedId);
                
                //Updater carport delen                
                updateCarPort(slope, 0, width, length, rooftypeId, remark, id);
                //updaterShedId(0, id);
                
                connection.commit();
                connection.setAutoCommit(true);
                return true;
                
                
            }
            else if(shedId == 0 && "on".equals(shedCheck))
            {
                // Start transaktion idet vi vil have ALLE opdateringer eller INGEN gennemført.
                connection.setAutoCommit(false);
                //Skal tilføj Skurid til carport.
                
                //Her skal den opret skur til databasen. Hvis der ikke er tilføjet skurid i formen.
                //Men man har klikket af på, at shedcheck.
                //int addShedId = createShed(shedLength, shedWidth);
                //eller skal vi bare bruge "createShed" og så få angivet den værdi som vi kan bruge til, at updater til forespørgelse.
                
                pstm = connection.prepareStatement(CREATE_SHED_SQL, Statement.RETURN_GENERATED_KEYS);
                pstm.setInt(1, shedLength);
                pstm.setInt(2, shedWidth);
                //pstm.addBatch();
                
                pstm.executeUpdate();
                ResultSet rs = pstm.getGeneratedKeys();
                rs.next();
                
                int addShedId = rs.getInt(1);
                rs.close();
                
                
                pstm = connection.prepareStatement(UPDATE_SHEDID_SQL);
                pstm.setInt(1, addShedId);
                pstm.setInt(2, id);
                pstm.executeUpdate();
                
                connection.commit();
                // Reset autocommit på forbindelsen.
                connection.setAutoCommit(true);
                return true;
            }
            else
            {
                //skal kun updater de enkelte værdi på forespørgelsen
                connection.setAutoCommit(false);
                
                updateCarPort(slope, shedId, width, length, rooftypeId, remark, id);
                
                connection.commit();
                connection.setAutoCommit(true);
                
                return true;
            }
        }
        catch(Exception e)
        {
            throw new FogException("Kunne ikke opdater", e.getMessage());
        }    
    }

    /**
     * Skal updater skur område med nye værdier.
     * @param shedWidth
     * @param shedLength
     * @param shedId
     * @param commits
     * @return
     * @throws SQLException 
     */
    private boolean updateShed(int shedWidth, int shedLength, int shedId) throws SQLException {
        PreparedStatement pstm;
        //Updater skur værdi.
        pstm = connection.prepareStatement(UPDATE_SHED_SQL);
        pstm.setInt(1, shedWidth);
        pstm.setInt(2, shedLength);
        pstm.setInt(3, shedId);
        //gem i databasen
        return pstm.executeUpdate() == 1;
        
    }

    /**
     * Skal updater carport værdi til de nye værdier.
     * Er carportforespørgslens skur blevet slettet, angives 0 som shedId.
     * @param slope
     * @param shedId Skurets id eller 0 ved intet skur.
     * @param width
     * @param length
     * @param rooftypeId
     * @param remark
     * @param id
     * @param commits
     * @return
     * @throws SQLException 
     */
    private boolean updateCarPort(int slope, int shedId, int width, int length, int rooftypeId, String remark, int id) throws SQLException {
        PreparedStatement pstm;
        //Updater carport værdi.
        pstm = connection.prepareStatement(UPDATE_CARPORT_SQL);
        pstm.setInt(1, slope);
        if (shedId != 0)
                pstm.setInt(2, shedId);                
            else // ... intet skur medfører null i sql.
                pstm.setNull(2, Types.INTEGER);        
        pstm.setInt(3, width);
        pstm.setInt(4, length);
        pstm.setInt(5, rooftypeId);
        pstm.setString(6, remark);
        pstm.setInt(7, id);
        return pstm.executeUpdate() == 1;
        
    }

    /**
     * Den skal slette det enkelt skur.
     * @param shedId
     * @param commits
     * @return
     * @throws SQLException 
     */
    private boolean deleteShed(int shedId) throws SQLException {
        PreparedStatement pstm;
        //Her skal vi slette Skur.
        pstm = connection.prepareStatement(DELETE_SHED_SQL);
        pstm.setInt(1, shedId);
        return pstm.executeUpdate() == 1;
        
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
