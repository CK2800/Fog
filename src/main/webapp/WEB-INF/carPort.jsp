<%
    String title = "Carport";
    boolean viewUpdateQuest = (Boolean)request.getAttribute("viewBill");
    String requestForm = (String) request.getAttribute("requestForm");
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
                        <%= requestForm %>
                    </div>
                    <div class="col-md-4">
                        <% if(viewUpdateQuest == true) { %>
                        <p>Styklist kommer her</p>
                        <% } else { %>
                        <p>Hej</p>
                        <% } %>
                    </div>
                </div>
            </div>
            
            <jsp:include page="view/footer/footer.jsp"></jsp:include> <%-- Footer her ---%>
    </body>
</html>
