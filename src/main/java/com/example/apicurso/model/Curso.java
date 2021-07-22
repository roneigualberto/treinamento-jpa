package com.example.apicurso.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 4000)
    private String descricao;

    private NivelCursoEnum nivel;

    private String instrutor;

    private Double preco;

    @CreationTimestamp
    private LocalDateTime dtHoraAtualizacao;

    @OneToMany(mappedBy = "curso")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OrderBy("ordem")
    private List<Aula> aulas;


    @PrePersist
    @PreUpdate
    @PreRemove
    protected void prePersist() {
        this.dtHoraAtualizacao = LocalDateTime.now();
    }
}
