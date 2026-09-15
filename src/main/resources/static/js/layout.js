document.addEventListener("DOMContentLoaded", function () {
    var toggle = document.getElementById("sidebarToggle");
    var sidebar = document.getElementById("appSidebar");

    if (!toggle || !sidebar) {
        return;
    }

    toggle.addEventListener("click", function () {
        var hidden = sidebar.classList.toggle("d-none");
        toggle.setAttribute("aria-expanded", String(!hidden));
    });
});

document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll('[data-bs-toggle="tooltip"]').forEach(function (el) {
        new bootstrap.Tooltip(el);
    });
});
