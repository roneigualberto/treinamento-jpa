package com.example.apicurso.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    private Aluno aluno;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.PERSIST)
    @Builder.Default
    private List<ItemCompra> itens = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime dtHoraRealizacao;


    private Integer parcelas;

    @Embedded
    @Builder.Default
    private Cartao cartao = new Cartao();

    public void addCursos(List<Curso> cursos) {
        this.itens = cursos
                .stream()
                .map((curso -> new ItemCompra(null, this, curso, curso.getPreco()))).collect(Collectors.toList());
    }


    //DTO - VO- Value Object


}
