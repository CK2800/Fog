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
public class ShowAdminNewPasswordCommand extends Command {
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        String email = request.getParameter("email");
        
        DataFacade dataFacede = new DataFacadeImpl(DbConnector.getConnection());
        boolean newPassword = dataFacede.forgotPassword(email, null);
        if(newPassword)
            return new ShowAdminUsersCommand().execute(request, response);
        else
            return new ShowLoginCommand().execute(request, response);
    }
}
