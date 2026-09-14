@param String title
@param boolean error

<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <style>
        * {
            box-sizing: border-box;
        }

        body {
            margin: 0;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: "Segoe UI", Roboto, Helvetica, Arial, sans-serif;
            background: linear-gradient(135deg, #c2410c 0%, #ea580c 50%, #f59e0b 100%);
        }

        .card {
            width: 100%;
            max-width: 380px;
            margin: 20px;
            padding: 40px 32px;
            border-radius: 16px;
            background: #ffffff;
            box-shadow: 0 20px 45px rgba(0, 0, 0, 0.25);
        }

        .card h1 {
            margin: 0 0 4px;
            font-size: 24px;
            color: #1f2937;
            text-align: center;
        }

        .card p.subtitle {
            margin: 0 0 28px;
            font-size: 14px;
            color: #6b7280;
            text-align: center;
        }

        .field {
            margin-bottom: 18px;
        }

        .field label {
            display: block;
            margin-bottom: 6px;
            font-size: 13px;
            font-weight: 600;
            color: #374151;
        }

        .field input {
            width: 100%;
            padding: 12px 14px;
            font-size: 15px;
            border: 1px solid #d1d5db;
            border-radius: 8px;
            outline: none;
            transition: border-color 0.15s ease, box-shadow 0.15s ease;
        }

        .field input:focus {
            border-color: #ea580c;
            box-shadow: 0 0 0 3px rgba(234, 88, 12, 0.15);
        }

        .submit {
            width: 100%;
            padding: 12px 14px;
            margin-top: 8px;
            font-size: 15px;
            font-weight: 600;
            color: #ffffff;
            background: linear-gradient(135deg, #c2410c 0%, #ea580c 100%);
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: opacity 0.15s ease;
        }

        .submit:hover {
            opacity: 0.9;
        }

        .alert {
            margin-bottom: 18px;
            padding: 10px 14px;
            font-size: 13px;
            color: #991b1b;
            background: #fee2e2;
            border: 1px solid #fecaca;
            border-radius: 8px;
            text-align: center;
        }
    </style>
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
