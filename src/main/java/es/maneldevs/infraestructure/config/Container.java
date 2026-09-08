package es.maneldevs.infraestructure.config;

import java.util.List;

import com.zaxxer.hikari.HikariDataSource;

import es.maneldevs.application.portin.AuthUseCase;
import es.maneldevs.application.portout.ApiKeyPort;
import es.maneldevs.application.portout.UserPort;
import es.maneldevs.application.service.AuthService;
import es.maneldevs.infraestructure.in.adapter.HttpController;
import es.maneldevs.infraestructure.in.adapter.api.AuthApiController;
import es.maneldevs.infraestructure.in.adapter.api.DefaultApiController;
import es.maneldevs.infraestructure.in.adapter.web.DefaultWebController;
import es.maneldevs.infraestructure.out.persistence.ApiKeyRepository;
import es.maneldevs.infraestructure.out.persistence.UserRepository;

public class Container {
    public List<HttpController> controllers;
    public SecurityFilter securityFilter;

    public Container(HikariDataSource dataSource) {
        UserPort userPort = new UserRepository(dataSource);
        ApiKeyPort apiKeyPort = new ApiKeyRepository(dataSource);
        AuthUseCase authUseCase = new AuthService(userPort, apiKeyPort);
        this.securityFilter = new SecurityFilter(authUseCase);
        DefaultWebController defaultWebController = new DefaultWebController();
        DefaultApiController defaultApiController = new DefaultApiController();
        AuthApiController authApiController = new AuthApiController(authUseCase);
        this.controllers = List.of(
                defaultWebController,
                defaultApiController,
                authApiController);
    }
}
