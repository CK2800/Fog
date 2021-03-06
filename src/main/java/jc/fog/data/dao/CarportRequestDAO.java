package jc.fog.data.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import javafx.util.Pair;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.exceptions.RecordNotFoundException;
import jc.fog.exceptions.RecordNotFoundException.Table;
import jc.fog.logic.dto.CarportRequestDTO;

/**
 *
 * @author Jesper
 */
public class CarportRequestDAO extends AbstractDAO{
    
       

    final static String GET_CPREQUESTS_SQL = "SELECT c.id, c.rooftypeId, c.slope, c.width, c.height, c.length, c.remark, "
                                           + "s.id AS shedId, s.length AS shedLength, s.width AS shedWidth "
                                           + "FROM Carportrequests c LEFT OUTER JOIN Sheds s ON c.id = s.carportRequestId";
    final static String GET_CPREQUEST_SQL = GET_CPREQUESTS_SQL + " WHERE c.id = ?";
    
    final static String CREATE_CPREQUEST_SQL = "INSERT INTO Carportrequests(rooftypeId, slope, width, height, length, remark) VALUES (?, ?, ?, ?, ?, ?)"; 
    
    final static String CREATE_SHED_SQL = "INSERT INTO Sheds(carportRequestId, length, width) VALUES(?, ?, ?)";
    
    final static String UPDATE_CARPORT_SQL = "UPDATE Carportrequests SET slope = ?, width = ?, length = ?, rooftypeId = ?, remark = ? WHERE id = ?";        
    
    final static String UPDATE_SHED_SQL = "UPDATE Sheds SET width = ?, length = ? WHERE id = ?";
              
    final static String DELETE_SHED_SQL = "DELETE FROM Sheds WHERE id = ?";
            
    /**
     * Konstruktør som fordrer en DbConnector instans.     
     * @param connection     
     */
    public CarportRequestDAO(Connection connection)
    {          
        super(connection);        
    }
    
    /**
     * Den skal hente den enkelt forespørgsel.
     * @param id - Skal være angivet en værdi for, at kunne hente den enkelt.
     * @return Den henter enkelt forespørgsel. Det kan være fx nr 1.
     * @throws jc.fog.exceptions.FogException Beskrivende exception til brug i UI.     
     */
    public CarportRequestDTO getCarportRequest(int id) throws FogException
    {
        CarportRequestDTO carportRequest = null;
        Pair<Integer, Object> pair1 = new Pair<>(1, id);
        try
        (
            PreparedStatement pstm = createPreparedStatement(connection, GET_CPREQUEST_SQL, Statement.NO_GENERATED_KEYS, pair1);
            ResultSet rs = pstm.executeQuery();
        )
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
            else // record ej fundet.
            {
                throw new RecordNotFoundException(Table.CARPORTREQUESTS, "id", id);
            }
        }

