package es.maneldevs.infraestructure.config;

import java.nio.file.Paths;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;

public class TemplateEngineConfig {

    public static TemplateEngine init() {
        boolean isProduction = "production".equalsIgnoreCase(Env.APP_ENV);
        TemplateEngine templateEngine;
        if (isProduction) {
            // Load classes compiled statically by jte-maven-plugin
            templateEngine = TemplateEngine.createPrecompiled(ContentType.Html);
        } else {
            // Compile templates on-the-fly from resources
            templateEngine = TemplateEngine.create(
                    new DirectoryCodeResolver(Paths.get("src/main/resources/templates")),
                    ContentType.Html);
        }
        return templateEngine;
    }
}
