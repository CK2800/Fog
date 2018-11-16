<%@page import="jc.fog.logic.ForesporgselDTO"%>
<%
    String title = "Forespørgsel"; //Den kan fremkomme i title efter hvilken kunde der laver forespørgsel.
    
    /* 
    FORBUDT - INGEN KODE I VIEWET - og vores command har jo lige sat en ForesporgselDTO i requestet !!!
    
    int getId = Integer.parseInt(request.getParameter("id"));
    ForesporgselDTO forDTO = ForesporgselDAO.getForesporgselSingle(getId);
    
    */
    
    ForesporgselDTO foresporgselDTO = (ForesporgselDTO)request.getAttribute("ForesporgselDTO");
    String requestForm = (String)request.getAttribute("requestForm");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= title %></title>
    </head>
    <body>
        <h1>Indhold her.</h1>        
        <%= requestForm %>
        <p><%= foresporgselDTO.getBredde() %></p>
        <p><%= foresporgselDTO.getLaengde() %></p>
        <p><%= foresporgselDTO.getHoejde() %></p>
        <p><%= foresporgselDTO.getHaeldning() %></p>
        <p><%= foresporgselDTO.getBemaerkning() %></p>
        <p><%= (foresporgselDTO.getSkurId() == 1) ? "Ja - Skur" : "Nej skur" %></p>
    </body>
</html>