        catch(RecordNotFoundException r)
        {
            throw new FogException("Forespørgslen kunne ikke findes.", "CarportRequest med id: " + id + " blev ikke fundet.", r);
        }
        catch(SQLException s)
        {
            throw new FogException("Forespørgslen kunne ikke findes.", s.getMessage(), s);
        }
        return carportRequest;
    }
    
    /**
     * Henter alle carport forespørgsler.
     * @return - List af CarportRequestDTO objekter. Fandtes ingen forespørgsler, returneres tom liste.
     * @throws jc.fog.exceptions.FogException
     */
    public ArrayList<CarportRequestDTO> getCarportRequests() throws FogException
    {
        
        //kan være den skal laves om.
        ArrayList<CarportRequestDTO> carportRequests = new ArrayList<CarportRequestDTO>();
        
        //Forsøg at hente forespørgsel ud fra Sql'en
        try
        (
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
        catch(SQLException s)
        {
            throw new FogException("Forespørgsler kunne ikke findes.", s.getMessage(), s);
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
     * @return true hvis oprettelsen lykkes, ellers false.
     * @throws jc.fog.exceptions.FogException
          
     */
    public int createCarportRequestAndShed(int rooftypeId, int slope, int width, int height, int length, int shedLength, int shedWidth, String remark) throws FogException
    {        
        // Fjern whitespace foran og bagved.
        remark = remark.trim();             
        try
        {
            // Start transaktion, da BÅDE carport OG skur (hvis valgt) skal oprettes.
            connection.setAutoCommit(false);
                        
            // Først oprettes forespørgsel.
            int carportRequestId = createCarportRequest(rooftypeId, slope, width, height, length, remark);
            // hvis skur dimensioner er angivet, oprettes det nu.
            if (shedLength > 0 && shedWidth > 0)
                createShed(carportRequestId, shedLength, shedWidth);

            connection.commit();
            // Reset autocommit på forbindelsen.
            connection.setAutoCommit(true);

            // If exactly one row was affected, return true.
            //pstm.executeUpdate() == 1;                                
            return carportRequestId;
        }
        catch(SQLException s)
        {
            try
            {
                connection.rollback();
            }
            catch(SQLException se)
            {
                // rollback failed, lad os lukke forbindelsen.
                DbConnector.closeConnection();
                throw new FogException("Forespørgslen blev ikke oprettet.", se.getMessage(), se);
            }
                
            throw new FogException("Forespørgslen blev ikke oprettet.", s.getMessage(), s);
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
     * @param rooftypeId
     * @param remark
     * @return
     * @throws jc.fog.exceptions.FogException    
     */
    public boolean updateCarPortRequestAndShed(int id, int shedId, String shedCheck, int slope, int width, int length, int shedWidth, int shedLength, int rooftypeId, String remark) throws FogException
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
                    ok = updateCarPort(slope, width, length, rooftypeId, remark, id);

                success = ok;
            }
            // Checkbox for skur fravalgt, men skurId findes => slet skur.
            else if(shedId > 0 && !"on".equals(shedCheck))
            {                               
                //Skal slet det enkelt skur.
                ok = deleteShed(shedId);
                if (ok) //Updater carport delen                
                    ok = updateCarPort(slope, width, length, rooftypeId, remark, id);

                success = ok;
            }
            // Intet skurId, men checkbox tilvalgt => opret skur.
            else if(shedId == 0 && "on".equals(shedCheck))
            {                
                ok = updateCarPort(slope, width, length, rooftypeId, remark, id);
                int addShedId = 0;
                if (ok)
                    addShedId = createShed(id, shedLength, shedWidth);
                
                ok = addShedId > 0;                

                success = ok;
            }
            else // Intet skurId og intet flueben i checkbox => opdater carportens værdier.
            {   
                ok = updateCarPort(slope, width, length, rooftypeId, remark, id);                
                success = ok;
            }

            connection.commit();
            connection.setAutoCommit(true);
        }
        catch(RecordNotFoundException r)
        {
            String friendly = "", detailed = "";
            switch (r.getTable())
            {
                case CARPORTREQUESTS:
                {
                    friendly = "Carport blev ikke opdateret.";
                    detailed = "Carport med id " + id + " blev ikke opdateret.";
                }
                break;
                case SHEDS:
                {
                    friendly = "Skur blev ikke opdateret.";
                    detailed = "Skur med id " + shedId + " blev ikke opdateret/nedlagt.";
                }
                break;
            }
            throw new FogException(friendly, detailed, r);            
        }
        catch(SQLException s)
        {
            throw new FogException("Carport og/eller skur blev ikke opdateret.", s.getMessage(),s);
        }
        
        return success;
                
    }

    /**
     * Skal updater skur område med nye værdier.
     * @param shedWidth
     * @param shedLength
     * @param shedId
     * @param commits
     * @return true hvis opdateringen lykkes, ellers false.
     * @throws SQLException Kastes hvis PreparedStatement fejler.
     * @throws RecordNotFoundException Kastes hvis record med angivet shedId ikke findes.
     */
    private boolean updateShed(int shedWidth, int shedLength, int shedId) throws SQLException, RecordNotFoundException {
        
        boolean success = false;
        Pair<Integer, Object> p1 = new Pair(1, shedWidth);
        Pair<Integer, Object> p2 = new Pair(2, shedLength);
        Pair<Integer, Object> p3 = new Pair(3, shedId);
        
        try(PreparedStatement pstm = createPreparedStatement(connection, UPDATE_SHED_SQL, Statement.NO_GENERATED_KEYS, p1, p2, p3 );)
        {
            //gem i databasen
            success = pstm.executeUpdate() == 1;
            if (!success)
            {
                throw new RecordNotFoundException(Table.SHEDS, "id", shedId);
            }
        }
        
//        try(PreparedStatement pstm = connection.prepareStatement(UPDATE_SHED_SQL);)
//        {
//            pstm.setInt(1, shedWidth);
//            pstm.setInt(2, shedLength);
//            pstm.setInt(3, shedId);
//            //gem i databasen
//            success = pstm.executeUpdate() == 1;
//            if (!success)
//            {
//                throw new RecordNotFoundException(Table.SHEDS, "id", shedId);
//            }
//        }
        
        return success;        
    }

    /**
     * Skal updater carport værdi til de nye værdier.
     * Er carportforespørgslens skur blevet slettet, angives 0 som shedId.
     * @param slope     
     * @param width
     * @param length
     * @param rooftypeId
     * @param remark
     * @param id     
     * @return true hvis opdateringen går godt, ellers false.
     * @throws SQLException Hvis PreparedStatement fejler, kastes denne exception.
     * @throws RecordNotFoundException Hvis record med id ikke findes, kastes denne exception.
     */
    private boolean updateCarPort(int slope, int width, int length, int rooftypeId, String remark, int id) throws SQLException, RecordNotFoundException {
        
        boolean success = false;
        
        try(PreparedStatement pstm = connection.prepareStatement(UPDATE_CARPORT_SQL);)
        {
            pstm.setInt(1, slope);                  
            pstm.setInt(2, width);
            pstm.setInt(3, length);
            pstm.setInt(4, rooftypeId);
            pstm.setString(5, remark);
            pstm.setInt(6, id);
            success = pstm.executeUpdate() == 1;
            
            if (!success)
            {
                throw new RecordNotFoundException(RecordNotFoundException.Table.CARPORTREQUESTS, "id", id);
            }
        }        
        return success;
    }

    /**
     * Den skal slette det enkelt skur.
     * @param shedId
     * @param commits
     * @return
     * @throws SQLException 
     */
    private boolean deleteShed(int shedId) throws SQLException, RecordNotFoundException {
        boolean success = false;
        try(PreparedStatement pstm = connection.prepareStatement(DELETE_SHED_SQL);)
        {
            pstm.setInt(1, shedId);
            success = pstm.executeUpdate() == 1;
            
            if (!success)
            {
                throw new RecordNotFoundException(Table.SHEDS, "id", shedId);
            }
        }
        return success;
    }
    
    /**
     * Opretter en carport forespørgsel og returnerer dens id.
     * Metoden lukker de ressourcer der bruges ifm. oprettelsen i databasen vha. try-with-ressources.
     * @param rooftypeId Tagtypens id.
     * @param slope Hældningsgrad.
     * @param width Vidde
     * @param height Højde
     * @param length Længde
     * @param remark evt. bemærkninger.
     * @return int Carportens id i databasen.
     * @throws SQLException Hvis oprettelsen slår fejl, kastes en SQLException.
     */
    private int createCarportRequest(int rooftypeId, int slope, int width, int height, int length, String remark) throws SQLException
    {
        // Opret Pair objekter som byttes ind i SQL.
        Pair<Integer, Object> pair1 = new Pair<>(1, rooftypeId);
        Pair<Integer, Object> pair2 = new Pair<>(2, slope);
        Pair<Integer, Object> pair3 = new Pair<>(3, width);
        Pair<Integer, Object> pair4 = new Pair<>(4, height);
        Pair<Integer, Object> pair5 = new Pair<>(5, length);
        Pair<Integer, Object> pair6 = new Pair<>(6, remark);
        
        try
        (
            PreparedStatement pstm = createPreparedStatement(connection, CREATE_CPREQUEST_SQL, Statement.RETURN_GENERATED_KEYS, pair1, pair2, pair3, pair4, pair5, pair6);
            ResultSet rs = updateAndGetKeys(pstm);
        )
        {
            rs.next();
            return rs.getInt(1);
        }
    }

    /**
     * Hjælpemetode som opretter skuret. 
     * Metoden lukker de ressourcer der bruges ifm. oprettelsen i databasen vha. try-with-ressources.
     * @param carportRequestId Id på carport forespørgsel som skuret hører til.
     * @param length skurets længde.
     * @param width skurets bredde.
     * @return id på det nyoprettede skur.
     * @throws SQLException Hvis der opstår fejl undervejs i oprettelsen, kastes SQLException.
     */
    private int createShed(int carportRequestId, int length, int width) throws SQLException
    {        
        Pair<Integer, Object> pair1 = new Pair<>(1, carportRequestId);
        Pair<Integer, Object> pair2 = new Pair<>(2, length);
        Pair<Integer, Object> pair3 = new Pair<>(3, width);
        
        try
        (
            PreparedStatement pstm = createPreparedStatement(connection, CREATE_SHED_SQL, Statement.RETURN_GENERATED_KEYS, pair1, pair2, pair3);
            ResultSet rs = updateAndGetKeys(pstm);
        )                
        {
            rs.next();
            return rs.getInt(1);
        }        
    }  
}
