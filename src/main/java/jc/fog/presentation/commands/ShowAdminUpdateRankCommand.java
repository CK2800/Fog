/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.data.DataFacade;
import jc.fog.data.DataFacadeImpl;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.presentation.Pages;

/**
 *
 * @author Jespe
 */
public class ShowAdminUpdateRankCommand extends Command {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        int id = Integer.parseInt(request.getParameter("id"));//Henter brugerid
        int rank = Integer.parseInt(request.getParameter("rank"));//Få afvide hvilken man har.
        
        int newRank = (rank == 5) ? 1 : 5;//Tildeler den nye rank som man skal ha.
        
        DataFacade dataFacede = new DataFacadeImpl(DbConnector.getConnection());
        boolean succesRank = dataFacede.setNewRankUser(id, newRank);
        
        if(succesRank)
            return new ShowAdminUsersCommand().execute(request, response);
        else
            return new ShowLoginCommand().execute(request, response);
    }
}
