package com.mobilise.BookManagementSystem;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
		info = @Info(
				title = "BOOK-MANAGEMENT REST APIs",
				description = "BOOK-MANAGEMENT REST API ENDPOINTS DOCUMENTATIONS",
				version = "v1.0.0",
				contact = @Contact(
						name = "Bennett",
						email = "info@bennett.com",
						url = "https://www.bennett.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.bennett.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "BOOK-MANAGEMENT REST API ENDPOINTS DOCUMENTATIONS",
				url = "https://www.bennett.com"
		)
)
@SpringBootApplication
public class BookManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookManagementSystemApplication.class, args);
	}

}
