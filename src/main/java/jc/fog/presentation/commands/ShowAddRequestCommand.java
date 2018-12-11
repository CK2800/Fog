/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import jc.fog.presentation.commands.ShowRequestsCommand;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.data.DataFacadeImpl;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.presentation.Pages;

/**
 *
 * @author Jesper
 */
public class ShowAddRequestCommand extends Command
{
    
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        // Later we will validate a logged in user

        // nap parametre fra requests og dan CarportRequestDTO.
        int rooftypeId = Integer.parseInt(request.getParameter("rooftypeId"));
        int slope = Integer.parseInt(request.getParameter("slope"));
        int width = Integer.parseInt(request.getParameter("width"));
        int length = Integer.parseInt(request.getParameter("length"));
        String remark = request.getParameter("remark");

        int shedLength = Integer.parseInt(request.getParameter("shedLength"));
        int shedWidth = Integer.parseInt(request.getParameter("shedWidth"));
        
        DataFacadeImpl dataFacade = new DataFacadeImpl(DbConnector.getConnection());
        //husk at fjern højde som er 200
        boolean createRequest = dataFacade.createCarPort(rooftypeId, slope, width, length, 200, shedWidth, shedLength, remark);
        
        if (createRequest)
        {            
            // Kald ShowRequestCommand.execute.
            return new ShowRequestsCommand().execute(request, response);            
        }
        else
        {
            // Er vi her, er der sket en fejl. Returner til indtastningsside igen.
            
            //skal find ud af om vi skal lave den her om.
            return Pages.SINGLE_CARPORTVIEW;
        }
    }
}
