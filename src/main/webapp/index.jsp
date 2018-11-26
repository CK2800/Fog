<%-- 
    Document   : index
    Created on : 15-11-2018, 17:48:35
    Author     : Claus
--%>
<%@page import="jc.fog.presentation.Fields"%>
<%
    String title = "Velkommen til FOG";
    String errorText = request.getAttribute(Fields.ERROR_TEXT) != null ? (String)request.getAttribute(Fields.ERROR_TEXT): "No errors!";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= title %></title>
        <jsp:include page="WEB-INF/css/cdnfilesBootstrap.jsp"></jsp:include>
    </head>
    <body>
        
        <div class="container" style="margin-top: 55px;">
            <jsp:include page="WEB-INF/nav/nav.jsp"></jsp:include>
            <h1><%= title %></h1>
            <div><%= errorText %></div>
        </div>
        <jsp:include page="WEB-INF/footer/footer.jsp"></jsp:include>
    </body>
</html>
