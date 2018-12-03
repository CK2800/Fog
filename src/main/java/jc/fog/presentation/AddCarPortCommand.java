/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.data.DataFacade;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;

/**
 *
 * @author Jesper
 */
public class AddCarPortCommand extends Command
{
    
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        // Later we will validate a logged in user

        // nap parametre fra requests og dan CarportRequestDTO.
        int rooftypeId = 1; //Integer.parseInt(request.getParameter("rooftypeId"));
        int slope = Integer.parseInt(request.getParameter("slope"));
        int width = Integer.parseInt(request.getParameter("width"));
        int length = Integer.parseInt(request.getParameter("length"));
        String remark = request.getParameter("remark");

        int shedLength = Integer.parseInt(request.getParameter("shedLength"));
        int shedWidth = Integer.parseInt(request.getParameter("shedWidth"));
        
        DataFacade dataFacade = new DataFacade(DbConnector.getConnection());
        //husk at fjern højde som er 200
        boolean updateCarport = dataFacade.createCarPort(rooftypeId, slope, width, length, 200, shedWidth, shedLength, remark);
        
        
<<<<<<< HEAD
        // Return the page showing all requests.
        return Pages.ALL_CARPORTS;
=======
        if(createCarport)
        {
            return Pages.ALL_MATERIALS;
        }
        else
        {
            return Pages.INDEX;
        }
>>>>>>> FeatureClaus
    }
}
