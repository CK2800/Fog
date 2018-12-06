/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jc.fog.exceptions.FogException;

/**
 *
 * @author Jespe
 */
public class Logout extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        HttpSession session = request.getSession();
        session.setAttribute("id", null);
        session.setAttribute("rank", null);

        return Pages.INDEX;
    }
}
