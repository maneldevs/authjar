package es.maneldevs.application.portout;

import java.util.List;

import es.maneldevs.domain.model.ClientApp;

public interface ClientAppPort {
    List<ClientApp> getClientApps(String clientId);

    ClientApp getClientApp(String clientId, String appId);

    ClientApp getActiveClientAppByApiKeyHash(String apiKeyHash);

    boolean existsByName(String name, String excludeId);

    ClientApp createClientApp(String clientId, String appId, String name, String apiKeyHash);

    void updateClientApp(String id, String name, Boolean active, String apiKeyHash);
}
