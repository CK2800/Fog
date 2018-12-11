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
import jc.fog.exceptions.FogException;
import jc.fog.logic.MaterialDTO;
import jc.fog.logic.UsersDTO;
import jc.fog.data.DataFacadeImpl;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.presentation.Pages;

/**
 *
 * @author Jespe
 */
public class ShowAdminUsersCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        try{
            //sikker sig at man har den rigtigt rank for at kun se det her område.
            HttpSession session = request.getSession();
            UsersDTO user = (UsersDTO)session.getAttribute("user");
            // Har vi en user i session, er denne logget ind, gå til index side.
            if(user != null && user.getRank() > 1)
            {
                return Pages.INDEX;
            } 
            
            DataFacade dataFacade = new DataFacadeImpl(DbConnector.getConnection());
            List<UsersDTO> getAllUsers = dataFacade.getAllUsers();
            request.setAttribute("usersTable", userTable(getAllUsers, user));
        
            return Pages.ADMIN_USER;
        }
        catch(Exception e)
        {
            throw new FogException("" + e.getMessage());
        }
    }
    
    
    /**
     * 
     * @param getAllUsers
     * @param userInfo Sikker sig at man ikke vil kun giv sig selv en mindre rank eller ny adgangskode.
     * @return 
     */
    private String userTable(List<UsersDTO> getAllUsers, UsersDTO userInfo)
    {
        StringBuilder stringBuilder = new StringBuilder();
        String table = "<table class=\"table table-striped\"><thead><tr><th>$1</th><th>$2</th><th>$3</th><th>$4</th></tr></thead><tbody>$body</tbody></table>";        
        
        table = table.replace("$1", "Navn");
        table = table.replace("$2", "Email");
        table = table.replace("$3", "Bruger status");
        table = table.replace("$4", "");
        
        for(UsersDTO item : getAllUsers)
        {
            String row = "<tr><td>$1</td><td>$2</td><td>$3</td><td>$4</td></tr>";
            row = row.replace("$1", String.valueOf(item.getName()));
            row = row.replace("$2", String.valueOf(item.getEmail()));
            row = row.replace("$3", String.valueOf(item.getRank() == 1 ? "Admin" : "Alm bruger"));
            row = row.replace("$4", (userInfo.getId() != item.getId() ? "<a href=\"FrontController?command=" + Commands.ADMIN_RANK + "&id=" + item.getId() + "&rank=" + item.getRank() + "\" class=\"btn btn-info btn-sm\">" + (item.getRank() == 5 ? "Gør til Admin" : "Gør til alm bruger") + "</a> " : "") + 
                                    (userInfo.getId() != item.getId() ? "<a href=\"FrontController?command=" + Commands.ADMIN_PASSWORD + "&email=" + item.getEmail()+ "\" class=\"btn btn-info btn-sm\">Ny kode</a>" : "") + 
                                    (userInfo.getId() != item.getId() ? "<a href=\"FrontController?command=" + Commands.ADMIN_DELETE_USER + "&id=" + item.getId() + "\" class=\"btn btn-danger btn-sm\">Slet bruger</a>" : ""));
            stringBuilder.append(row);
        }
        
        return table.replace("$body", stringBuilder.toString());
    }
}
