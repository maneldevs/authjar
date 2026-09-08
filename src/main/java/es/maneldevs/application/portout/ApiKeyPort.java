package es.maneldevs.application.portout;

public interface ApiKeyPort {

    boolean existsActiveApiKey(String hashedApiKey);
    
}
