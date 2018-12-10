/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.data.DataFacadeImpl;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.presentation.Pages;

/**
 *
 * @author Jespe
 */
public class ShowAddRegisterCommand extends Command
{
    
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        int phone = Integer.parseInt(request.getParameter("phone"));
        int zipcode = Integer.parseInt(request.getParameter("zipcode"));
        
        DataFacadeImpl dataFacade = new DataFacadeImpl(DbConnector.getConnection());
        
        int createUser = dataFacade.createUser(email, password, name, phone, zipcode);
        
        if(createUser > 0)
        {        
            return new ShowLoginCommand().execute(request, response);
        }
        else
        {
            return new ShowRegisterCommand().execute(request, response);
        }
    }

}
