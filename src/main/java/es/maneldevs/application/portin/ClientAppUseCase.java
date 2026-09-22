package es.maneldevs.application.portin;

import java.util.List;

import es.maneldevs.domain.model.ClientApp;

public interface ClientAppUseCase {
    List<ClientApp> getClientApps(String clientId);

    ClientApp getClientApp(String clientId, String appId);

    String generateApiKey();

    ClientApp createClientApp(String clientId, String appId, String name, String apiKey);

    void updateClientApp(String clientId, String appId, String name, Boolean active, String apiKey);
}
