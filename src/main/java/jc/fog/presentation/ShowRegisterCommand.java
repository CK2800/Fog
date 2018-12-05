/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.exceptions.FogException;

/**
 *
 * @author Jespe
 */
public class ShowRegisterCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        
        
        
        request.setAttribute("loginForm", loginForm());
        
        
        return Pages.SINGLE_DRAW;
    }
    
    private String loginForm()
    {
        //Navn-c postnr telefon-c emai-c adgangskode-c
        
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<form action=\"FrontController\" method=\"POST\">");
        stringBuilder.append("E-mail (Brugernavn):<br /><input type=\"text\" name=\"email\" class=\"form-control\" placeholder=\"Din Email\" /><br />");
        stringBuilder.append("Adgangskode<br /><input type=\"text\" name=\"password\" class=\"form-control\" placeholder=\"Din adgangskode\" /><br />");
        stringBuilder.append("Navn:<br /><input type=\"text\" name=\"email\" class=\"form-control\" placeholder=\"Din Email\" /><br />");
        stringBuilder.append("Telefon nr:<br /><input type=\"text\" name=\"email\" class=\"form-control\" placeholder=\"Din Email\" /><br />");
        
        //Dropdown
        
        stringBuilder.append("<input type=\"submit\" class=\"btn btn-info btn-block\" value=\"Log ind\" \">");
        stringBuilder.append("</form><br/>");
        
        return stringBuilder.toString();
    }
}
