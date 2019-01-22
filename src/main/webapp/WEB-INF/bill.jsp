<%-- 
    Document   : stykliste
    Created on : 21-11-2018, 10:52:30
    Author     : Claus
--%>
<%@page import="jc.fog.presentation.Fields"%>
<%
    String id = (String)request.getParameter("id");//Er den vigtig at få mere over??? i forhold til når den skal beregn?
    String carportDimensioner = (String)request.getAttribute("carportDimensioner");
    String tabel = (String)request.getAttribute("stykliste");
    String totalpris = (String)request.getAttribute("totalpris");
    String shedCheck = (String)request.getAttribute("shed");
    String errorText = (String)request.getAttribute(Fields.ERROR_TEXT);
    
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Fog stykliste og prisberegner</title>
        <jsp:include page="./css/bootstrap.jsp"></jsp:include>
    </head>
    <body>
            <jsp:include page="view/Header.jsp"></jsp:include> <%-- Header her ---%>
            <div class="container" style="min-height: 500px;">
            <%= errorText == null ? "" : "<div class=\"errorText\">" + errorText + "</div>" %>
            <h1>Stykliste for forespørgsel <%= id %>, <%= carportDimensioner %> <%= shedCheck %></h1>
                <%= tabel %>     
                <text class="btn btn-secondary btn-lg btn-block">Totalpris DKK: <%= totalpris %></text><br/><br/>
            </div>
            
            <jsp:include page="view/footer/footer.jsp"></jsp:include> <%-- Footer her ---%>
    </body>
</html>
