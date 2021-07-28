package com.example.apicurso.controller;


import com.example.apicurso.dto.CartaoDTO;
import com.example.apicurso.dto.CompraDTO;
import com.example.apicurso.dto.CredencialDTO;
import com.example.apicurso.model.Aluno;
import com.example.apicurso.model.Cartao;
import com.example.apicurso.model.Compra;
import com.example.apicurso.model.Curso;
import com.example.apicurso.model.Inscricao;
import com.example.apicurso.repository.AlunoRepository;
import com.example.apicurso.repository.CompraRepository;
import com.example.apicurso.repository.CursoRepository;
import com.example.apicurso.repository.InscricaoRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
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
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/alunos")
@AllArgsConstructor
public class AlunoController {


    private final AlunoRepository alunoRepository;

    private final CursoRepository cursoRepository;

    private final CompraRepository compraRepository;

    private final InscricaoRepository inscricaoRepository;

    private final MessageSource messageSource;

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
        String format = MessageFormat.format("Aluno {0}", id);
        Aluno aluno = alunoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, messageSource.getMessage("aluno.nao.encontrado", new Object[]{id}, LocaleContextHolder.getLocale())));
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

        //Incricoes

        List<Inscricao> inscricoes = cursos.stream().map((curso) -> Inscricao.builder().aluno(aluno).curso(curso).build()).collect(Collectors.toList());

        inscricaoRepository.saveAll(inscricoes);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(compraSalva.getId())
                .toUri();

        return ResponseEntity.created(location).body(compraSalva);

    }

    @PostMapping("/autentica")
    public ResponseEntity<Aluno> autenticar(@RequestBody CredencialDTO credencial) {

        Aluno aluno = alunoRepository.autentica(credencial).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Email ou senha invalidos"));

        return ResponseEntity.ok(aluno);
    }

}
