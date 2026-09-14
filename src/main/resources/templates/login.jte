@param String title
@param boolean error

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <link rel="stylesheet" href="/static/css/login.css">
</head>

<body>
    <div class="card">
        <h1>Monitor</h1>
        <p class="subtitle">Introduce tus credenciales para continuar</p>

        @if (error)
            <div class="alert">Usuario o contraseña incorrectos</div>
        @endif

        <form method="post" action="/web/login">
            <div class="field">
                <label for="email">Usuario</label>
                <input type="text" id="email" name="email" autocomplete="username" required autofocus>
            </div>
            <div class="field">
                <label for="password">Contraseña</label>
                <input type="password" id="password" name="password" autocomplete="current-password" required>
            </div>
            <button type="submit" class="submit">Entrar</button>
        </form>
    </div>
</body>

</html>
