package es.maneldevs.application.service;

import java.util.List;

import es.maneldevs.application.portin.ClientUseCase;
import es.maneldevs.application.portout.ClientPort;
import es.maneldevs.domain.exception.ValidationException;
import es.maneldevs.domain.model.Client;

public class ClientService implements ClientUseCase {
    private final ClientPort clientPort;

    public ClientService(ClientPort clientPort) {
        this.clientPort = clientPort;
    }

    @Override
    public List<Client> getClients() {
        return clientPort.getClients();
    }

    @Override
    public Client getClientById(String id) {
        return clientPort.getClientById(id);
    }

    @Override
    public Client createClient(String name) {
        validateName(name, null);
        return clientPort.createClient(name);
    }

    @Override
    public void updateClient(String id, String name) {
        validateName(name, id);
        clientPort.updateClient(id, name);
    }

    private void validateName(String name, String excludeId) {
        if (name == null || name.isBlank()) {
            throw new ValidationException("Name is required");
        }
        if (clientPort.existsByName(name, excludeId)) {
            throw new ValidationException("A client with that name already exists");
        }
    }

}
