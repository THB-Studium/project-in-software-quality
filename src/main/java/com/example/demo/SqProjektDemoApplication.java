package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.context.WebServerPortFileWriter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EnableAutoConfiguration(exclude = { ErrorMvcAutoConfiguration.class, SolrAutoConfiguration.class })
@EntityScan(basePackages = { "com.example.demo.model" })
public class SqProjektDemoApplication {

	// @PostConstruct
	public static void main(String[] args) {

		/*** create application ***/
		SpringApplication app = new SpringApplication(SqProjektDemoApplication.class);

		/*** add pid / port file writers ***/
		app.addListeners(new ApplicationPidFileWriter());
		app.addListeners(new WebServerPortFileWriter());

		/*** run application ***/
		app.run(args);
	}

}
