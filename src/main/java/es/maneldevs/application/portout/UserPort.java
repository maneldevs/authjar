package es.maneldevs.application.portout;

import es.maneldevs.domain.model.User;

public interface UserPort {

    User getUserLoggedFromSessionNotExpired(String sessionId);

    User getActiveUserById(String userId);

    User getUserByEmail(String email);
    
}
