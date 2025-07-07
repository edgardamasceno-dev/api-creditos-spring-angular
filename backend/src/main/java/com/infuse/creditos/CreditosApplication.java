package com.infuse.creditos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CreditosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CreditosApplication.class, args);
	}

	@Configuration
	class DocsRedirectConfig implements WebMvcConfigurer {
		@Override
		public void addViewControllers(ViewControllerRegistry registry) {
			registry.addRedirectViewController("/docs", "/docs.html");
			registry.addRedirectViewController("/swagger", "/swagger-ui.html");
		}
	}

}
