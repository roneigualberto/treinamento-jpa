package com.example.apicurso.repository;


import com.example.apicurso.model.Curso;
import com.example.apicurso.model.NivelCursoEnum;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
@Lazy
public interface CursoRepository extends JpaRepository<Curso, Long> {


    List<Curso> searchByNome(String nome);

    List<Curso> readByNomeLike(String nome);

    List<Curso> getByNomeContaining(String nome);

    List<Curso> queryByNivelIn(Set<NivelCursoEnum> nivel);

    List<Curso> findTop3ByNivelOrderByPreco(NivelCursoEnum nivel);

    List<Curso> findTop3ByCategorias_NomeOrderByPreco(String nome);

    List<Curso> findByPrecoBetween(Double min, Double max);

    <T> List<T> findByInstrutor(String instrutor, Class<T> projectionClass);


}
