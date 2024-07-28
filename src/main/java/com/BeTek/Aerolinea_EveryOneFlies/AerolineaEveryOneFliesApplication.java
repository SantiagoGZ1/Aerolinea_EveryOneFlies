package com.BeTek.Aerolinea_EveryOneFlies;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class AerolineaEveryOneFliesApplication {

	public static void main(String[] args) {
		System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", System.getenv("GOOGLE_APPLICATION_CREDENTIALS"));
		SpringApplication.run(AerolineaEveryOneFliesApplication.class, args);
	}

	@Bean
	public OpenAPI customApi(){
		return new OpenAPI()
				.info(new Info()
						.title("Aerolinea_EveryOneFlies")
						.version("0.1")
						.description("Sistema de reservas para vuelos comerciales")
						.termsOfService("http://swagger.io/terms/")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}
}
