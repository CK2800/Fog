<%
    String title = "Alle Forespørgsel"; //Den kan fremkomme i title efter hvilken kunde der laver forespørgsel.
    /*
    NEJ - INGEN KODE I VIEWET og INGEN afhængighed til DAO klasserne her !!! 
    String tableView = ForesporgselDAO.getForesporgselTable();
    */
    String tableView = (String)request.getAttribute("requestsTable");
    
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