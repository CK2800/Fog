<%
    String title = "Fremvis tegning på carport";
    String svg = (String) request.getAttribute("svg");
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
            <h1><%= title %></h1>
            <%= svg %>
        </div>
    </body>
</html>
