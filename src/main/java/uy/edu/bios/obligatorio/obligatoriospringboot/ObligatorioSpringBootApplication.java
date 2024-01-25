package uy.edu.bios.obligatorio.obligatoriospringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:valores.properties")
public class ObligatorioSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObligatorioSpringBootApplication.class, args);
	}

}
