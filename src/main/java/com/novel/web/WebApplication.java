package com.novel.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		log.info("starting the application....");
		SpringApplication.run(WebApplication.class, args);
	}

}
