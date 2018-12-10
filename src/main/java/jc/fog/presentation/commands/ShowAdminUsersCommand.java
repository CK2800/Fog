/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        DataFacadeImpl dataFacade = new DataFacadeImpl(DbConnector.getConnection());
        
        
        
        return Pages.ADMIN_USER;
    }
}
