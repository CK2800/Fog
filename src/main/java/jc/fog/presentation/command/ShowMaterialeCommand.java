/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.command;

import java.util.List;
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
public class ShowMaterialeCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        List<MaterialeDTO> getListMateriale = DataFacade.getMaterialer();
        request.setAttribute("materialeTable", requestsToHtml(getListMateriale));
        
        return Pages.ALL_MATERIALE;
    }
    
    private String requestsToHtml(List<MaterialeDTO> requests)
    {
        StringBuilder stringBuilder = new StringBuilder();
        String table = "<table class=\"table table-striped\"><thead><tr><th>$1</th><th>$2</th><th>$3</th><th>$4</th><th>$5</th><th>$6</th></tr></thead><tbody>$body</tbody></table>";        
        
        table = table.replace("$1", "ID");
        table = table.replace("$2", "Materialetype");
        table = table.replace("$3", "Navn");
        table = table.replace("$4", "Længde");
        table = table.replace("$5", "Enhed");
        table = table.replace("$6", "Se forespørgsel");
        
        for(MaterialeDTO item : requests)
        {
            String row = "<tr><td>$1</td><td>$2</td><td>$3</td><td>$4</td><td>$5</td><td>$6</td></tr>";
            row = row.replace("$1", String.valueOf(item.getId()));
            row = row.replace("$2", String.valueOf(item.getMaterialetypeDTO().getType()));
            row = row.replace("$3", String.valueOf(item.getNavn()));
            row = row.replace("$4", String.valueOf(item.getLaengde()));
            row = row.replace("$5", String.valueOf(item.getEnhed()));
            row = row.replace("$6", "<a href=\"FrontController?command=showsinglemateriale&id=" + item.getId() + "\" class=\"btn btn-info btn-sm\">Se her</a>");
            stringBuilder.append(row);
        }
        
        return table.replace("$body", stringBuilder.toString());
    }
}
