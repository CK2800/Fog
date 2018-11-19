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
        <jsp:include page="./bootstrap/csscdn.jsp"></jsp:include>
    </head>
    <body>
        <div class="container">
        <h1>Indhold her.</h1>
        <a href="FrontController?command=showrequests" class="btn btn-secondary">Tilbage</a>
        <%= requestForm %>
        </div>
        <jsp:include page="./bootstrap/jscdn.jsp"></jsp:include>
    </body>
</html>
