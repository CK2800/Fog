/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jc.fog.data.DataFacadeImpl;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.logic.dto.CarportRequestDTO;
import jc.fog.logic.dto.UsersDTO;
import jc.fog.presentation.Pages;


/**
 * This command is used to retrieve all carport requests in the database 
 * and show them on the webpage named all_requests.jsp.
 * 
 * @author Claus
 */
public class ShowRequestsCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        try {
            //sikker sig at man har den rigtigt rank for at kun se det her område.
            HttpSession session = request.getSession();
            UsersDTO user = (UsersDTO)session.getAttribute("user");
            // Har vi en user i session, er denne logget ind, gå til index side.
            if(user == null || user != null && user.getRank() > 1)
            {
                return Pages.INDEX;
            } 

            // Get the list of requests.
            DataFacadeImpl dataFacade = new DataFacadeImpl(DbConnector.getConnection());
            List<CarportRequestDTO> requests = dataFacade.getCarports();

            // Convert the requests to a nicely formattet HTML table and save on request.
            request.setAttribute("requestsTable", requestsToHtml(requests));

            // Return the page showing all requests.
            return Pages.ALL_CARPORTS;
        }
        catch(Exception e)
        {
            throw new FogException("Der gik noget galt da man vil se carport forespørgelse..", e.getMessage(), e);
        }
    }
    
    /**
     * Hjælpemetode til formattering af en samling af CarportRequestDTO objekter til html tabel.
     * @param requests Samling af CarportRequestDTO objekter.
     * @return String En html tabel.
     */
    private String requestsToHtml(List<CarportRequestDTO> requests)
    {
        StringBuilder stringBuilder = new StringBuilder();
        String table = "<table class=\"table table-striped\"><thead><tr><th>$1</th><th>$2</th><th>$3</th><th>$4</th><th>$5</th><th>$6</th><th>$7</th></tr></thead><tbody>$body</tbody></table>";        
        
        table = table.replace("$1", "ID");
        table = table.replace("$2", "BREDDE");
        table = table.replace("$3", "H&Oslash;JDE");
        table = table.replace("$4", "L&AElig;NGDE");
        table = table.replace("$5", "Skur");
        table = table.replace("$6", "Taghældning");
        table = table.replace("$7", "Se forespørgsel");
        
        for(CarportRequestDTO item : requests)
        {            
            String row = "<tr><td>$1</td><td>$2</td><td>$3</td><td>$4</td><td>$5</td><td>$6</td><td>$7</td></tr>";
            row = row.replace("$1", String.valueOf(item.getId()));
            row = row.replace("$2", String.valueOf(item.getWidth()));
            row = row.replace("$3", String.valueOf(item.getHeight()));
            row = row.replace("$4", String.valueOf(item.getLength()));
            row = row.replace("$5", shedIsIndicated(item));
            row = row.replace("$6", Integer.toString(item.getSlope()));
            row = row.replace("$7", "<a href=\"FrontController?command=" + Commands.SINGLEDRAW + "&id=" + item.getId() + "\" class=\"btn btn-info btn-sm\">Se tegning</a> "
                    + "<a href=\"FrontController?command=" + Commands.SHOW_BILL + "&id=" + item.getId() + "\" class=\"btn btn-info btn-sm\">Se Stykliste</a> "
                    + "<a href=\"FrontController?command=" + Commands.SHOW_FORM_REQUEST + "&id=" + item.getId() + "\" class=\"btn btn-info btn-sm\">Opdater</a>");

            stringBuilder.append(row);
            
        }
        
        return table.replace("$body", stringBuilder.toString());
    }

    private String shedIsIndicated(CarportRequestDTO item) {
        String textSkur = "Nej";
        if(item.getShedId() > 0)
        {
            textSkur = "Ja";
        }
        return textSkur;
    }
}
