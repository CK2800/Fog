<%
    String title = "Alle Forespørgsel"; //Den kan fremkomme i title efter hvilken kunde der laver forespørgsel.
    String tableView = (String)request.getAttribute("requestsTable");    
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= title %></title>
        <jsp:include page="./bootstrap/cdnfilesBootstrap.jsp"></jsp:include>
    </head>
    <body>
        
        <div class="container">
            <jsp:include page="./nav/nav.jsp"></jsp:include>
        <%= tableView %>
        </div>
    </body>
</html>
