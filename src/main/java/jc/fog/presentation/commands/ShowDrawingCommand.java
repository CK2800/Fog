/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jc.fog.data.DataFacadeImpl;
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.LogicFacade;
import jc.fog.logic.MaterialDTO;
import jc.fog.logic.Rectangle;
import jc.fog.logic.UsersDTO;
import jc.fog.presentation.Drawing;
import jc.fog.presentation.Pages;

/**
 *
 * @author Jespe
 */
public class ShowDrawingCommand extends Command{
    
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        try
        {
            //sikker sig at man har den rigtigt rank for at kun se det her område.
            HttpSession session = request.getSession();
            UsersDTO user = (UsersDTO)session.getAttribute("user");
            // Har vi en user i session, er denne logget ind, gå til index side.
            if(user == null || user != null && user.getRank() > 1)
            {
                return Pages.INDEX;
            } 
            
            //henter id som skal bruges til, at frem vise hvordan tegning skal være.        
            int id = Integer.parseInt(request.getParameter("id"));

            // Hent carport request og materialer.
            DataFacadeImpl dataFacade = new DataFacadeImpl(DbConnector.getConnection());
            CarportRequestDTO carportRequest = dataFacade.getCarport(id);
            List<MaterialDTO> materials = dataFacade.getMaterials();
            // Udregn rektangler.
            List<Rectangle> rectangles = LogicFacade.drawCarport(carportRequest, materials);

            //Her bliver højde og bredde til svg filen angivet.
            request.setAttribute("svg", Drawing.drawSvg(rectangles, "100%", "100%", carportRequest.getWidth(), carportRequest.getLength()));

            return Pages.SINGLE_DRAW;
        }
        catch(Exception e)
        {
            throw new FogException("Tegning kunne ikke blive vist. " , e.getMessage());
        }
    }
}
