package es.maneldevs.infraestructure.in.adapter.web;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import io.javalin.http.Context;

public class FlashMessages {
    private static final String ERROR_COOKIE = "flash_error";

    private FlashMessages() {
    }

    public static void setError(Context ctx, String message) {
        ctx.cookie(ERROR_COOKIE, URLEncoder.encode(message, StandardCharsets.UTF_8), 5);
    }

    public static String readError(Context ctx) {
        String raw = ctx.cookie(ERROR_COOKIE);
        if (raw == null) {
            return "";
        }
        ctx.removeCookie(ERROR_COOKIE);
        return URLDecoder.decode(raw, StandardCharsets.UTF_8);
    }

}
