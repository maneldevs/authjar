@import gg.jte.Content

@param String title
@param String userEmail
@param String activeItem
@param Content content

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/bootstrap-icons.css">
    <link rel="stylesheet" href="/static/css/layout.css">
</head>

<body>
    @template.layout.topbar(appName = "Monitor", userEmail = userEmail)

    <div class="app-body d-flex">
        @template.layout.sidebar(activeItem = activeItem)
        <main class="main-content flex-grow-1 p-4">
            <h1 class="page-title mb-4">${title}</h1>
            ${content}
        </main>
    </div>

    <script src="/static/js/bootstrap.bundle.min.js"></script>
    <script src="/static/js/layout.js"></script>
</body>

</html>
