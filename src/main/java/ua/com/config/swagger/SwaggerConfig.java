package ua.com.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
@ComponentScan(basePackages = "ua.com.controller.profile")
public class SwaggerConfig {

    private static final String TITLE = "RabotyNET REST API Documentation";
    private static final String DESCRIPTION = "RESTFull API Documentation";
    private static final String VERSION = "1.0";
    private static final String BASIC_AUTH = "BasicAuth";

    @Bean
    public Docket basicAuthSecuredApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("Basic Authorization")
                .select()
                .apis(RequestHandlerSelectors.basePackage("ua.com.controller"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(new BasicAuth(BASIC_AUTH)))
                .securityContexts(Collections.singletonList(basicSecurityContext()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(TITLE)
                .description(DESCRIPTION)
                .version(VERSION)
                .build();
    }

    private SecurityContext basicSecurityContext() {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference(BASIC_AUTH,
                        new AuthorizationScope[0])))
                .build();
    }

}
