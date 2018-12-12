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

/**
 *
 * @author Jespe
 */
public class ShowUserPasswordUpdate extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {        
        //Session bruges til, at kun opdater ens konto med ny adgangskode.
        HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO)session.getAttribute("user");

        //Få fat i id på user via session
        int id = user.getId();

        //få fat i den værdi som der er skrevet i input.
        String password = request.getParameter("password");


        DataFacade dataFacade = new DataFacadeImpl(DbConnector.getConnection());
        boolean succesPassword = dataFacade.updateUserPassword(password, id);

        if(succesPassword)
            return new ShowUserHomeCommand().execute(request, response);
        else
            return new ShowUserHomeCommand().execute(request, response);
    }
}