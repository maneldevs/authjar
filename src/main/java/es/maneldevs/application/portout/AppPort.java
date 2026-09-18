package es.maneldevs.application.portout;

import java.util.List;

import es.maneldevs.domain.model.App;

public interface AppPort {
    List<App> getApps();

    App getAppById(String id);

    boolean existsByName(String name, String excludeId);

    App createApp(String name);

    void updateApp(String id, String name);
}
