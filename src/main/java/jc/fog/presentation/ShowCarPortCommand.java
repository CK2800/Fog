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
        
        //skal lige overvej om det er noget vi overhovedet kommer til, at bruge.. Fx om man er medarbejder eller ej?
        boolean viewUpdateQuest = true;
        
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
        }
        else 
        {
            // Ingen id i requestet, lav en tom formular til ny oprettelse af carportrequest                        
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
        StringBuilder stringBuilder = new StringBuilder("<form action=\"FrontController\" method=\"POST\">"
                + "<input type=\"hidden\" name=\"command\" value=\"" + Commands.SHOW_BILL + "\">");
        
        if(item != null)
        {
            stringBuilder.append("<input type=\"hidden\" name=\"shedId\" value=\"$shedId\" />");
            stringBuilder.append("<input type=\"hidden\" name=\"carportId\" value=\"$id\" />");
        }
        
        //Carport information her
        stringBuilder.append("L&aelig;ngde:<br /><input type=\"text\" name=\"length\" class=\"form-control\" value=\"$carport1\" /><br />");
        stringBuilder.append("Bredde:<br /><input type=\"text\" name=\"width\" class=\"form-control\" value=\"$carport2\" /><br />");
        stringBuilder.append("H&oslash;jde:<br /><input type=\"text\" class=\"form-control\" name=\"height\" value=\"$carport3\" /><br />");
        stringBuilder.append("H&aelig;ldning:<br /><input type=\"text\" class=\"form-control\" name=\"slope\" value=\"$carport4\" /><br />");
        
        //Shed information her
        stringBuilder.append("Skur:<br /><input type=\"checkbox\" name=\"addSked\" $shed1 /><br />");
        stringBuilder.append("Skur L&aelig;ngde:<br /><input type=\"text\" class=\"form-control\" name=\"shedLength\" value=\"$shed2\" /><br />");
        stringBuilder.append("Skur Bredde:<br /><input type=\"text\" name=\"shedWidth\" class=\"form-control\" value=\"$shed3\" /><br />");
        
        //Kommentar fra kunden.
        stringBuilder.append("Kommentar:<br /><input type=\"text\" name=\"remark\" class=\"form-control\" value=\"$carport5\" /><br />");
        
        //Dropdown i forhold til type af tag.
        //KOmmer her med dropdown.
        stringBuilder.append("<br/>");
        
        //Her kommer submit som skal updatere eller beregn styklisten
        if(bill)
        {
            stringBuilder.append("<input type=\"submit\" formaction=\"/Fog/FrontController?command=" + Commands.UPDATE_CARPORT_REQUEST + "\" value=\"$submit1\" class=\"btn btn-success btn-block\" />");
        }
        else
        {
            stringBuilder.append("<input type=\"submit\" formaction=\"/Fog/FrontController?command=" + Commands.ADD_CARPORT_REQUEST + "\" value=\"$submit1\" class=\"btn btn-success btn-block\" />");
        }
        
        stringBuilder.append("<input type=\"submit\" class=\"btn btn-info btn-block\" value=\"$submit2\" \"><br/>");
        stringBuilder.append("</form><br/>");
        
        String text = stringBuilder.toString();
        
        if (item != null)
        {
            //Id'er til det angivet punkt/område i forhold til hvis det skal opdatere.
            text = text.replace("$shedId", String.valueOf(item.getShedId()));       
            text = text.replace("$id", String.valueOf(item.getId()));
            
            //Carport område
            text = text.replace("$carport1", String.valueOf(item.getLength()));
            text = text.replace("$carport2", String.valueOf(item.getWidth()));
            text = text.replace("$carport3", String.valueOf(item.getHeight()));
            text = text.replace("$carport4", String.valueOf(item.getSlope()));
            
            //Shed område
            String textShedId = "";
            if(item.getShedId() > 0)
            {
                textShedId = "check";
            }
            
            if(item.getShedId() != 0)
            {
                text = text.replace("$shed2", String.valueOf(item.getShedDTO().getLength()));
                text = text.replace("$shed3", String.valueOf(item.getShedDTO().getWidth()));
            }
            else
            {
                text = text.replace("$shed2", "0");
                text = text.replace("$shed3", "0");
            }
            text = text.replace("$shed1", textShedId);
            
            //Kunde kommentar
            text = text.replace("$carport5", String.valueOf(item.getRemark()));
            
            //Det som der skal blive vist af tekst ved "Submit" område.
            text = text.replace("$submit1", "Updater indhold");
            text = text.replace("$submit2", "Beregn stykliste");
        }
        else
        {
            
            //Carport område
            text = text.replace("$carport1", "");
            text = text.replace("$carport2", "");
            text = text.replace("$carport3", "");
            text = text.replace("$carport4", "");
            
            //Shed område
            text = text.replace("$shed2", "");
            text = text.replace("$shed3", "");
            text = text.replace("$shed1", "");
            
            //Kunde kommentar
            text = text.replace("$carport5", "");
            
            //Det som der skal blive vist af tekst ved "Submit" område.
            text = text.replace("$submit1", "Opret forespørgelse");
            text = text.replace("$submit2", "Beregn stykliste");
            
        }
        return text;
    }
}
