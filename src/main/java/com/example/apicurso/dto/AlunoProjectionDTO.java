package com.example.apicurso.dto;

import org.springframework.beans.factory.annotation.Value;

public interface AlunoProjectionDTO {

    Long getId();

    @Value("#{target.nome + ' ' + target.sobrenome}")
    String getNomeCompleto();

}
