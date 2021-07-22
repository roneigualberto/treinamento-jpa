package com.example.apicurso;

import com.example.apicurso.model.Categoria;
import com.example.apicurso.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@SpringBootApplication
public class ApiCursoApplication implements ApplicationRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	public static void main(String[] args) {
		SpringApplication.run(ApiCursoApplication.class, args);
	}

	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {

		//		Categoria categoria = Categoria.builder()
		//				.nome("DevOps")
		//				.build();
		//
		//		categoriaRepository.save(categoria);
		//
		//		System.out.println("Curso API");
	}
}
