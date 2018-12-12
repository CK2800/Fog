/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jc.fog.data.DataFacade;
import jc.fog.data.DataFacadeImpl;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.logic.dto.UsersDTO;
import jc.fog.presentation.Pages;

/**
 *
 * @author Jespe
 */
public class ShowUserHomeCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {   
        //sikker sig at man har den rigtigt rank for at kun se det her område.
        HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO)session.getAttribute("user");
        // Har vi en user i session, er denne logget ind, gå til index side.
        if(user != null && user.getRank() < 0)
        {
            return Pages.INDEX;
        } 

        //Få fat i vores id på user som vi skal brug til, at fremvise navn på konto siden.
        int userid = user.getId();

        DataFacade dataFacade = new DataFacadeImpl(DbConnector.getConnection());
        String getName = dataFacade.returnUserName(userid);//få bruges navn tilbage her.

        request.setAttribute("getUserName", getName);
        return Pages.USER_HOME;
    }
    
}
