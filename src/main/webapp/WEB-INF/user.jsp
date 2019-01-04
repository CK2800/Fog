<%@page import="jc.fog.presentation.commands.Commands"%>
<%
    String title = "Opdater adgangskode";
    String getUserName = (String)request.getAttribute("getUserName");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= title %></title>
        <jsp:include page="./css/bootstrap.jsp"></jsp:include>
    </head>
    <body>
            <jsp:include page="view/Header.jsp"></jsp:include> <%-- Header her ---%>
            <div class="container" style="min-height: 500px;">
                <div class="row">
                    
                    <div class="col-md-8">
                        <h2>Hej <%= getUserName %></h2>
                    </div>
                    
                    <div class="col-md-4">
                        <!--- Her vil overblik være fra dvs sider. --->
                        <h2>Din oplysning <%= getUserName %></h2>
                        <ul class="list-group">
                            <li class="list-group-item list-group-item-action list-group-item-primary">
                                <a href="/Fog/FrontController?command=<%= Commands.USER_PASSWORD %>">Opdater adgangskode</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            
            <jsp:include page="view/footer/footer.jsp"></jsp:include> <%-- Footer her ---%>
    </body>
</html>
