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
        // get request's id from request.
        int id = Integer.parseInt(request.getParameter("id"));
                
        // Get DataFacade.
        DataFacade dataFacade = new DataFacade(DbConnector.getConnection());
        
        String requestForm = null;
        boolean viewUpdateQuest = false;//Den skal kun bliver vist hvis der fremkommer Tal ved "id'en".
        if(id > 0)
        {
            //Det her skal bliver vist hvis man skal updater indhold.
            CarportRequestDTO carportRequestDTO = dataFacade.getCarport(id);
            // Create HTML form with request's data and set it on http request.
            requestForm = carportRequestToBill(carportRequestDTO);
            
            
            //Fortæller at den skal fremvise styklisten her.
            viewUpdateQuest = true;
            //Her skal styklisten så fremkomme.
        }
        else
        {
            //Laver fejl fordi der ikke er angivet nogen id med noget tal.
            
            //Det her skal blive vist hvis man skal opret en forespørgelse.
            requestForm = carportRequestToForm();
        }
        
        request.setAttribute("requestForm", requestForm);
        request.setAttribute("viewBill", viewUpdateQuest);
        
        return Pages.SINGLE_CARPORTVIEW;
    }
    
    
    /**
     * Hjælpemetode som danner HTML formular baseret på CarportRequestDTO.
     * @param item
     * @return 
     */
    private String carportRequestToBill(CarportRequestDTO item)
    {
        StringBuilder stringBuilder = new StringBuilder("<form action=\"#\" method=\"POST\">");
        stringBuilder.append("<input type=\"hidden\" class=\"form-control\" disabled name=\"id\" readonly value=\"").append(item.getId()).append("\" />");
        stringBuilder.append("L&aelig;ngde:<br /><input type=\"text\" name=\"laengde\" class=\"form-control\" value=\"").append(item.getLength()).append("\" /><br />");
        stringBuilder.append("Bredde:<br /><input type=\"text\" name=\"bredde\" class=\"form-control\" value=\"").append(item.getWidth()).append("\" /><br />");
        stringBuilder.append("H&oslash;jde:<br /><input type=\"text\" class=\"form-control\" name=\"hoejde\" value=\"").append(item.getHeight()).append("\" /><br />");
        stringBuilder.append("H&aelig;ldning:<br /><input type=\"text\" class=\"form-control\" name=\"haeldning\" value=\"").append(item.getSlope()).append("\" /><br />");
        stringBuilder.append("Skur:<br /><input type=\"checkbox\" name=\"skur\"").append(item.getShedDTO() != null ? " checked" : "").append(" /><br />");
        
        if(item.getShedDTO() == null)
        {
            stringBuilder.append("Skur L&aelig;ngde:<br /><input type=\"text\" class=\"form-control\" name=\"laengde\" value=\"0\" /><br />");
            stringBuilder.append("Skur Bredde:<br /><input type=\"text\" class=\"form-control\" name=\"bredde\" value=\"0\" /><br />");
        }
        else
        {
            stringBuilder.append("Skur L&aelig;ngde:<br /><input type=\"text\" class=\"form-control\" name=\"laengde\" value=\"").append(item.getLength()).append("\" /><br />");
            stringBuilder.append("Skur Bredde:<br /><input type=\"text\" name=\"bredde\" class=\"form-control\" value=\"").append(item.getWidth()).append("\" /><br />");
        }
        stringBuilder.append("<br/>");
        stringBuilder.append("<input type=\"submit\" value=\"Tjek styklisten\" class=\"btn btn-success btn-block\" />");
        stringBuilder.append("</form><br/>");
        
        
        
        return stringBuilder.toString();
    }
    
    private String carportRequestToForm()
    {
        StringBuilder stringBuilder = new StringBuilder("<form action=\"#\" method=\"POST\">");
        stringBuilder.append("L&aelig;ngde:<br /><input type=\"text\" name=\"laengde\" class=\"form-control\" /><br />");
        stringBuilder.append("Bredde:<br /><input type=\"text\" name=\"bredde\" class=\"form-control\" /><br />");
        stringBuilder.append("H&oslash;jde:<br /><input type=\"text\" class=\"form-control\" name=\"hoejde\" /><br />");
        stringBuilder.append("H&aelig;ldning:<br /><input type=\"text\" class=\"form-control\" name=\"haeldning\" /><br />");
        stringBuilder.append("Skur:<br /><input type=\"checkbox\" name=\"skur\" /><br />");
        stringBuilder.append("Skur L&aelig;ngde:<br /><input type=\"text\" class=\"form-control\" name=\"laengde\" value=\"0\" /><br />");
        stringBuilder.append("Skur Bredde:<br /><input type=\"text\" class=\"form-control\" name=\"bredde\" value=\"0\" /><br />");
        stringBuilder.append("<br/>");
        stringBuilder.append("<input type=\"submit\" value=\"Gem\" class=\"btn btn-success btn-block\" />");
        stringBuilder.append("</form><br/>");
        
        
        
        return stringBuilder.toString();
    }
}
