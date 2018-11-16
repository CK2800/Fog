<%
    String title = "Forespørgsel"; //Den kan fremkomme i title efter hvilken kunde der laver forespørgsel.
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
        <a href="FrontController?command=showrequests">Tilbage</a>
        <%= requestForm %>
    </body>
</html>
