package es.maneldevs.infraestructure.config;

import java.util.List;

import com.zaxxer.hikari.HikariDataSource;

import es.maneldevs.application.service.AuthService;
import es.maneldevs.infraestructure.in.adapter.HttpController;
import es.maneldevs.infraestructure.in.adapter.api.AuthApiController;
import es.maneldevs.infraestructure.in.adapter.api.DefaultApiController;
import es.maneldevs.infraestructure.in.adapter.web.DefaultWebController;

public class Container {
    public List<HttpController> controllers;
    public SecurityFilter securityFilter;

    public Container(HikariDataSource dataSource) {
        var userPort = new UserRepository(dataSource);
        var apiKeyPort = new ApiKeyRepository(dataSource);
        var AuthUseCase = new AuthService(userPort, apiKeyPort);
        this.securityFilter = new SecurityFilter(AuthUseCase);
        var defaultWebController = new DefaultWebController();
        var defaultApiController = new DefaultApiController();
        var authApiController = new AuthApiController(AuthUseCase);
        this.controllers = List.of(
                defaultWebController,
                defaultApiController,
                authApiController);
    }
}
