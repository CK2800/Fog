/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.data.DataFacade;
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;
import jc.fog.logic.Rectangle;

/**
 * Use this command to show a page with the details
 * of a single carport request.
 * The id of the carport request must be set as a parameter
 * named 'requestId' in the HttpServletRequest object.
 * 
 * @author Claus
 */
public class ShowSingleRequestCommand extends Command
{
@Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        // get request's id from request.
        int id = Integer.parseInt(request.getParameter("id"));
        // get request.
        ForesporgselDTO foresporgselDTO = DataFacade.getCarPort(id);
        // Create HTML form with request's data and set it on http request.
        request.setAttribute("requestForm", requestToForm(foresporgselDTO));
        request.setAttribute("ForesporgselDTO", foresporgselDTO);
        
        List<Rectangle> rectangles = new ArrayList<Rectangle>();
        rectangles.add(new Rectangle(0, 0, 170, 450, "7FFF00"));
        rectangles.add(new Rectangle(15, 120, 125, 140, "D2691E"));
        
        request.setAttribute("svg", Drawing.drawSvg(rectangles, 200, 500));
        
        // return the page.
        return Pages.SINGLE_CARPORT;
    }
    
    /**
     * Hjælpemetode som danner HTML formular baseret på ForesporgselDTO.
     * @param item
     * @return 
     */
    private String requestToForm(ForesporgselDTO item)
    {
        StringBuilder stringBuilder = new StringBuilder("<form action=\"#\" method=\"POST\">");
        stringBuilder.append("<a class=\"btn btn-info btn-xs\" href=\"FrontController?command=" + Commands.SHOWREQUESTS +"\">Tilbage..</a><br/>");
        stringBuilder.append("Id:<br /><input type=\"text\" class=\"form-control\" disabled name=\"id\" readonly value=\"").append(item.getId()).append("\" /><br />");
        stringBuilder.append("L&aelig;ngde:<br /><input type=\"text\" name=\"laengde\" class=\"form-control\" value=\"").append(item.getLaengde()).append("\" /><br />");
        stringBuilder.append("Bredde:<br /><input type=\"text\" name=\"bredde\" class=\"form-control\" value=\"").append(item.getBredde()).append("\" /><br />");
        stringBuilder.append("H&oslash;jde:<br /><input type=\"text\" class=\"form-control\" name=\"hoejde\" value=\"").append(item.getHoejde()).append("\" /><br />");
        stringBuilder.append("H&aelig;ldning:<br /><input type=\"text\" class=\"form-control\" name=\"haeldning\" value=\"").append(item.getHaeldning()).append("\" /><br />");
        stringBuilder.append("Skur:<br /><input type=\"checkbox\" name=\"skur\"").append(item.getSkurDTO() != null ? " checked" : "").append(" /><br />");
        
        if(item.getSkurDTO() == null)
        {
            stringBuilder.append("Skur L&aelig;ngde:<br /><input type=\"text\" class=\"form-control\" name=\"laengde\" value=\"0\" /><br />");
            stringBuilder.append("Skur Bredde:<br /><input type=\"text\" class=\"form-control\" name=\"bredde\" value=\"0\" /><br />");
        }
        else
        {
            stringBuilder.append("Skur L&aelig;ngde:<br /><input type=\"text\" class=\"form-control\" name=\"laengde\" value=\"").append(item.getLaengde()).append("\" /><br />");
            stringBuilder.append("Skur Bredde:<br /><input type=\"text\" name=\"bredde\" class=\"form-control\" value=\"").append(item.getBredde()).append("\" /><br />");
        }
        stringBuilder.append("<br/>");
        stringBuilder.append("<input type=\"submit\" value=\"Gem\" class=\"btn btn-success btn-block\" />");
        stringBuilder.append("</form>");
        
        
        
        return stringBuilder.toString();
    }
}