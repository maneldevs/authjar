package es.maneldevs.application.portin;

import java.util.List;

import es.maneldevs.domain.model.Client;

public interface ClientUseCase {
    List<Client> getClients();

    Client getClientById(String id);

    Client createClient(String name);

    void updateClient(String id, String name, boolean active);
}
