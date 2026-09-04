package es.maneldevs.infraestructure.config;

import java.util.List;

import com.zaxxer.hikari.HikariDataSource;

import es.maneldevs.infraestructure.in.HttpController;
import es.maneldevs.infraestructure.in.api.DefaultApiController;
import es.maneldevs.infraestructure.in.web.DefaultWebController;

public class Container {

    public static List<HttpController> buildControllers(HikariDataSource dataSource) {
        // var authRepository = new PostgresAuthRepository(dataSource);
        // var loginUseCase = new LoginUseCase(authRepository);
        // var authWebController = new AuthWebController(loginUseCase);
        // var authApiController = new AuthApiController(loginUseCase);
        var defaultWebController = new DefaultWebController();
        var defaultApiController = new DefaultApiController();
        return List.of(
                // authWebController,
                // authApiController
                defaultWebController,
                defaultApiController);

    }
}
