/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jc.fog.exceptions.FogException;
import jc.fog.presentation.Pages;

/**
 *
 * @author Jespe
 */
public class LogoutCommand extends Command
{
    /**
     * Overskriver Command.execute, fordi både GET og POST requests skal gøre det samme.
     * @param request
     * @param response
     * @return
     * @throws FogException 
     */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);

        return Pages.INDEX;
    }

    @Override
    protected String doPost(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected String doGet(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
