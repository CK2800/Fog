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
public class ShowUserPasswordUpdate extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        try
        {
            //Session bruges til, at kun opdater ens konto med ny adgangskode.
            HttpSession session = request.getSession();
            UsersDTO user = (UsersDTO)session.getAttribute("user");
            // Har vi en user i session, er denne logget ind, gå til index side.
            if(user == null)
            {
                return Pages.INDEX;
            } 
            
            //få fat i den værdi som der er skrevet i input.
            String password = request.getParameter("password");            
            
            DataFacade dataFacade = new DataFacadeImpl(DbConnector.getConnection());
            boolean succesPassword = dataFacade.forgotPassword(user.getEmail(), password);
            
            if(succesPassword)
                return new ShowUserHomeCommand().execute(request, response);
            else
                return new ShowUserHomeCommand().execute(request, response);
        }
        catch(Exception e)
        {
            throw new FogException("Der gik noget galt da den skulle opdater password" + e.getMessage());
        }
    }
}
