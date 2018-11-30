<%-- 
    Document   : index
    Created on : 15-11-2018, 17:48:35
    Author     : Claus
--%>

<%
    String title = "Velkommen til FOG";
    //String errorText = request.getAttribute(Fields.ERROR_TEXT) != null ? (String)request.getAttribute(Fields.ERROR_TEXT): "No errors!";
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%= title %></title>
        <jsp:include page="WEB-INF/css/bootstrap.jsp"></jsp:include>
        <link href="WEB-INF/css/css.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
            <jsp:include page="WEB-INF/view/Header.jsp"></jsp:include> <%-- Header her ---%>
            <div class="container" style="min-height: 500px;">
                
            </div>
            
            <jsp:include page="WEB-INF/view/footer/footer.jsp"></jsp:include> <%-- Footer her ---%>
    </body>
</html>
