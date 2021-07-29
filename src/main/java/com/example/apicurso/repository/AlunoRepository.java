package com.example.apicurso.repository;


import com.example.apicurso.dto.CredencialDTO;
import com.example.apicurso.model.Aluno;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Lazy
public interface AlunoRepository extends JpaRepository<Aluno, Long> {


    @Query("select al from Aluno al where al.email = :#{#c.email} and al.senha = :#{#c.senha}")
    Optional<Aluno> autentica(@Param("c") CredencialDTO credencial);


    <T> Optional<T> findByEmail(String email, Class<T> projectionClass);


//    default <T> Map<Long, T> findAll2() {
//        return this.findAll().stream().collect(Collectors.toMap(Aluno::getId, Function.identity()));
//    }


}
