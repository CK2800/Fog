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
import jc.fog.exceptions.FogException;
import jc.fog.logic.UsersDTO;

public class ShowLoginCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        try
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
        catch(Exception e)
        {
            throw new FogException("Login side kan ikke vises, prøv igen.", e.getMessage());
        }
    }
    
    /**
     * Fremviser login form.
     * @return 
     */
    private String login()
    {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append("<form action=\"FrontController\" method=\"POST\">");
        stringbuilder.append("E-mail (Brugernavn):<br /><input type=\"text\" name=\"email\" class=\"form-control\" placeholder=\"Din Email\" /><br />");
        stringbuilder.append("Adgangskode<br /><input type=\"password\" name=\"password\" class=\"form-control\" placeholder=\"Din adgangskode\" /><br />");
        stringbuilder.append("<input type=\"submit\"formaction=\"/Fog/FrontController?command=" + Commands.LOGIN_CHECK + "\" class=\"btn btn-info btn-block\" value=\"Login\" \">");
        stringbuilder.append("<input type=\"submit\"formaction=\"/Fog/FrontController?command=" + Commands.FORGET + "\" class=\"btn btn-info btn-block\" value=\"Login\" \">");
        stringbuilder.append("</form><br/>");
        
        return stringbuilder.toString();
    }
}
