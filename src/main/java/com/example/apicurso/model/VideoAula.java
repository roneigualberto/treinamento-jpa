package com.example.apicurso.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VideoAula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne
    @JoinColumn(nullable = false)
    private Aula aula;

    @Column
    private String nomeArquivo;

    @Column
    private Long tamanho;

    @Column
    private String tipo;

    @Column
    @Lob
    private byte[] conteudo;

}
