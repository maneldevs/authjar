package es.maneldevs.infraestructure.config;

public class Env {
    public static final String APP_ENV = getEnvVar("APP_ENV", "develop");
    public static final String APP_PORT = getEnvVar("APP_PORT", "8080");
    public static final String DB_URL = getEnvVar("DB_URL", "jdbdc:postgresql://localhost:5432/postgres");
    public static final String DB_USER = getEnvVar("DB_USER", "postgres");
    public static final String DB_PASSWORD = getEnvVar("DB_PASSWORD", "postgres");

    private static String getEnvVar(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value == null) {
            value = System.getenv(key);
        }
        return value != null ? value : defaultValue;
    }

}
