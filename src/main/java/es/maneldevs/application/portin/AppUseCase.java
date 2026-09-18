package es.maneldevs.application.portin;

import java.util.List;

import es.maneldevs.domain.model.App;

public interface AppUseCase {
    List<App> getClients();

    App getClientById(String id);

    App createClient(String name);

    void updateClient(String id, String name);
}
