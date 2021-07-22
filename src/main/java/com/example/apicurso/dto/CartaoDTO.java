package com.example.apicurso.dto;

import com.example.apicurso.model.TipoCartaoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoDTO {


    private TipoCartaoEnum tipo;


    private String numero;


    private Integer mesExpiracao;


    private Integer anoExpiracao;

    private Integer cvv;

}
