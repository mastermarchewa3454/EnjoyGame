package com.enjoy.mathero;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.*;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET,
                        Arrays.asList(new ResponseMessageBuilder()
                                        .code(200)
                                        .message("Successfully returned")
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(403)
                                        .message("Accessing the resource you were trying to reach is forbidden")
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(404)
                                        .message("Object with provided id not found")
                                        .build()))
                .globalResponseMessage(RequestMethod.POST,
                        Arrays.asList(new ResponseMessageBuilder()
                                        .code(200)
                                        .message("Successfully created")
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(403)
                                        .message("Accessing the resource you were trying to reach is forbidden")
                                        .build(),
                                new ResponseMessageBuilder()
                                        .code(400)
                                        .message("The request body is invalid")
                                        .build()))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.enjoy.mathero.ui.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiEndPointsInfo())
                .produces(new HashSet<>(Collections.singletonList("application/json")));
    }

    @Bean
    public UiConfiguration tryItOutConfig() {
        final String[] methodsWithTryItOutButton = {  };
        return UiConfigurationBuilder.builder().supportedSubmitMethods(methodsWithTryItOutButton).build();
    }

    private ApiInfo apiEndPointsInfo() {
        return new ApiInfoBuilder().title("Mathero REST API")
                .description("CZ3003 Software Systems Analysis & Design")
                .contact(new Contact("Enjoy", "https://github.com/mastermarchewa3454/EnjoyGame", null))
                .version("1.0.0")
                .build();
    }
}
