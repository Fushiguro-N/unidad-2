package pe.edu.upeu.lms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LmsMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LmsMsApplication.class, args);
	}

}
