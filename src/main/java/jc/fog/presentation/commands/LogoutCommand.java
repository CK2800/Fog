/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import jc.fog.presentation.commands.Command;
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
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        HttpSession session = request.getSession();
        session.setAttribute("user", null);

        return Pages.INDEX;
    }
}
