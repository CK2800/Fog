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
        <title><%= title %></title>
        <jsp:include page="./css/bootstrap.jsp"></jsp:include>
    </head>
    <body>
            <jsp:include page="view/logo/logo.jsp"></jsp:include> <%-- Logo her ---%>
            <jsp:include page="view/nav/nav.jsp"></jsp:include> <%-- menu her ---%>
            
            <div class="container" style="min-height: 500px;">
                    <div class="col-md-8">
                    <%= requestForm%>
                    </div>
                    <div class="col-md-4">
                        <h2>Tegning</h2>
                    <%= svg%>
                    </div>
                </div>
            </div>
            
            <jsp:include page="view/footer/footer.jsp"></jsp:include> <%-- Footer her ---%>
    </body>
</html>
