package br.com.bandtec.AC3Yoshi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Ac3YoshiApplication {

	public static void main(String[] args) {
		SpringApplication.run(Ac3YoshiApplication.class, args);
	}

}
