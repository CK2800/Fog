/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jc.fog.data.DataFacadeImpl;
import jc.fog.logic.dto.UsersDTO;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.logic.dto.MaterialDTO;
import jc.fog.presentation.Pages;



/**
 *
 * @author Jespe
 */
public class ShowSingleMaterialeCommand extends Command {
    
    
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        try {
            //sikker sig at man har den rigtigt rank for at kun se det her område.
            HttpSession session = request.getSession();
            UsersDTO user = (UsersDTO)session.getAttribute("user");
            // Har vi en user i session, er denne logget ind, gå til index side.
            if(user == null || user != null && user.getRank() > 1)
            {
                return Pages.INDEX;
            }
            
            int getId = Integer.parseInt(request.getParameter("id"));
            DataFacadeImpl dataFacade = new DataFacadeImpl(DbConnector.getConnection());
            MaterialDTO materialDTO = dataFacade.getMaterial(getId);
            request.setAttribute("materialeForm", materialToForm(materialDTO));


            return Pages.SINGLE_MATERIAL;
        }
        catch(NumberFormatException n)
        {
            throw new FogException("Materialet kan ikke vises.", n.getMessage(), n);
        }     
        catch(FogException f)
        {
            // kastes blot videre.
            throw f;
        }        
    }
    
    private String materialToForm(MaterialDTO item)
    {
        StringBuilder stringBuilder = new StringBuilder("<form action=\"#\" method=\"POST\">");
        stringBuilder.append("<input type=\"hidden\" class=\"form-control\" disabled name=\"id\" readonly value=\"").append(item.getId()).append("\" />");
        stringBuilder.append("Navn:<br /><input type=\"text\" required class=\"form-control\" name=\"navn\" value=\"").append(item.getName()).append("\" /><br />");
        stringBuilder.append("Enhed:<br /><input type=\"text\" required class=\"form-control\" name=\"enhed\" value=\"").append(item.getUnit()).append("\" /><br />");
        stringBuilder.append("Materiale Type:<br /><input type=\"text\" required class=\"form-control\" name=\"materialeType\" value=\"").append(item.getMaterialtypeDTO().getType()).append("\" /><br />");
        stringBuilder.append("Længde:<br /><input type=\"number\" max=\"900\" class=\"form-control\" name=\"laengde\" value=\"").append(item.getLength()).append("\" /><br />");

        stringBuilder.append("<br/>");
        stringBuilder.append("<input type=\"submit\" value=\"Gem\" class=\"btn btn-success btn-block\" />");
        stringBuilder.append("</form>");
        
        
        
        return stringBuilder.toString();
    }
    
    
}
