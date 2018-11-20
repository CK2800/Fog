<%
    String title = "Alle materiale";
    String tableView = (String)request.getAttribute("materialeTable");
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
            <h1><%= title %></h1>
            <%= tableView %>
        </div>
        <jsp:include page="./bootstrap/jscdn.jsp"></jsp:include>
    </body>
</html>
