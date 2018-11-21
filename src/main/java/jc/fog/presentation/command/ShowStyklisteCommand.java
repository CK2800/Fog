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
import jc.fog.logic.LogicFacade;
import jc.fog.logic.MaterialeDTO;
import jc.fog.logic.StyklisteItem;
import jc.fog.presentation.constants.Pages;

/**
 * Use this Command to display the bill of materials of a carport request.
 * 
 * @author Claus
 */
public class ShowStyklisteCommand extends Command
{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        // get request's id from request.
        int id = Integer.parseInt(request.getParameter("id"));
        // get request.
        ForesporgselDTO foresporgselDTO = DataFacade.getRequest(id);
        // get materials.
        List<MaterialeDTO> materialer = DataFacade.getMaterialer();
        // Calculate the bill of materials.
        List<StyklisteItem> stykliste = LogicFacade.beregnStykliste(foresporgselDTO, materialer);
        // Calculate string with carport dimensions.
        String carportDimensioner = String.valueOf(foresporgselDTO.getBredde()) + " x " + String.valueOf(foresporgselDTO.getLaengde()) + " cm.";
        
        // Format the bill of materials nicely for view.
        request.setAttribute("stykliste", styklisteToHtml(stykliste));
        request.setAttribute("carportDimensioner", carportDimensioner);
        
        return Pages.STYKLISTE;
    }
    
    private String styklisteToHtml(List<StyklisteItem> stykliste)
    {
        StringBuilder stringBuilder = new StringBuilder();
        String table = "<table class=\"table table-striped\"><thead><tr><th>$1</th><th>$2</th><th>$3</th><th>$4</th><th>$5</th></tr></thead><tbody>$body</tbody></table>";        
        
        table = table.replace("$1", "Antal");
        table = table.replace("$2", "Navn");
        table = table.replace("$3", "Materialetype");        
        table = table.replace("$4", "Længde");
        table = table.replace("$5", "Instruks");
        
        
        for(StyklisteItem item : stykliste)
        {
            String row = "<tr><td>$1</td><td>$2</td><td>$3</td><td>$4</td><td>$5</td></tr>";
            row = row.replace("$1", String.valueOf(item.getCount()).concat(" ").concat(item.getMaterialeDTO().getEnhed()));
            row = row.replace("$2", String.valueOf(item.getMaterialeDTO().getNavn()));
            row = row.replace("$3", String.valueOf(item.getMaterialeDTO().getMaterialetypeDTO().getType()));
            row = row.replace("$4", String.valueOf(item.getMaterialeDTO().getLaengde()));
            row = row.replace("$5", String.valueOf(item.getHjaelpetekst()));
            //row = row.replace("$6", "<a href=\"FrontController?command=showsinglemateriale&id=" + item.getId() + "\" class=\"btn btn-info btn-sm\">Se her</a>");
            stringBuilder.append(row);
        }
        
        return table.replace("$body", stringBuilder.toString());
    }
    
}
