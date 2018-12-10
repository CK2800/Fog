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
        try(PreparedStatement pstm = connection.prepareStatement(GET_CPREQUEST_SQL))
        {
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
    public ArrayList<CarportRequestDTO> getCarportRequests() throws FogException
    {
        
        //kan være den skal laves om.
        ArrayList<CarportRequestDTO> carportRequests = new ArrayList<CarportRequestDTO>();
        
        //Forsøg at hente forespørgsel ud fra Sql'en
        try(
            PreparedStatement pstm = connection.prepareStatement(GET_CPREQUESTS_SQL); 
            ResultSet rs = pstm.executeQuery();
            )
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
        catch(Exception e)
        {
            //Der er sket en fejl her
            throw new FogException("Error:" + e.getMessage(), e.getMessage());
        }  
        return carportRequests;
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
        // Fjern whitespace foran og bagved.
        remark = remark.trim();
        boolean success = false;
        try(PreparedStatement pstm = connection.prepareStatement(CREATE_CPREQUEST_SQL);)
        {
            // Start transaktion, da BÅDE skur (hvis valgt) OG carport skal oprettes.
            connection.setAutoCommit(false);
            
            int shedId = 0;
            // Først oprettes skur, hvis det ønskes.
            if (shedLength > 0 && shedWidth > 0) // skur ønskes.
                shedId = createShed(shedLength, shedWidth);                
            
            // Opret forespørgsel, evt. med skur.
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

            connection.commit();
            // Reset autocommit på forbindelsen.
            connection.setAutoCommit(true);

            // If exactly one row was affected, return true.
            success = pstm.executeUpdate() == 1;                                
        }
        // Her skal vi have egen exception handling
        catch(Exception e)
        {
            // Blev forespørgsel ikke oprettet, kast exception.
            if (!success)
                throw new FogException("Forespørgsel blev ikke gemt.", e.getMessage());
            else // forespørgsel oprettet, men exception er sket.
            {
                // todo: log exception.
            }
        }        
        return success;
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
        boolean success = false, ok = false;
        
        try
        {                         
            // Start transaktion idet vi vil have ALLE opdateringer eller INGEN gennemført.
            connection.setAutoCommit(false);
            
            // Har vi skurets id og er skuret tilvalgt, opdater evt. skurets attributter.
            if(shedId > 0 && "on".equals(shedCheck))//Den skal køre hvis Shedid er over 0 og Checkbox er tilføjet
            {                   
                //Skal opdater værdi hos skur. til de nye værdier.
                ok = updateShed(shedWidth, shedLength, shedId);
                if (ok) // skur opdateret ok, nu opdateres carport.
                    ok = updateCarPort(slope, shedId, width, length, rooftypeId, remark, id);
                    
                success = ok;
            }
            // Checkbox for skur fravalgt, men skurId findes => slet skur.
            else if(shedId > 0 && !"on".equals(shedCheck))
            {                               
                //Skal slet det enkelt skur.
                ok = deleteShed(shedId);
                if (ok) //Updater carport delen                
                    updateCarPort(slope, 0, width, length, rooftypeId, remark, id);
                
                success = ok;
            }
            // Intet skurId, men checkbox tilvalgt => opret skur.
            else if(shedId == 0 && "on".equals(shedCheck))
            {                
                int addShedId = createShed(shedLength, shedWidth);
                if (addShedId > 0)
                    ok = updateCarPort(slope, addShedId, width, length, rooftypeId, remark, id);
                else
                    ok = false;
                
                success = ok;
            }
            else // Opdater carportens værdier.
            {   
                ok = updateCarPort(slope, shedId, width, length, rooftypeId, remark, id);                
                success = ok;
            }
            
            connection.commit();
            connection.setAutoCommit(true);
            return success;
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
        
        try(PreparedStatement pstm = connection.prepareStatement(UPDATE_SHED_SQL);)
        {
            pstm.setInt(1, shedWidth);
            pstm.setInt(2, shedLength);
            pstm.setInt(3, shedId);
            //gem i databasen
            return pstm.executeUpdate() == 1;
        }
        
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
        try(PreparedStatement pstm = connection.prepareStatement(UPDATE_CARPORT_SQL);)
        {
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
    }

    /**
     * Den skal slette det enkelt skur.
     * @param shedId
     * @param commits
     * @return
     * @throws SQLException 
     */
    private boolean deleteShed(int shedId) throws SQLException {
        try(PreparedStatement pstm = connection.prepareStatement(DELETE_SHED_SQL);)
        {
            pstm.setInt(1, shedId);
            return pstm.executeUpdate() == 1;
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
        int id = 0;
        try(PreparedStatement pstm = connection.prepareStatement(CREATE_SHED_SQL, Statement.RETURN_GENERATED_KEYS );)
        {
            pstm.setInt(1, length);
            pstm.setInt(2, width);
            pstm.executeUpdate();
            try(ResultSet rs = pstm.getGeneratedKeys();)
            {
                // Move the cursor to first record.
                rs.next();            
                id = rs.getInt(1); 
            }
        }
        catch(Exception e)
        {
            throw new FogException("Skur blev ikke oprettet.", e.getMessage());
        }
        return id;
    }
    
    
}
