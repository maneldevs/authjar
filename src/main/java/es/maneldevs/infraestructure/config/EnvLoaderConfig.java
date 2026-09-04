package es.maneldevs.infraestructure.config;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoaderConfig {
    public static void init() {
        Dotenv.configure().ignoreIfMissing().systemProperties().load();
    }
}
