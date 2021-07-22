package com.example.apicurso.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Cartao {

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoCartaoEnum tipo;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private Integer mesExpiracao;

    @Column(nullable = false)
    private Integer anoExpiracao;

    @Column(nullable = false)
    private Integer cvv;

}
