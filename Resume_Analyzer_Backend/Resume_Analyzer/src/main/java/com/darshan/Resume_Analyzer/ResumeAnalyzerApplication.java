package com.darshan.Resume_Analyzer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ResumeAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ResumeAnalyzerApplication.class, args);
	}

	// ✅ Enable global CORS for your frontend (React or Postman)
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")  // allow all endpoints
						.allowedOrigins("http://localhost:5173") // React frontend
						.allowedMethods("*"); // Allow GET, POST, etc.
			}
		};
	}
}
