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
import jc.fog.logic.UsersDTO;
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
        try
        {
            //sikker sig at man har den rigtigt rank for at kun se det her område.
            HttpSession session = request.getSession();
            UsersDTO user = (UsersDTO)session.getAttribute("user");
            // Har vi en user i session, er denne logget ind, gå til index side.
            if(user == null)
            {
                return Pages.INDEX;
            } 
            
            request.setAttribute("getUserName", user.getName());//bruger session med ens navn.
            
            return Pages.USER_HOME;
        }
        catch(Exception e)
        {
            throw new FogException("Den ikke vise forsiden til bruger - " + e.getMessage());
        }
    }
    
}
