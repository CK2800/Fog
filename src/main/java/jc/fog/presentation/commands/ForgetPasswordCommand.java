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
public class ForgetPasswordCommand extends Command
{
//    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
//    {
//        String email = request.getParameter("email");
//        
//        DataFacade dataFacade = new DataFacadeImpl(DbConnector.getConnection());
//        boolean password = dataFacade.forgotPassword(email, null);
//        if(password)            
//            return new ShowLoginCommand().execute(request, response);
//        else
//            return new ShowRegisterCommand().execute(request, response);
//    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        // efter denne command er kørt, skal klient redirectes til resultatet.
        clientRedirect = true;
        
        // Hent data fra request.
        String email = request.getParameter("email");
        
        DataFacade dataFacade = new DataFacadeImpl(DbConnector.getConnection());
        boolean password = dataFacade.forgotPassword(email, null);
        
        // Returner stier til FrontControlleren, som den kan bede browseren om at redirecte til.
        if (password)
            return Pages.REDIRECT_PREFIX + Commands.LOGIN;
        else
            return Pages.REDIRECT_PREFIX + Commands.REGISTER;
    }

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        throw new FogException("Der er sket en fejl.", "ForgetPassword er kaldt med GET. Command'et forventer POST.", null);
    }
}
