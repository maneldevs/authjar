package es.maneldevs.application.service;

import java.util.List;

import es.maneldevs.application.portin.ClientAppUseCase;
import es.maneldevs.application.portout.AppPort;
import es.maneldevs.application.portout.ClientAppPort;
import es.maneldevs.application.portout.ClientPort;
import es.maneldevs.domain.exception.ValidationException;
import es.maneldevs.domain.model.ClientApp;

public class ClientAppService implements ClientAppUseCase {
    private final ClientAppPort clientAppPort;
    private final ClientPort clientPort;
    private final AppPort appPort;

    public ClientAppService(ClientAppPort clientAppPort, ClientPort clientPort, AppPort appPort) {
        this.clientAppPort = clientAppPort;
        this.clientPort = clientPort;
        this.appPort = appPort;
    }

    @Override
    public List<ClientApp> getClientApps(String clientId) {
        return clientAppPort.getClientApps(clientId);
    }

    @Override
    public ClientApp getClientApp(String clientId, String appId) {
        return clientAppPort.getClientApp(clientId, appId);
    }

    @Override
    public ClientApp createClientApp(String clientId, String appId, String name) {
        validateClientId(clientId);
        validateAppId(appId);
        validateName(name, null);
        return clientAppPort.createClientApp(clientId, appId, name);
    }

    @Override
    public void updateClientApp(String clientId, String appId, String name, Boolean active) {
        ClientApp existing = clientAppPort.getClientApp(clientId, appId);
        if (existing == null) {
            throw new ValidationException("Client app not found");
        }
        validateName(name, existing.getId());
        clientAppPort.updateClientApp(existing.getId(), name, active);
    }

    private void validateClientId(String clientId) {
        if (clientId == null || clientId.isBlank()) {
            throw new ValidationException("Client is required");
        }
        if (clientPort.getClientById(clientId) == null) {
            throw new ValidationException("Client not found");
        }
    }

    private void validateAppId(String appId) {
        if (appId == null || appId.isBlank()) {
            throw new ValidationException("App is required");
        }
        if (appPort.getAppById(appId) == null) {
            throw new ValidationException("App not found");
        }
    }

    private void validateName(String name, String excludeId) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Name is required");
        }
        if (clientAppPort.existsByName(name, excludeId)) {
            throw new ValidationException("A client app with that name already exists");
        }
    }

}
