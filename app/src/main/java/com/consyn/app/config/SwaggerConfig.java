package com.consyn.app.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
    
public class SwaggerConfig {

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.consyn.app.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(metadata())
				.useDefaultResponseMessages(false)
				.genericModelSubstitutes(Optional.class);

	}

	private ApiInfo metadata() {
		return new ApiInfoBuilder()//
				.title("CONSYN")//
				.description(
						"This is a sample JWT authentication service. You can find out more about JWT at [https://jwt.io/](https://jwt.io/). For this sample, you can use admin or user role customers to test the authorization filters. Once you have successfully logged in and obtained the token, you should click on the right top button `Authorize` and introduce it with the prefix \"Bearer \".")//
				.version("1.0.0")//
				.license("MIT License").licenseUrl("")//
				.contact(new Contact(null, null, "gsreekanth528@gmail.com"))//
				.build();
	}

}
