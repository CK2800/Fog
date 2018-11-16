<%-- 
    Document   : index
    Created on : 15-11-2018, 17:48:35
    Author     : Claus
--%>
<%@page import="jc.fog.presentation.constants.Fields"%>
<%
    String errorText = request.getAttribute(Fields.ERROR_TEXT) != null ? (String)request.getAttribute(Fields.ERROR_TEXT): "No errors!";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <div><%= errorText %></div>
    </body>
</html>
