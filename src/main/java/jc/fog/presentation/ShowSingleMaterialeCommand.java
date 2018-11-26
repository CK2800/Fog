/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.data.DataFacade;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.logic.MaterialDTO;



/**
 *
 * @author Jespe
 */
public class ShowSingleMaterialeCommand extends Command {
    
    
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        int getId = Integer.parseInt(request.getParameter("id"));
        DataFacade dataFacade = new DataFacade(DbConnector.getConnection());
        MaterialDTO materialDTO = dataFacade.getMaterial(getId);
        request.setAttribute("materialeForm", materialToForm(materialDTO));
        
        
        return Pages.SINGLE_MATERIAL;
    }
    
    private String materialToForm(MaterialDTO item)
    {
        StringBuilder stringBuilder = new StringBuilder("<form action=\"#\" method=\"POST\">");
        
        stringBuilder.append("Id:<br /><input type=\"text\" class=\"form-control\" disabled name=\"id\" readonly value=\"").append(item.getId()).append("\" /><br />");
        stringBuilder.append("Navn:<br /><input type=\"text\" class=\"form-control\" name=\"navn\" value=\"").append(item.getName()).append("\" /><br />");
        stringBuilder.append("Enhed:<br /><input type=\"text\" class=\"form-control\" name=\"enhed\" value=\"").append(item.getUnit()).append("\" /><br />");
        stringBuilder.append("Materiale Type:<br /><input type=\"text\" class=\"form-control\" name=\"materialeType\" value=\"").append(item.getMaterialtypeDTO().getType()).append("\" /><br />");
        stringBuilder.append("Længde:<br /><input type=\"text\" class=\"form-control\" name=\"laengde\" value=\"").append(item.getLength()).append("\" /><br />");

        stringBuilder.append("<br/>");
        stringBuilder.append("<input type=\"submit\" value=\"Gem\" class=\"btn btn-success btn-block\" />");
        stringBuilder.append("</form>");
        
        stringBuilder.append("<a href=\"FrontController?command=showmateriale\">Tilbage..</a>");
        
        return stringBuilder.toString();
    }
    
    
}
