package es.maneldevs.application.service;

import java.util.List;

import es.maneldevs.application.portin.AppUseCase;
import es.maneldevs.application.portout.AppPort;
import es.maneldevs.domain.exception.ValidationException;
import es.maneldevs.domain.model.App;

public class AppService implements AppUseCase {
    private final AppPort appPort;

    public AppService(AppPort appPort) {
        this.appPort = appPort;
    }

    @Override
    public List<App> getClients() {
        return appPort.getApps();
    }

    @Override
    public App getClientById(String id) {
        return appPort.getAppById(id);
    }

    @Override
    public App createClient(String name) {
        validateName(name, null);
        return appPort.createApp(name);
    }

    @Override
    public void updateClient(String id, String name) {
        validateName(name, id);
        appPort.updateApp(id, name);
    }

    private void validateName(String name, String excludeId) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Name is required");
        }
        if (appPort.existsByName(name, excludeId)) {
            throw new ValidationException("An app with that name already exists");
        }
    }
    
}
