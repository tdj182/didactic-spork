package com.revature.profiles;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("spring.datasource")
public class DBConfiguration {

	private String driverClassName;
	private String url;
	private String username;
	private String password;
	
	@Profile("dev") // this correlates to spring.profiles.active in application.properties
	@Bean
	public String devDatabaseConnection() {
			System.out.println("DEB CONNECTION FOR DEV - H2");
			System.out.println(driverClassName);
			System.out.println(url);
			return "DEB CONNECTION FOR DEV - H2";
	}
	
	@Profile("test") // this correlates to spring.profiles.active in application.properties
	@Bean
	public String testDatabaseConnection() {
			System.out.println("DEB CONNECTION FOR TEST - low cost RDS instance");
			System.out.println(driverClassName);
			System.out.println(url);
			return "DEB CONNECTION FOR TEST - low cost RDS instance";
	}
	
	@Profile("prod") // this correlates to spring.profiles.active in application.properties
	@Bean
	public String prodDatabaseConnection() {
			System.out.println("DEB CONNECTION FOR PROD - high performance RDS instance");
			System.out.println(driverClassName);
			System.out.println(url);
			return "DEB CONNECTION FOR PROD - high performance RDS instance";
	}
	
}
