<%@page import="jc.fog.data.ForesporgselDAO"%>
<%
    String title = "Alle Forespørgsel"; //Den kan fremkomme i title efter hvilken kunde der laver forespørgsel.
    String tableView = ForesporgselDAO.getForesporgselTable();
    
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%= tableView %>
    </body>
</html>
