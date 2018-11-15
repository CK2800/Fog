/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.data.DataFacade;
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;
import jc.fog.presentation.constants.Pages;

/**
 * Use this command to show a page with the details
 * of a single carport request.
 * The id of the carport request must be set as a parameter
 * named 'requestId' in the HttpServletRequest object.
 * 
 * @author Claus
 */
public class ShowSingleRequestCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        // get requestId from request.
        int requestId = Integer.parseInt(request.getParameter("requestId"));
        // get request and set it on http request.
        ForesporgselDTO foresporgselDTO = DataFacade.getRequest(requestId);
        request.setAttribute("ForesporgselDTO", foresporgselDTO);
        
        // return the page.
        return Pages.SINGLE_REQUEST;
    }
}
