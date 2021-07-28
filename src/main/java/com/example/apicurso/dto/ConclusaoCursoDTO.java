package com.example.apicurso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConclusaoCursoDTO {

    private Float nota;

    private String comentario;
}
