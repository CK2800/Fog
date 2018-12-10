/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.data.DataFacadeImpl;
import jc.fog.exceptions.FogException;
import jc.fog.presentation.Pages;

public class ShowLoginCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
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
        stringbuilder.append("E-mail (Brugernavn):<br /><input type=\"text\" name=\"email\" class=\"form-control\" placeholder=\"Din Email\" /><br />");
        stringbuilder.append("Adgangskode<br /><input type=\"password\" name=\"password\" class=\"form-control\" placeholder=\"Din adgangskode\" /><br />");
        stringbuilder.append("<input type=\"submit\"formaction=\"/Fog/FrontController?command=" + Commands.LOGIN_CHECK + "\" class=\"btn btn-info btn-block\" value=\"Login\" \">");
        stringbuilder.append("<input type=\"submit\"formaction=\"/Fog/FrontController?command=" + Commands.FORGOT + "\" class=\"btn btn-info btn-block\" value=\"Ny adgangskode\" \">");
        stringbuilder.append("</form><br/>");
        
        return stringbuilder.toString();
    }
}
