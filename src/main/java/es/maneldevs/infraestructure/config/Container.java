package es.maneldevs.infraestructure.config;

import java.util.List;

import com.zaxxer.hikari.HikariDataSource;

import es.maneldevs.application.portin.AppUseCase;
import es.maneldevs.application.portin.AuthUseCase;
import es.maneldevs.application.portin.ClientAppUseCase;
import es.maneldevs.application.portin.ClientUseCase;
import es.maneldevs.application.portout.ApiKeyPort;
import es.maneldevs.application.portout.AppPort;
import es.maneldevs.application.portout.ClientAppPort;
import es.maneldevs.application.portout.ClientPort;
import es.maneldevs.application.portout.SessionPort;
import es.maneldevs.application.portout.UserPort;
import es.maneldevs.application.service.AppService;
import es.maneldevs.application.service.AuthService;
import es.maneldevs.application.service.ClientAppService;
import es.maneldevs.application.service.ClientService;
import es.maneldevs.infraestructure.in.adapter.HttpController;
import es.maneldevs.infraestructure.in.adapter.api.AuthApiController;
import es.maneldevs.infraestructure.in.adapter.api.DefaultApiController;
import es.maneldevs.infraestructure.in.adapter.b2b.DefaultB2BController;
import es.maneldevs.infraestructure.in.adapter.web.AppWebController;
import es.maneldevs.infraestructure.in.adapter.web.AuthWebController;
import es.maneldevs.infraestructure.in.adapter.web.ClientAppWebController;
import es.maneldevs.infraestructure.in.adapter.web.ClientWebController;
import es.maneldevs.infraestructure.in.adapter.web.DashboardWebController;
import es.maneldevs.infraestructure.in.adapter.web.DefaultWebController;
import es.maneldevs.infraestructure.out.persistence.ApiKeyRepository;
import es.maneldevs.infraestructure.out.persistence.AppRepository;
import es.maneldevs.infraestructure.out.persistence.ClientAppRepository;
import es.maneldevs.infraestructure.out.persistence.ClientRepository;
import es.maneldevs.infraestructure.out.persistence.SessionRepository;
import es.maneldevs.infraestructure.out.persistence.UserRepository;

public class Container {
    public List<HttpController> controllers;
    public SecurityFilter securityFilter;

    public Container(HikariDataSource dataSource) {
        UserPort userPort = new UserRepository(dataSource);
        ApiKeyPort apiKeyPort = new ApiKeyRepository(dataSource);
        SessionPort sessionPort = new SessionRepository(dataSource);
        ClientPort clientPort = new ClientRepository(dataSource);
        AppPort appPort = new AppRepository(dataSource);
        ClientAppPort clientAppPort = new ClientAppRepository(dataSource);

        AuthUseCase authUseCase = new AuthService(userPort, apiKeyPort, sessionPort);
        ClientUseCase clientUseCase = new ClientService(clientPort);
        AppUseCase appUseCase = new AppService(appPort);
        ClientAppUseCase clientAppUseCase = new ClientAppService(clientAppPort, clientPort, appPort);

        this.securityFilter = new SecurityFilter(authUseCase);

        DefaultWebController defaultWebController = new DefaultWebController();
        AuthWebController authWebController = new AuthWebController(authUseCase);
        DefaultApiController defaultApiController = new DefaultApiController();
        AuthApiController authApiController = new AuthApiController(authUseCase);
        DefaultB2BController defaultB2BController = new DefaultB2BController();
        DashboardWebController deshboardWebController = new DashboardWebController();
        ClientWebController clientWebController = new ClientWebController(clientUseCase);
        AppWebController appWebController = new AppWebController(appUseCase);
        ClientAppWebController clientAppWebController = new ClientAppWebController(clientAppUseCase, clientUseCase, appUseCase);
        
        this.controllers = List.of(
                defaultWebController,
                authWebController,
                defaultApiController,
                authApiController,
                defaultB2BController,
                deshboardWebController,
                clientWebController,
                appWebController,
                clientAppWebController
            );
    }
}
