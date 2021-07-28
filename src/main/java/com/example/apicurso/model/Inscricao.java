package com.example.apicurso.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import java.time.LocalDateTime;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedStoredProcedureQuery(
        name = "Inscricao.concluirCurso",
        procedureName = Inscricao.CONCLUIR_CURSO_PROCEDURE,
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_inscricao_id", type = Long.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_nota", type = Float.class),
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "p_comentario", type = String.class)
        }
)
public class Inscricao {


    public static final String CONCLUIR_CURSO_PROCEDURE = "CONCLUIR_CURSO";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "inscricao_aluno_fk"))
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(nullable = false, foreignKey = @ForeignKey(name = "inscricao_curso_fk"))
    private Curso curso;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime dtHoraInicio;

    @Column
    @ColumnDefault("false")
    @Builder.Default
    private Boolean concluido = false;

    @Column
    private Float nota;

    @Column
    private String comentario;

    @Column
    private LocalDateTime dtHoraConclusao;

}
