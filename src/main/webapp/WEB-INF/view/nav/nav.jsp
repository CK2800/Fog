<%@page import="jc.fog.presentation.Commands"%>
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
        <a class="nav-link" href="/Fog/FrontController?command=<%= Commands.SHOW_MATERIALS %>">Materiale</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/Fog/FrontController?command=<%= Commands.SHOW_REQUESTS %>">Forspørgelse</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="/Fog/FrontController?command=<%= Commands.SHOW_FORM_REQUEST %>">Opret forespørgelse</a>
      </li> 
      <li class="nav-item">
        <a class="nav-link" href="/Fog/FrontController?command=<%= Commands.REGISTER %>">Opret bruger</a>
      </li> 
      <li class="nav-item">
        <a class="nav-link" href="/Fog/FrontController?command=<%= Commands.LOGIN %>">Log ind</a>
      </li> 
      <li class="nav-item">
        <a class="nav-link" href="/Fog/FrontController?command=<%= Commands.LOGOUT %>">Log ud</a>
      </li> 
    </ul>
  </div>  
</nav>