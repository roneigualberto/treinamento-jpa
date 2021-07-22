package com.example.apicurso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {

    private Set<Long> cursos;

    private CartaoDTO cartao;

    private Integer parcelas;

}
