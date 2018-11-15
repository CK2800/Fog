/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.command;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.data.DataFacade;
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;
import jc.fog.presentation.constants.Pages;

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
        List<ForesporgselDTO> requests = DataFacade.getRequests();
        
        // Save the list on request.
        request.setAttribute("requests", requests);
        
        // Return the page showing all requests.
        return Pages.ALL_REQUESTS;
    }
}
