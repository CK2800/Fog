/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.data.DataFacade;
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;
import jc.fog.logic.MaterialeDTO;
import jc.fog.presentation.constants.Pages;

/**
 *
 * @author Jespe
 */
public class ShowSingleMaterialeCommand extends Command {
    
    
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        int getId = Integer.parseInt(request.getParameter("id"));
        MaterialeDTO materialeDTO = DataFacade.getMateriale(getId);
        request.setAttribute("materialeForm", requestToForm(materialeDTO));
        
        return Pages.SINGLE_MATERIALE;
    }
    
    private String requestToForm(MaterialeDTO item)
    {
        StringBuilder stringBuilder = new StringBuilder("<form action=\"#\" method=\"POST\">");
        
        stringBuilder.append("Id:<br /><input type=\"text\" class=\"form-control\" disabled name=\"id\" readonly value=\"").append(item.getId()).append("\" /><br />");
        stringBuilder.append("Navn:<br /><input type=\"text\" class=\"form-control\" name=\"id\" readonly value=\"").append(item.getNavn()).append("\" /><br />");
        stringBuilder.append("Enhed:<br /><input type=\"text\" class=\"form-control\" name=\"id\" readonly value=\"").append(item.getEnhed()).append("\" /><br />");
        stringBuilder.append("Materiale Type:<br /><input type=\"text\" class=\"form-control\" name=\"id\" readonly value=\"").append(item.getMaterialetypeDTO().getType()).append("\" /><br />");
        stringBuilder.append("Længde:<br /><input type=\"text\" class=\"form-control\" name=\"id\" readonly value=\"").append(item.getLaengde()).append("\" /><br />");

        stringBuilder.append("<br/>");
        stringBuilder.append("<input type=\"submit\" value=\"Gem\" class=\"btn btn-success btn-block\" />");
        stringBuilder.append("</form>");
        
        return stringBuilder.toString();
    }
}
