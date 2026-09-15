@param String appName
@param String userEmail

<nav class="navbar navbar-expand navbar-dark bg-dark topbar">
    <div class="container-fluid">
        <button class="btn btn-outline-light btn-sm me-3" type="button" id="sidebarToggle"
            aria-controls="appSidebar" aria-expanded="true" aria-label="Mostrar u ocultar el menú lateral">
            &#9776;
        </button>
        <a class="navbar-brand mb-0 h1" href="/web/dashboard">${appName}</a>

        <ul class="navbar-nav ms-auto">
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="userMenuToggle" role="button"
                    data-bs-toggle="dropdown" aria-expanded="false">
                    ${userEmail}
                </a>
                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="userMenuToggle">
                    <li><a class="dropdown-item" href="/web/account">Account</a></li>
                    <li><a class="dropdown-item" href="/web/logout">Logout</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>
