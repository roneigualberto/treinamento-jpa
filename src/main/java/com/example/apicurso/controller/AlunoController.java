package com.example.apicurso.controller;


import com.example.apicurso.dto.CartaoDTO;
import com.example.apicurso.dto.CompraDTO;
import com.example.apicurso.model.Aluno;
import com.example.apicurso.model.Cartao;
import com.example.apicurso.model.Compra;
import com.example.apicurso.model.Curso;
import com.example.apicurso.repository.AlunoRepository;
import com.example.apicurso.repository.CompraRepository;
import com.example.apicurso.repository.CursoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/alunos")
@AllArgsConstructor
public class AlunoController {


    private final AlunoRepository alunoRepository;

    private final CursoRepository cursoRepository;

    private final CompraRepository compraRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Aluno> salvar(@RequestBody @Valid Aluno aluno) {

        Aluno alunoSalvo = alunoRepository.save(aluno);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(alunoSalvo.getId())
                .toUri();

        return ResponseEntity.created(location).body(alunoSalvo);
    }

    @GetMapping("{id}")
    public ResponseEntity<Aluno> consultar(@PathVariable Long id) {
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));
        return ResponseEntity.ok(aluno);
    }


    @PostMapping("{alunoId}/compra")
    @Transactional
    public ResponseEntity<Compra> comprar(@PathVariable("alunoId") Long alunoId, @RequestBody CompraDTO compraDTO) {

        Aluno aluno = alunoRepository.findById(alunoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado"));

        List<Curso> cursos = cursoRepository.findAllById(compraDTO.getCursos());


        Compra compra = Compra.builder()
                .aluno(aluno)
                .parcelas(compraDTO.getParcelas())
                .build();

        Cartao cartao = compra.getCartao();
        CartaoDTO cartaoDTO = compraDTO.getCartao();

        cartao.setTipo(cartaoDTO.getTipo());
        cartao.setNumero(cartaoDTO.getNumero());
        cartao.setMesExpiracao(cartaoDTO.getMesExpiracao());
        cartao.setAnoExpiracao(cartaoDTO.getAnoExpiracao());
        cartao.setCvv(cartaoDTO.getCvv());

        compra.addCursos(cursos);

        Compra compraSalva = compraRepository.save(compra);


        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(compraSalva.getId())
                .toUri();

        List.of("test").stream().anyMatch()

        return ResponseEntity.created(location).body(compraSalva);

    }

}
