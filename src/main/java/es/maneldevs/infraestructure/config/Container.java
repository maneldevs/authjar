package es.maneldevs.infraestructure.config;

import java.util.List;

import com.zaxxer.hikari.HikariDataSource;

import es.maneldevs.application.portin.AuthUseCase;
import es.maneldevs.application.portout.ApiKeyPort;
import es.maneldevs.application.portout.SessionPort;
import es.maneldevs.application.portout.UserPort;
import es.maneldevs.application.service.AuthService;
import es.maneldevs.infraestructure.in.adapter.HttpController;
import es.maneldevs.infraestructure.in.adapter.api.AuthApiController;
import es.maneldevs.infraestructure.in.adapter.api.DefaultApiController;
import es.maneldevs.infraestructure.in.adapter.b2b.DefaultB2BController;
import es.maneldevs.infraestructure.in.adapter.web.AuthWebController;
import es.maneldevs.infraestructure.in.adapter.web.DefaultWebController;
import es.maneldevs.infraestructure.in.adapter.web.DeshboardWebController;
import es.maneldevs.infraestructure.out.persistence.ApiKeyRepository;
import es.maneldevs.infraestructure.out.persistence.SessionRepository;
import es.maneldevs.infraestructure.out.persistence.UserRepository;

public class Container {
    public List<HttpController> controllers;
    public SecurityFilter securityFilter;

    public Container(HikariDataSource dataSource) {
        UserPort userPort = new UserRepository(dataSource);
        ApiKeyPort apiKeyPort = new ApiKeyRepository(dataSource);
        SessionPort sessionPort = new SessionRepository(dataSource);
        AuthUseCase authUseCase = new AuthService(userPort, apiKeyPort, sessionPort);
        this.securityFilter = new SecurityFilter(authUseCase);
        DefaultWebController defaultWebController = new DefaultWebController();
        AuthWebController authWebController = new AuthWebController(authUseCase);
        DefaultApiController defaultApiController = new DefaultApiController();
        AuthApiController authApiController = new AuthApiController(authUseCase);
        DefaultB2BController defaultB2BController = new DefaultB2BController();
        DeshboardWebController deshboardWebController = new DeshboardWebController();
        this.controllers = List.of(
                defaultWebController,
                authWebController,
                defaultApiController,
                authApiController,
                defaultB2BController,
                deshboardWebController);
    }
}
