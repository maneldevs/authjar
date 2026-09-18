package es.maneldevs.domain.model;

public class App {
    private final String id;
    private final String name;

    public App(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
}
