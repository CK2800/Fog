/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jc.fog.logic.dto.UsersDTO;

import jc.fog.data.DataFacadeImpl;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.logic.dto.MaterialDTO;
import jc.fog.presentation.Pages;

/**
 *
 * @author Jespe
 */
public class ShowMaterialsCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        try
        {
            //sikker sig at man har den rigtigt rank for at kun se det her område.
            HttpSession session = request.getSession();
            UsersDTO user = (UsersDTO)session.getAttribute("user");
            // Har vi en user i session, er denne logget ind, gå til index side.
            if(user == null || user != null && user.getRank() > 1)
            {
                return Pages.INDEX;
            }
            
            DataFacadeImpl dataFacade = new DataFacadeImpl(DbConnector.getConnection());
            List<MaterialDTO> materials = dataFacade.getMaterials();
            request.setAttribute("materialTable", materialsToHtml(materials));
        
            return Pages.ALL_MATERIALS;
        }
        catch(Exception e)
        {
            throw new FogException("Der gik noget galt ved fremvis af materials" + e.getMessage());
        }
    }
    
    private String materialsToHtml(List<MaterialDTO> materials)
    {
        StringBuilder stringBuilder = new StringBuilder();
        String table = "<table class=\"table table-striped\"><thead><tr><th>$1</th><th>$2</th><th>$3</th><th>$4</th><th>$5</th><th>$6</th></tr></thead><tbody>$body</tbody></table>";        
        
        table = table.replace("$1", "ID");
        table = table.replace("$2", "Materialetype");
        table = table.replace("$3", "Navn");
        table = table.replace("$4", "Længde");
        table = table.replace("$5", "Enhed");
        table = table.replace("$6", "Se forespørgsel");
        
        for(MaterialDTO item : materials)
        {
            String row = "<tr><td>$1</td><td>$2</td><td>$3</td><td>$4</td><td>$5</td><td>$6</td></tr>";
            row = row.replace("$1", String.valueOf(item.getId()));
            row = row.replace("$2", String.valueOf(item.getMaterialtypeDTO().getType()));
            row = row.replace("$3", String.valueOf(item.getName()));
            row = row.replace("$4", String.valueOf(item.getLength()));
            row = row.replace("$5", String.valueOf(item.getUnit()));
            row = row.replace("$6", "<a href=\"FrontController?command=" + Commands.SHOW_SINGLE_MATERIAL + "&id=" + item.getId() + "\" class=\"btn btn-info btn-sm\">Se her</a>");
            stringBuilder.append(row);
        }
        
        return table.replace("$body", stringBuilder.toString());
    }
}
