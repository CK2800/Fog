/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jc.fog.logic.dto.UsersDTO;
import jc.fog.exceptions.FogException;
import jc.fog.presentation.Pages;

public class ShowLoginCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {   
        HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO)session.getAttribute("user");
        // Har vi en user i session, er denne logget ind, gå til index side.
        if(user != null && user.getId() > 0)
        {
            return Pages.INDEX;
        }        

        // Ingen user i session => dan login formular og vis login side.
        request.setAttribute("login", login());
        return Pages.LOGIN;
    }
    
    /**
     * Fremviser login form.
     * @return 
     */
    private String login()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("<form action=\"FrontController\" method=\"POST\">");
        stringbuilder.append("E-mail (Brugernavn):<br /><input type=\"email\" pattern=\"[a-zA-Z0-9.-_]{1,}@[a-zA-Z.-]{2,}[.]{1}[a-zA-Z]{2,}\" name=\"email\" required class=\"form-control\" placeholder=\"Din Email\" /><br />");
        stringbuilder.append("Adgangskode<br /><input type=\"password\" name=\"password\" class=\"form-control\" placeholder=\"Din adgangskode\" /><br />");
        stringbuilder.append("<input type=\"submit\"formaction=\"/Fog/FrontController?command=" + Commands.LOGIN_CHECK + "\" class=\"btn btn-info btn-block\" value=\"Login\" \">");
        stringbuilder.append("<input type=\"submit\"formaction=\"/Fog/FrontController?command=" + Commands.FORGET_PASSWORD + "\" class=\"btn btn-info btn-block\" value=\"Ny adgangskode\" \">");
        stringbuilder.append("</form><br/>");
        
        return stringbuilder.toString();
    }
}
