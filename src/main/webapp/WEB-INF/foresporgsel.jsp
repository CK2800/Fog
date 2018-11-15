<%@page import="jc.fog.logic.ForesporgselDTO"%>
<%@page import="jc.fog.data.ForesporgselDAO"%>
<%
    String title = "Forespørgsel"; //Den kan fremkomme i title efter hvilken kunde der laver forespørgsel.
    int getId = Integer.parseInt(request.getParameter("id"));
    ForesporgselDTO forDTO = ForesporgselDAO.getForesporgselSingle(getId);
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
        <p><%= forDTO.getBredde() %></p>
        <p><%= forDTO.getLaengde() %></p>
        <p><%= forDTO.getHoejde() %></p>
        <p><%= forDTO.getHaeldning() %></p>
        <p><%= forDTO.getBemaerkning() %></p>
        <p><%= (forDTO.getSkurId() == 1) ? "Ja - Skur" : "Nej skur" %></p>
    </body>
</html>
