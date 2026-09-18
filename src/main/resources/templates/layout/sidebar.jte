@param String activeItem

<aside class="sidebar bg-light border-end" id="appSidebar">
    <nav class="nav flex-column p-2">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a href="/web/dashboard"
                    class="nav-link link-dark${"dashboard".equals(activeItem) ? " active fw-bold" : ""}">
                    Dashboard
                </a>
            </li>
            <li class="nav-item">
                <a href="/web/clients"
                    class="nav-link link-dark${"clients".equals(activeItem) ? " active fw-bold" : ""}">
                    Clients
                </a>
            </li>
            <li class="nav-item">
                <a href="/web/apps"
                    class="nav-link link-dark${"apps".equals(activeItem) ? " active fw-bold" : ""}">
                    Applications
                </a>
            </li>
            <li class="nav-item">
                <a href="/web/users"
                    class="nav-link link-dark${"users".equals(activeItem) ? " active fw-bold" : ""}">
                    Users
                </a>
            </li>
        </ul>
    </nav>
</aside>
