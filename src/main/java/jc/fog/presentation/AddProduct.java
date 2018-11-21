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
import jc.fog.exceptions.FogException;
import jc.fog.logic.ForesporgselDTO;

/**
 *
 * @author Jesper
 */
public class AddProduct extends Command
{
    
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {
        // Later we will validate a logged in user
        
        
        
        // Return the page showing all requests.
        return Pages.ALL_REQUESTS;
    }
}
