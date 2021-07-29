package com.example.apicurso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CursoProjectionDTO {

    private Long id;

    private String nome;

    private Double preco;
}
