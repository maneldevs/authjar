package es.maneldevs.domain.model;

public class ClientApp {
    private final String id;
    private final String name;
    private final Boolean active;
    private final Client client;
    private final App app;
    private final String apiKeyHash;

    public ClientApp(String id, String name, Boolean active, Client client, App app, String apiKeyHash) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.client = client;
        this.app = app;
        this.apiKeyHash = apiKeyHash;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getActive() {
        return active;
    }

    public Client getClient() {
        return client;
    }

    public App getApp() {
        return app;
    }

    public String getApiKeyHash() {
        return apiKeyHash;
    }
}
