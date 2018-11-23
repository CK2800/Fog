/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.data.DataFacade;
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;

import jc.fog.presentation.Commands;
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
        // Later we will validate a logged in user
        
        // Get the list of requests.
        List<ForesporgselDTO> requests = DataFacade.getCarPorts();
        
        // Convert the requests to a nicely formattet HTML table and save on request.
        request.setAttribute("requestsTable", requestsToHtml(requests));
        
        // Return the page showing all requests.
        return Pages.ALL_CARPORTS;
    }
    
    /**
     * Hjælpemetode til formattering af en samling af ForesporgselDTO objekter til html tabel.
     * @param requests Samling af ForesporgselDTO objekter.
     * @return String En html tabel.
     */
    private String requestsToHtml(List<ForesporgselDTO> requests)
    {
        StringBuilder stringBuilder = new StringBuilder();
        String table = "<table class=\"table table-striped\"><thead><tr><th>$1</th><th>$2</th><th>$3</th><th>$4</th><th>$5</th></tr></thead><tbody>$body</tbody></table>";        
        
        table = table.replace("$1", "ID");
        table = table.replace("$2", "BREDDE");
        table = table.replace("$3", "H&Oslash;JDE");
        table = table.replace("$4", "L&AElig;NGDE");
        table = table.replace("$5", "Se forespørgsel");
        
        for(ForesporgselDTO item : requests)
        {
            String row = "<tr><td>$1</td><td>$2</td><td>$3</td><td>$4</td><td>$5</td></tr>";
            row = row.replace("$1", String.valueOf(item.getId()));
            row = row.replace("$2", String.valueOf(item.getBredde()));
            row = row.replace("$3", String.valueOf(item.getHoejde()));
            row = row.replace("$4", String.valueOf(item.getLaengde()));
            row = row.replace("$5", "<a href=\"FrontController?command=" + Commands.SHOWSINGLEREQUEST + "&id=" + item.getId() + "\" class=\"btn btn-info btn-sm\">Se her</a> - "
                    + "<a href=\"FrontController?command=" + Commands.STYKLISTE + "&id=" + item.getId() + "\" class=\"btn btn-info btn-sm\">Se Styklist</a>");
            stringBuilder.append(row);
            
        }
        
        return table.replace("$body", stringBuilder.toString());
    }
}