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
 * @author Jespe
 */
public class UpdateCarPortCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        int id = Integer.parseInt(request.getParameter("carportId"));
        int shedId = Integer.parseInt(request.getParameter("shedId"));
        String shedCheck = request.getParameter("addSked");

        // nap parametre fra requests og dan CarportRequestDTO.
        int rooftypeId = 1; //Integer.parseInt(request.getParameter("rooftypeId"));
        int slope = Integer.parseInt(request.getParameter("slope"));
        int width = Integer.parseInt(request.getParameter("width"));
        int height = Integer.parseInt(request.getParameter("height"));//Bliver taget væk på et tidspunkt da vi mener at vi ikke kommer til, at bruge den.
        int length = Integer.parseInt(request.getParameter("length"));
        String remark = request.getParameter("remark");

        int shedLength = Integer.parseInt(request.getParameter("shedLength"));
        int shedWidth = Integer.parseInt(request.getParameter("shedWidth"));


        DataFacade dataFacade = new DataFacade(DbConnector.getConnection());
        boolean updateCarport = dataFacade.updateCarPort(id, shedId, shedCheck, slope, width, length, shedWidth, shedLength, rooftypeId, remark);
        
        if(updateCarport)
        {
            return Pages.ALL_CARPORTS;
        }
        else
        {
            return Pages.INDEX;
        }
    }
}
