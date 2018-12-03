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
import jc.fog.data.DbConnector;
import jc.fog.exceptions.FogException;
import jc.fog.logic.CarportRequestDTO;
import jc.fog.logic.LogicFacade;
import jc.fog.logic.MaterialDTO;
import jc.fog.logic.Rectangle;

/**
 *
 * @author Jespe
 */
public class ShowDrawingCommand extends Command{
    
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        //henter id som skal bruges til, at frem vise hvordan tegning skal være.
        //Den bliver ikke brugt lige pt.
        int id = Integer.parseInt(request.getParameter("id"));
        
        
        List<Rectangle> rectangles = new ArrayList<Rectangle>();
        rectangles.add(new Rectangle(0, 0, 170, 180, "7FFF00"));
        rectangles.add(new Rectangle(15, 120, 825, 140, "D2691E"));
        

        // Hent carport request og materialer.
        DataFacade dataFacade = new DataFacade(DbConnector.getConnection());
        CarportRequestDTO carportRequest = dataFacade.getCarport(id);
        List<MaterialDTO> materials = dataFacade.getMaterials();
        // Udregn rektangler.
        rectangles = LogicFacade.drawCarport(carportRequest, materials);
       

        //Her bliver højde og bredde til svg filen angivet.
        request.setAttribute("svg", Drawing.drawSvg(rectangles, "100%", "100%", carportRequest.getWidth(), carportRequest.getLength()));

        
        
        return Pages.SINGLE_DRAW;
    }
}
