package com.example.apicurso.repository;


import com.example.apicurso.model.Aula;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Lazy
public interface AulaRepository extends JpaRepository<Aula, Long> {
}
