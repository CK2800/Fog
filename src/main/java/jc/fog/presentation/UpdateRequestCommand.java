/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation;

import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.data.DataFacade;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.logic.CarportRequestDTO;

/**
 *
 * @author Jespe
 */
public class UpdateRequestCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        int id = Integer.parseInt(request.getParameter("carportId"));
        int shedId = Integer.parseInt(request.getParameter("shedId"));
        String shedCheck = request.getParameter("addSked");

        // nap parametre fra requests og dan CarportRequestDTO.
        int rooftypeId = Integer.parseInt(request.getParameter("rooftypeId"));
        int slope = Integer.parseInt(request.getParameter("slope"));
        int width = Integer.parseInt(request.getParameter("width"));
        int height = Integer.parseInt(request.getParameter("height"));//Bliver taget væk på et tidspunkt da vi mener at vi ikke kommer til, at bruge den.
        int length = Integer.parseInt(request.getParameter("length"));
        String remark = request.getParameter("remark");

        int shedLength = Integer.parseInt(request.getParameter("shedLength"));
        int shedWidth = Integer.parseInt(request.getParameter("shedWidth"));


        DataFacade dataFacade = new DataFacade(DbConnector.getConnection());
        boolean updateRequest = dataFacade.updateRequest(id, shedId, shedCheck, slope, width, length, shedWidth, shedLength, rooftypeId, remark);
        // Hvis opdatering er ok, gå til side med alle carporte.
        if (updateRequest)
        {
            try
            {
                // Kald ShowRequestCommand.execute.
                return new ShowRequestsCommand().execute(request, response);
            }
            catch(Exception e)
            {
                throw new FogException("Listen med forespørgsler kan ikke vises, ring til Jesper", e.getMessage());
            }
        }
        else
        {
            // Er vi her, er der sket en fejl. Returner til indtastningsside igen.
            return Pages.SINGLE_CARPORTVIEW;
        }
        
    }
}
