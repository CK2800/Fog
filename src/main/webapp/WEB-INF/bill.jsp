<%-- 
    Document   : stykliste
    Created on : 21-11-2018, 10:52:30
    Author     : Claus
--%>
<%
    String id = (String)request.getParameter("id");
    String carportDimensioner = (String)request.getAttribute("carportDimensioner");
    String tabel = (String)request.getAttribute("stykliste");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>g</title>
        <jsp:include page="./css/bootstrap.jsp"></jsp:include>
    </head>
    <body>
            <jsp:include page="view/Header.jsp"></jsp:include> <%-- Header her ---%>
            <div class="container" style="min-height: 500px;">
            <h1>Stykliste for forespørgsel <%= id %>, <%= carportDimensioner %></h1>
                <%= tabel %>     
            </div>
            
            <jsp:include page="view/footer/footer.jsp"></jsp:include> <%-- Footer her ---%>
    </body>
</html>
