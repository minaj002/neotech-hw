package com.neotech;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerController {

    @Bean
    public Docket serviceApiLogin() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.neotech.controller"))
                .build()
                .useDefaultResponseMessages(false)
                .enableUrlTemplating(false)
                ;
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Neotech API Documentation")
                .build();
    }

}
