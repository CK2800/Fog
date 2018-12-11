/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jc.fog.presentation.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jc.fog.exceptions.FogException;

/**
 * If an unknown command is requested, this command is executed.
 * 
 * @author Claus
 */
public class UnknownCommand extends Command
{
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws FogException
    {        
        throw new FogException("Den ønskede side kan ikke vises.", "Unknown command request.", null);
    }
}
