<%
    String title = "Alle bruger";
    String tableView = (String)request.getAttribute("usersTable");
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
                <%= tableView %>
            </div>
            
            <jsp:include page="view/footer/footer.jsp"></jsp:include> <%-- Footer her ---%>
    </body>
</html>
