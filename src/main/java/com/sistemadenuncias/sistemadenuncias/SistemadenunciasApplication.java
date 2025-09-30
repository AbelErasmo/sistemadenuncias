package com.sistemadenuncias.sistemadenuncias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages =  "com.sistemadenuncias.sistemadenuncias")
public class SistemadenunciasApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemadenunciasApplication.class, args);
	}

}
