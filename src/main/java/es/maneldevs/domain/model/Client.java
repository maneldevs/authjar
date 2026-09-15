package es.maneldevs.domain.model;

public class Client {
    private final String id;
    private final String name;
    private final boolean active;

    public Client(String id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }
}
