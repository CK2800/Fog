<%@page import="jc.fog.logic.UsersDTO"%>
<%@page import="jc.fog.presentation.Commands"%>
<%
    UsersDTO user = (UsersDTO)session.getAttribute("user");
%>
<nav class="navbar navbar-expand-sm bg-dark navbar-dark" style="margin-bottom: 20px;">
    <a class="navbar-brand" href="/Fog/">
        Forside
    </a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="collapsibleNavbar">
    <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="/Fog/FrontController?command=<%= Commands.SHOW_FORM_REQUEST %>">Opret forespørgelse</a>
            </li> 
        <% if(user != null) { %>
        
            <% if(user.getRank() == 1) { %>
                <li class="nav-item">
                    <a class="nav-link" href="/Fog/FrontController?command=<%= Commands.SHOW_MATERIALS %>">Materiale</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/Fog/FrontController?command=<%= Commands.SHOW_REQUESTS %>">Forspørgelse</a>
                </li>
            <% } %>
            <li class="nav-item">
                <a class="nav-link" href="/Fog/FrontController?command=<%= Commands.LOGOUT %>">Log ud</a>
            </li> 
        <% } else { %>
            <li class="nav-item">
                <a class="nav-link" href="/Fog/FrontController?command=<%= Commands.REGISTER %>">Opret bruger</a>
            </li> 
            <li class="nav-item">
                <a class="nav-link" href="/Fog/FrontController?command=<%= Commands.LOGIN %>">Log ind</a>
            </li> 
        <% } %>
        
        
        
      
    </ul>
  </div>  
</nav>