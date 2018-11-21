<%-- 
    Document   : stykliste
    Created on : 21-11-2018, 10:52:30
    Author     : Claus
--%>
<%
    String id = (String)request.getParameter("id");
    String carportDimensioner = (String)request.getAttribute("carportDimensioner");
    String tabel = (String)request.getAttribute("stykliste");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Stykliste for forespørgsel <%= id %>, <%= carportDimensioner %></h1>
        <%= tabel %>        
    </body>
</html>
