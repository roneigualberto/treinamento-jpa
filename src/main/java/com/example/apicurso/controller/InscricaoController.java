package com.example.apicurso.controller;


import com.example.apicurso.dto.ConclusaoCursoDTO;
import com.example.apicurso.repository.InscricaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inscricoes")
@AllArgsConstructor
public class InscricaoController {


    private final InscricaoRepository inscricaoRepository;


    @PutMapping("{inscricaoId}/conclusao-curso")
    @Transactional
    public ResponseEntity<?> conclusaoCurso(@PathVariable Long inscricaoId, @RequestBody ConclusaoCursoDTO dto) {

        inscricaoRepository.concluirCurso(inscricaoId, dto.getNota(), dto.getComentario());

        return ResponseEntity.ok().build();
    }

}
