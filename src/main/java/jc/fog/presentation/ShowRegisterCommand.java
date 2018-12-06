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
import jc.fog.logic.ZipcodeDTO;

/**
 *
 * @author Jespe
 */
public class ShowRegisterCommand extends Command {
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        DataFacade dataFacade = new DataFacade(DbConnector.getConnection());
        
        List<ZipcodeDTO> zipcodes = dataFacade.getZipcodes();
        
        
        request.setAttribute("register", register(zipcodes));
        
        
        return Pages.REGISTER;
    }
    
    private String register(List<ZipcodeDTO> zipcodes)
    {
        //Navn-c postnr telefon-c emai-c adgangskode-c
        
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<form action=\"FrontController\" method=\"POST\">");
        stringBuilder.append("E-mail (Brugernavn):<br /><input type=\"text\" name=\"email\" class=\"form-control\" placeholder=\"Din Email\" /><br />");
        stringBuilder.append("Adgangskode<br /><input type=\"text\" name=\"password\" class=\"form-control\" placeholder=\"Din adgangskode\" /><br />");
        stringBuilder.append("Navn:<br /><input type=\"text\" name=\"name\" class=\"form-control\" placeholder=\"Din Email\" /><br />");
        stringBuilder.append("Telefon nr:<br /><input type=\"text\" name=\"phone\" class=\"form-control\" placeholder=\"Din Email\" /><br />");
        
        //Dropdown
        String Dropdown = "Post nr:<br /><select class=\"form-control\" name=\"zipcode\">$body</select>";
        String rows = "";
        rows = zipcodesDropdown(zipcodes, rows);
        Dropdown = Dropdown.replace("$body", rows);
        stringBuilder.append(Dropdown);
        stringBuilder.append("<br/>");
        
        stringBuilder.append("<input type=\"submit\" class=\"btn btn-info btn-block\" value=\"Opret bruger\" \">");
        stringBuilder.append("</form><br/>");
        
        return stringBuilder.toString();
    }

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
