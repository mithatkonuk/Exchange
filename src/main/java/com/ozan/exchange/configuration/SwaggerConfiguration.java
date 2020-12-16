package com.ozan.exchange.configuration;

import io.swagger.annotations.ExternalDocs;
import io.swagger.annotations.Info;
import io.swagger.annotations.License;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Configuration
@EnableSwagger2
@SwaggerDefinition( info = @Info( description = "Ozan Exchange Service",
                                  version = "V12.0.12",
                                  title = "Awesome Resource API",
                                  contact = @io.swagger.annotations.Contact( name = "Mithat Konuk",
                                                                             email = "mithatkonuk@gmail.com",
                                                                             url = "https://www.linkedin.com/in/mithat-konuk-0652831ab/" ),
                                  license = @License( name = "Apache 2.0",
                                                      url = "http://www.apache.org/licenses/LICENSE-2.0" ) ),
                    consumes = { "application/json" },
                    produces = { "application/json" },
                    schemes = { SwaggerDefinition.Scheme.HTTP },
                    externalDocs = @ExternalDocs( value = "Contact me",
                                                  url = "https://www.linkedin.com/in/mithat-konuk-0652831ab/" ) )

public class SwaggerConfiguration
{

    public static final Contact DEFAULT_CONTACT = new Contact("Mithat Konuk",
                    "https://www.linkedin.com/in/mithat-konuk-0652831ab/", "mithatkonuk@gmail.com");

    public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Ozan Exchange Simple Api",
                    "Ozan Exchange is simple rate application", "1.0", "", DEFAULT_CONTACT,
                    "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", Arrays.asList());

    private static final Set<String> DEFAULT_PRODUCES_AND_CONSUMES =
                    new HashSet<String>(Arrays.asList("application/json", "application/xml"));

    @Bean
    public Docket api()
    {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO)
                        .produces(DEFAULT_PRODUCES_AND_CONSUMES)
                        .consumes(DEFAULT_PRODUCES_AND_CONSUMES).select()
                        .apis(RequestHandlerSelectors.any()).paths(PathSelectors.any())
                        .paths(Predicate.not(PathSelectors.regex("/error.*"))).build();
    }

}
