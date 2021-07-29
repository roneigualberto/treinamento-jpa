package com.example.apicurso.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Enumerated(EnumType.STRING)
    private NivelCursoEnum nivel;

    private String instrutor;

    private Double preco;

    @CreationTimestamp
    private LocalDateTime dtHoraAtualizacao;

    @OneToMany(mappedBy = "curso")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OrderBy("ordem")
    @ToString.Exclude
    private List<Aula> aulas;

    @ManyToMany
    @JoinTable(name = "curso_categoria", joinColumns = @JoinColumn(name = "curso_id"), inverseJoinColumns = @JoinColumn(name = "categoria_id"))
    @Builder.Default
    private List<Categoria> categorias = new ArrayList<>();


    @PrePersist
    @PreUpdate
    @PreRemove
    protected void prePersist() {
        this.dtHoraAtualizacao = LocalDateTime.now();
    }


    public void addCategoria(Categoria categoria) {
        this.categorias.add(categoria);
    }


}
