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

/**
 *
 * @author Jespe
 */
public class Forgot extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        String email = request.getParameter("email");
        
        DataFacade dataFacade = new DataFacade(DbConnector.getConnection());
        boolean success = dataFacade.forgetUser(email);
        if(success)
        {
            return Pages.LOGIN;
        }
        else
        {
            return Pages.REGISTER;
        }
    }
}
