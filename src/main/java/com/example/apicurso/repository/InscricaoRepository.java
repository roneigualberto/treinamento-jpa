package com.example.apicurso.repository;


import com.example.apicurso.model.Inscricao;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Lazy
public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {

    @Procedure(Inscricao.CONCLUIR_CURSO_PROCEDURE)
    void concluirCurso(@Param("p_inscricao_id") Long incricaoId, @Param("p_nota") Float nota, @Param("p_comentario") String comentario);
}
