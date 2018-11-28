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
import jc.fog.logic.CarportRequestDTO;

/**
 *
 * @author Jespe
 */
public class ShowCarPortCommand  extends Command
{
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        // get request's id from request hvis den findes.        
        int id = 0;        
                       
        // Get DataFacade.
        DataFacade dataFacade = new DataFacade(DbConnector.getConnection());
        
        String requestForm = null;
        boolean viewUpdateQuest = true;//skal lige overvej om det er noget vi overhovedet kommer til, at bruge..
        

        // Har vi et gyldigt id ?
        try
        {
            // Findes id ikke på requestet, catcher vi exception
            id = Integer.parseInt(request.getParameter("id"));
        }
        catch(NumberFormatException n){
            // NumberFormatException er forventet, hvis request ikke har id, som så vil være 0.
        }
        // Fandt vi et gyldigt id på requestet (dvs. > 0)
        if(id > 0)
        {
            //Det her skal bliver vist hvis man skal updater indhold.
            CarportRequestDTO carportRequestDTO = dataFacade.getCarport(id);
            // Create HTML form with request's data and set it on http request.
            requestForm = carportRequestToBill(carportRequestDTO,viewUpdateQuest);
            
            
            //Fortæller at den skal fremvise styklisten her.
            
            //Her skal styklisten så fremkomme.
        }
        else // Ingen id i requestet, lav en tom formular til ny oprettelse af carportrequest
        {            
            
            viewUpdateQuest = false;
            //Det her skal blive vist hvis man skal opret en forespørgelse.
            requestForm = carportRequestToBill(null, viewUpdateQuest);
        }
        
        request.setAttribute("requestForm", requestForm);//Det er form til den enkelt som skal bruges
        request.setAttribute("viewBill", viewUpdateQuest);//Det er i forhold til om man skal opret eller ret mv.
        
        return Pages.SINGLE_CARPORTVIEW;
    }
    
    
    /**
     * Hjælpemetode som danner HTML formular baseret på CarportRequestDTO.
     * @param item
     * @return 
     */
    private String carportRequestToBill(CarportRequestDTO item, boolean bill)
    {
        StringBuilder stringBuilder = new StringBuilder("<form action=\"#\" method=\"POST\">");
        
        //width, length osv.
        stringBuilder.append("<input type=\"hidden\" class=\"form-control\" disabled name=\"id\" readonly value=\"$1\" />");
        stringBuilder.append("L&aelig;ngde:<br /><input type=\"text\" name=\"laengde\" class=\"form-control\" value=\"$2\" /><br />");
        stringBuilder.append("Bredde:<br /><input type=\"text\" name=\"bredde\" class=\"form-control\" value=\"$3\" /><br />");
        stringBuilder.append("H&oslash;jde:<br /><input type=\"text\" class=\"form-control\" name=\"hoejde\" value=\"$4\" /><br />");
        stringBuilder.append("H&aelig;ldning:<br /><input type=\"text\" class=\"form-control\" name=\"haeldning\" value=\"$5\" /><br />");
        stringBuilder.append("Skur:<br /><input type=\"checkbox\" name=\"skur\"").append(item.getShedDTO() != null ? " checked" : "").append(" /><br />");
        
        stringBuilder.append("Skur L&aelig;ngde:<br /><input type=\"text\" class=\"form-control\" name=\"laengde\" value=\"$6\" /><br />");
        stringBuilder.append("Skur Bredde:<br /><input type=\"text\" name=\"bredde\" class=\"form-control\" value=\"$7\" /><br />");
        stringBuilder.append("<br/>");
        stringBuilder.append("<input type=\"submit\" value=\"$8\" class=\"btn btn-success btn-block\" />");
        stringBuilder.append("</form><br/>");
        
        String text = stringBuilder.toString();
        if (item != null)
        {
          // id   
            text = text.replace("$1", String.valueOf(item.getId()));
            text = text.replace("$2", String.valueOf(item.getLength()));
            text = text.replace("$3", String.valueOf(item.getWidth()));
            text = text.replace("$4", String.valueOf(item.getHeight()));
            text = text.replace("$5", String.valueOf(item.getSlope()));
            text = text.replace("$6", String.valueOf(item.getLength()));
            text = text.replace("$7", String.valueOf(item.getWidth()));  
            
            text = text.replace("$8", "");  
        }
        else
        {
            text = text.replace("$1", "");
            text = text.replace("$2", "");
            text = text.replace("$3", "");
            text = text.replace("$4", "");
            text = text.replace("$5", "");
            text = text.replace("$6", "");
            text = text.replace("$7", ""); 
            text = text.replace("$8", "");
        }
        return text.toString();
    }
}
