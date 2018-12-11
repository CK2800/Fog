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
import jc.fog.data.DataFacadeImpl;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.RooftypeDTO;
import jc.fog.logic.UsersDTO;
import jc.fog.presentation.Pages;

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
                       
        // Get DataFacadeImpl.
        DataFacadeImpl dataFacade = new DataFacadeImpl(DbConnector.getConnection());
        String requestForm = null;

        HttpSession session = request.getSession();
        UsersDTO user = (UsersDTO)session.getAttribute("user");
        
        // Har vi et gyldigt id ?
        try
        {
            // Findes id ikke på requestet, catcher vi exception
            id = Integer.parseInt(request.getParameter("id"));
        }
        catch(NumberFormatException n){
            // NumberFormatException er forventet, hvis request ikke har id, som så vil være 0.
        }

        List<RooftypeDTO> Rooftypes = dataFacade.getRooftypes();

        // Fandt vi et gyldigt id på requestet (dvs. > 0)
        if(id > 0)
        {
            //Det her skal bliver vist hvis man skal updater indhold.
            CarportRequestDTO carportRequestDTO = dataFacade.getCarport(id);
            // Create HTML form with request's data and set it on http request.
            requestForm = carportRequestToBill(carportRequestDTO, user, Rooftypes);
        }
        else 
        {
            // Ingen id i requestet, lav en tom formular til ny oprettelse af carportrequest      

            //Det her skal blive vist hvis man skal opret en forespørgelse.
            requestForm = carportRequestToBill(null, user, Rooftypes);
        }

        request.setAttribute("requestForm", requestForm);//Det er form til den enkelt som skal bruges

        return Pages.SINGLE_CARPORTVIEW;
    }
    
    
    /**
     * Hjælpemetode som danner HTML formular baseret på CarportRequestDTO.
     * @param item
     * @return 
     */
    private String carportRequestToBill(CarportRequestDTO item, UsersDTO user, List<RooftypeDTO> Rooftypes)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<form action=\"FrontController\" method=\"POST\">"
                + "<input type=\"hidden\" name=\"command\" value=\"" + Commands.SHOW_BILL + "\">");
        
        //Det er shedid og forespørgelse id som fremkommer her.
        anyIDs(item, stringBuilder);
        
        //Bruger oplysning skal opret sig her hvis man er oprettet.
        if(user.getId() == 0)
        {
            //skal opret sig som bruger første.
        }
        else
        {
            //Er man admin eller bruger?
        }
        
        //Carport information her
        stringBuilder.append("L&aelig;ngde:<br /><input type=\"text\" name=\"length\" class=\"form-control\" value=\"$carport1\" placeholder=\"Længde på carport\" /><br />");
        stringBuilder.append("Bredde:<br /><input type=\"text\" name=\"width\" class=\"form-control\" value=\"$carport2\" placeholder=\"Bredde på carport\" /><br />");
        stringBuilder.append("H&oslash;jde:<br /><input type=\"text\" class=\"form-control\" name=\"height\" value=\"$carport3\" placeholder=\"Højde\" /><br />");
        stringBuilder.append("H&aelig;ldning:<br /><input type=\"text\" class=\"form-control\" name=\"slope\" value=\"$carport4\" placeholder=\"Hældning på taget\" /><br />");
        
        //Shed information her
        stringBuilder.append("Skur:<br /><input type=\"checkbox\" name=\"addSked\" $shed1 /><br />");
        stringBuilder.append("Skur L&aelig;ngde:<br /><input type=\"text\" class=\"form-control\" name=\"shedLength\" value=\"$shed2\" placeholder=\"Skur længde\" /><br />");
        stringBuilder.append("Skur Bredde:<br /><input type=\"text\" name=\"shedWidth\" class=\"form-control\" value=\"$shed3\" placeholder=\"Skur bredde\" /><br />");
        
        //Kommentar fra kunden.
        stringBuilder.append("Kommentar:<br /><input type=\"text\" name=\"remark\" class=\"form-control\" value=\"$carport5\" placeholder=\"Kommentar til forespørgelse\" /><br />");
        
        //Dropdown i forhold til type af tag.
        String dropdown = "Type:<br /><select class=\"form-control\" name=\"rooftypeId\"><option>V&aelig;lg tagtype:</option>$body</select>";
        String rows = "";
        String selectedCheck = "";//Bruges til, at fortælle option hvilken værdi man har valgt.
        rows = rooftypeDropdown(Rooftypes, item, selectedCheck, rows);
        dropdown = dropdown.replace("$body", rows);
        stringBuilder.append(dropdown);
        stringBuilder.append("<br/><br/>");
        
        
        //Her kommer submit som skal updatere eller beregn styklisten
        if(user != null && user.getRank() == 1)
        {
            stringBuilder.append("<input type=\"submit\" formaction=\"/Fog/FrontController?command=" + Commands.UPDATE_REQUEST + "\" value=\"$submit1\" class=\"btn btn-success btn-block\" />");
        }
        else
        {
            stringBuilder.append("<input type=\"submit\" formaction=\"/Fog/FrontController?command=" + Commands.ADD_REQUEST + "\" value=\"$submit1\" class=\"btn btn-success btn-block\" />");
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
            
            String textShedId = shedCheck(item);
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
            text = text.replace("$carport4", "0");
            
            //Shed område
            text = text.replace("$shed2", "0");
            text = text.replace("$shed3", "0");
            text = text.replace("$shed1", "");
            
            //Kunde kommentar
            text = text.replace("$carport5", "");
            
            //Det som der skal blive vist af tekst ved "Submit" område.
            text = text.replace("$submit1", "Opret forespørgelse");
            text = text.replace("$submit2", "Beregn stykliste");
            
        }
        return text;
    }

    /**
     * Bruges til, at fortælle om den angivet "checkbox" er tilføjet eller ej?
     * @param item
     * @return 
     */
    private String shedCheck(CarportRequestDTO item) {
        return item.getShedId() > 0 ? "checked" : "";
    }

    /**
     * Den gennemløber "for" og tilføj de forskellig værdier fra db i "option"
     * @param Rooftypes
     * @param item
     * @param selectedCheck
     * @param rows
     * @return 
     */
    private String rooftypeDropdown(List<RooftypeDTO> Rooftypes, CarportRequestDTO item, String selectedCheck, String rows) {
        for(RooftypeDTO value : Rooftypes)
        {
            String row = "<option value=\"$1\" $3>$2</option>";
            row = row.replace("$1", String.valueOf(value.getId()));
            row = row.replace("$2", value.getType());
            row = row.replace("$3", item != null && item.getRooftypeId() == value.getId() ? "selected" : "");//Tilføj selected ved den angivet id som man har tilføjet ved forespørgelse.
            rows += row;
        }
        return rows;
    }

    /**
     * Tilføj to værdi til hvilken forespørgelse og samtidig skurid i shedId
     * @param item
     * @param stringBuilder 
     */
    private void anyIDs(CarportRequestDTO item, StringBuilder stringBuilder) {
        if(item != null)
        {
            stringBuilder.append("<input type=\"hidden\" name=\"shedId\" value=\"$shedId\" />");
            stringBuilder.append("<input type=\"hidden\" name=\"carportId\" value=\"$id\" />");
        }
    }

}
