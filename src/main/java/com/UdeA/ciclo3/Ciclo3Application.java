package com.UdeA.ciclo3;

import com.UdeA.ciclo3.Modelos.Empresa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication (exclude= {SecurityAutoConfiguration.class})

public class Ciclo3Application {
	@GetMapping ("/hello")
	public String hello () {
		return "hola ciclo 3... saldremos vivos de esto";

	}
	@GetMapping("/test")
	public String test (){
		Empresa emp =new Empresa("Cocacola","Calle del hambre","321321321","8000000");
		emp.setNombre("Feliz Domingo");
		return emp.getNombre();

	}


	public static void main(String[] args) {
		SpringApplication.run(Ciclo3Application.class, args);
	}

}
