package com.excilys.mviegas.computer_database.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.HashSet;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo())
                .consumes(new HashSet<>(Collections.singletonList("application/json")))
                .produces(new HashSet<>(Collections.singletonList("application/json")))

				;
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Api for Computer Database Project",
				"This is a REST Api for using the Computer Database",
				"0.37",
				null,
				contact(),
				null,
				null
		);
	}

	private Contact contact() {
		return new Contact("VIEGAS Mickael", "http://github.com/VmickaelV", "viegas.mickael@gmail.com");
	}
}


