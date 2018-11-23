<%
    String title = "Forespørgsel"; //Den kan fremkomme i title efter hvilken kunde der laver forespørgsel.
    String requestForm = (String) request.getAttribute("requestForm");
    String svg = (String) request.getAttribute("svg");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= title%></title>
        <jsp:include page="./bootstrap/csscdn.jsp"></jsp:include>
        </head>
        <body>
            <div class="container">
                <h1><%= title%></h1>
                <div class="row">
                    <div class="col-md-8">
                    <%= requestForm%>
                    </div>
                    <div class="col-md-4">
                    <%= svg%>
                    </div>
                </div>
            </div>
        <jsp:include page="./bootstrap/jscdn.jsp"></jsp:include>
    </body>
</html>
