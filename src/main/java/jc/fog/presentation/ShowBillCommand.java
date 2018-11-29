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
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.LogicFacade;
import jc.fog.logic.MaterialDTO;
import jc.fog.logic.BillItem;

/**
 * Use this Command to display the bill of materials of a carport request.
 * 
 * @author Claus
 */
public class ShowBillCommand extends Command
{

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        // Se om der er en id i request, for så skal vi hente carportrequest fra db.
        int id = 0;
        CarportRequestDTO carportRequestDTO;
        // Create DataFacade
        DataFacade dataFacade = new DataFacade(DbConnector.getConnection());
        // Har vi et gyldigt id ?
        try
        {
            // Findes id ikke på requestet, catcher vi exception
            id = Integer.parseInt(request.getParameter("id"));
        }
        catch(NumberFormatException n){
            // NumberFormatException er forventet, hvis request ikke har id, som så vil være 0.
        }
        if(id > 0)
        {        
            // get request.
            carportRequestDTO = dataFacade.getCarport(id);
        }
        else
        {
            int shedLength, shedWidth;
            
            //Ønsker man Skur til carport.
            boolean addSked = "1".equals(request.getParameter("addSked"));
            
            // nap parametre fra requests og dan CarportRequestDTO.
            int rooftypeId = 1; //Integer.parseInt(request.getParameter("rooftypeId"));
            int slope = Integer.parseInt(request.getParameter("slope"));
            int width = Integer.parseInt(request.getParameter("width"));
            int height = Integer.parseInt(request.getParameter("height"));
            int length = Integer.parseInt(request.getParameter("length"));
            String remark = request.getParameter("remark");//Hvad skal vi har gjort med den her ? Da den ikke vil ha nogen betydning i forhold til at beregn som FOG?
            
            if(addSked)//Man skal har klikket af ved at man ønsker Skur for at tilføj de værdi med.
            {
                shedLength = Integer.parseInt(request.getParameter("shedLength"));
                shedWidth = Integer.parseInt(request.getParameter("shedWidth"));
            }
            else
            {
                shedLength = 0;
                shedWidth = 0;
            }
            
            carportRequestDTO = new CarportRequestDTO(rooftypeId, slope, width, height, length, remark, shedLength, shedWidth);
        }
        // get materials.
        List<MaterialDTO> materials = dataFacade.getMaterials();
        // Calculate the bill of materials.
        List<BillItem> bill = LogicFacade.calculateBill(carportRequestDTO, materials);
        // Calculate string with carport dimensions.
        String carportDimensions = String.valueOf(carportRequestDTO.getWidth()) + " x " + String.valueOf(carportRequestDTO.getLength()) + " cm.";
        
        // Format the bill of materials nicely for view.
        request.setAttribute("stykliste", billToHtml(bill));
        request.setAttribute("carportDimensioner", carportDimensions);
        
        return Pages.BILL;
    }
    
    private String billToHtml(List<BillItem> bill)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<input name=\"action\" type=\"submit\" value=\"Tilbage\" onclick=\"window.history.back();\"/>");
        String table = "<table class=\"table table-striped\"><thead><tr><th>$1</th><th>$2</th><th>$3</th><th>$4</th><th>$5</th></tr></thead><tbody>$body</tbody></table>";        
        
        table = table.replace("$1", "Antal");
        table = table.replace("$2", "Navn");
        table = table.replace("$3", "Materialetype");        
        table = table.replace("$4", "Længde");
        table = table.replace("$5", "Instruks");
        
        
        for(BillItem item : bill)
        {
            String row = "<tr><td>$1</td><td>$2</td><td>$3</td><td>$4</td><td>$5</td></tr>";
            row = row.replace("$1", String.valueOf(item.getCount()).concat(" ").concat(item.getMaterialDTO().getUnit()));
            row = row.replace("$2", String.valueOf(item.getMaterialDTO().getName()));
            row = row.replace("$3", String.valueOf(item.getMaterialDTO().getMaterialtypeDTO().getType()));
            row = row.replace("$4", String.valueOf(item.getMaterialDTO().getLength()));
            row = row.replace("$5", String.valueOf(item.getRemarks()));
            //row = row.replace("$6", "<a href=\"FrontController?command=showsinglemateriale&id=" + item.getId() + "\" class=\"btn btn-info btn-sm\">Se her</a>");
            stringBuilder.append(row);
        }
        
       
        
        return table.replace("$body", stringBuilder.toString());
    }
    
}
