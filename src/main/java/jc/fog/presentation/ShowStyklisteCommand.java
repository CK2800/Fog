/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.data.DataFacade;
import jc.fog.exceptions.FogException;
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.LogicFacade;
import jc.fog.logic.MaterialeDTO;
import jc.fog.logic.BillItem;

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
        CarportRequestDTO foresporgselDTO = DataFacade.getCarPort(id);
        // get materials.
        List<MaterialeDTO> materialer = DataFacade.getMaterials();
        // Calculate the bill of materials.
        List<BillItem> stykliste = LogicFacade.beregnStykliste(foresporgselDTO, materialer);
        // Calculate string with carport dimensions.
        String carportDimensioner = String.valueOf(foresporgselDTO.getWidth()) + " x " + String.valueOf(foresporgselDTO.getLength()) + " cm.";
        
        // Format the bill of materials nicely for view.
        request.setAttribute("stykliste", styklisteToHtml(stykliste));
        request.setAttribute("carportDimensioner", carportDimensioner);
        
        return Pages.BILL;
    }
    
    private String styklisteToHtml(List<BillItem> stykliste)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<a href=\"FrontController?command=" + Commands.SHOWREQUESTS + "\">Tilbage..</a>");
        String table = "<table class=\"table table-striped\"><thead><tr><th>$1</th><th>$2</th><th>$3</th><th>$4</th><th>$5</th></tr></thead><tbody>$body</tbody></table>";        
        
        table = table.replace("$1", "Antal");
        table = table.replace("$2", "Navn");
        table = table.replace("$3", "Materialetype");        
        table = table.replace("$4", "Længde");
        table = table.replace("$5", "Instruks");
        
        
        for(BillItem item : stykliste)
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
