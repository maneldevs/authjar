package es.maneldevs.application.portout;

import java.util.List;

import es.maneldevs.domain.model.ClientApp;

public interface ClientAppPort {
    List<ClientApp> getClientApps(String clientId);

    ClientApp getClientApp(String clientId, String appId);

    boolean existsByName(String name, String excludeId);

    ClientApp createClientApp(String clientId, String appId, String name);

    void updateClientApp(String id, String name, Boolean active);
}
