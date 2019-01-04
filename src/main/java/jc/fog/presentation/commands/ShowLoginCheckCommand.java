/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jc.fog.data.DataFacadeImpl;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.logic.dto.UsersDTO;
import jc.fog.presentation.Pages;

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
        
        DataFacadeImpl dataFacade = new DataFacadeImpl(DbConnector.getConnection());
        UsersDTO user = dataFacade.login(email, password);
        
        //tilføjer session på Rank og Id.
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        return Pages.INDEX;
    }
}
