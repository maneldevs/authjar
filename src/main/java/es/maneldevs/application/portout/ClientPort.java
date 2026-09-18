package es.maneldevs.application.portout;

import java.util.List;

import es.maneldevs.domain.model.Client;

public interface ClientPort {
    List<Client> getClients();

    Client getClientById(String id);

    boolean existsByName(String name, String excludeId);

    Client createClient(String name);

    void updateClient(String id, String name);
}
