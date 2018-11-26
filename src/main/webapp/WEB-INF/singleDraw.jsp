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
        <jsp:include page="./css/cdnfilesBootstrap.jsp"></jsp:include>
    </head>
    <body>
            <jsp:include page="view/logo/logo.jsp"></jsp:include> <%-- Logo her ---%>
            <jsp:include page="view/nav/nav.jsp"></jsp:include> <%-- menu her ---%>
            
            <div class="container" style="min-height: 500px;">
            <input type="button" value="Print" onclick="window.print()" /> <br/><br/>
            <%= svg %>
            </div>
            
            <jsp:include page="view/footer/footer.jsp"></jsp:include> <%-- Footer her ---%>
    </body>
</html>
