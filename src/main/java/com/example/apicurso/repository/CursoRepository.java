package com.example.apicurso.repository;


import com.example.apicurso.model.Curso;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Lazy
public interface CursoRepository extends JpaRepository<Curso, Long> {
}
