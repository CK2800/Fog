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
import jc.fog.data.DataFacade;
import jc.fog.data.DataFacadeImpl;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.logic.UsersDTO;
import jc.fog.logic.ZipcodeDTO;
import jc.fog.presentation.Pages;

/**
 *
 * @author Jespe
 */
public class ShowRegisterCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        try
        {
            HttpSession session = request.getSession();
            UsersDTO user = (UsersDTO)session.getAttribute("user");
            // Har vi en user i session, er denne logget ind, gå til index side.
            if(user != null && user.getId() > 0)
            {
                return Pages.INDEX;
            } 
            
            DataFacade dataFacade = new DataFacadeImpl(DbConnector.getConnection());

            //får fast i zipcodes.
            List<ZipcodeDTO> zipcodes = dataFacade.getZipcodes();

            //Fremviser form for opret bruger delen.
            request.setAttribute("register", register(zipcodes));
            
               //Siden som skal bliver vist.
            return Pages.REGISTER;
        }
        catch(Exception e)
        {
            throw new FogException("Opret bruger side kan ikke vises, prøv igen.", e.getMessage(), e);
        }
        
    }
    
    /**
     * Form som bliver vist til bruger når man skal oprette sig.
     * @param zipcodes - Få zipcodes som skal bruge til dropdown.
     * @return 
     */
    private String register(List<ZipcodeDTO> zipcodes)
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<form action=\"FrontController\" method=\"POST\">");
        stringBuilder.append("E-mail (Brugernavn):<br /><input type=\"text\" name=\"email\" class=\"form-control\" placeholder=\"Din Email\" /><br />");
        stringBuilder.append("Adgangskode<br /><input type=\"password\" name=\"password\" class=\"form-control\" placeholder=\"Din adgangskode\" /><br />");
        stringBuilder.append("Navn:<br /><input type=\"text\" name=\"name\" class=\"form-control\" placeholder=\"Dit navn\" /><br />");
        stringBuilder.append("Telefon nr:<br /><input type=\"text\" name=\"phone\" class=\"form-control\" placeholder=\"Dit telefon nr\" /><br />");
        
        //Dropdown
        String Dropdown = "Post nr:<br /><select class=\"form-control\" name=\"zipcode\">$body</select>";
        String rows = "";
        rows = zipcodesDropdown(zipcodes, rows);
        Dropdown = Dropdown.replace("$body", rows);
        stringBuilder.append(Dropdown);
        stringBuilder.append("<br/>");
        
        stringBuilder.append("<input type=\"submit\"formaction=\"/Fog/FrontController?command=" + Commands.ADD_REGISTER + "\" class=\"btn btn-info btn-block\" value=\"Opret bruger\" \">");
        stringBuilder.append("</form><br/>");
        
        return stringBuilder.toString();
    }

    /**
     * For langt dvs værdier ind i option.
     * @param zipcodes
     * @param rows
     * @return 
     */
    private String zipcodesDropdown(List<ZipcodeDTO> zipcodes, String rows) {
        for(ZipcodeDTO value : zipcodes)
        {
            String row = "<option value=\"$1\">$2</option>";
            row = row.replace("$1", String.valueOf(value.getZip()));
            row = row.replace("$2", value.getCity());
            rows += row;
        }
        return rows;
    }
}
