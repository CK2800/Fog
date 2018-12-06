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
public class ShowLoginCheckCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        DataFacade dataFacade = new DataFacade(DbConnector.getConnection());
        UsersDTO user = dataFacade.login(email, password);

        HttpSession session = request.getSession();
        session.setAttribute("id", user.getId());
        session.setAttribute("rank", user.getRank());

        return Pages.INDEX;
    }
}
