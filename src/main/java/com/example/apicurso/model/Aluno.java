package com.example.apicurso.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.validation.constraints.Email;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String sobrenome;

//    @Column(nullable = false)
//    @CPF(message = "{cpf.invalido}")
//    private String cpf;

    @Column(nullable = false, unique = true)
    @Email()
    private String email;

    @Column
    private String senha;

    @ElementCollection
    @CollectionTable(name = "aluno_contato")
    @MapKeyColumn(name = "tipo_contato")
    @Column(name = "identificador")
    @Builder.Default
    @MapKeyEnumerated(EnumType.STRING)
    private Map<TipoContatoEnum, String> contatos = new HashMap<>();

}
