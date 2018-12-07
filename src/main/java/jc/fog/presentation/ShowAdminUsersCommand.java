/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jc.fog.data.DataFacade;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.logic.UsersDTO;

/**
 *
 * @author Jespe
 */
public class ShowAdminUsersCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        DataFacade dataFacade = new DataFacade(DbConnector.getConnection());
        
        
            //sikker sig at man har den rigtigt rank for at kun se det her område.
            HttpSession session = request.getSession();
            UsersDTO user = (UsersDTO)session.getAttribute("user");
            // Har vi en user i session, er denne logget ind, gå til index side.
            if(user != null && user.getRank() == 1)
            {
                return Pages.INDEX;
            } 
        
        return Pages.ADMIN_USER;
    }
}
