package com.example.apicurso.controller;


import com.example.apicurso.model.Aula;
import com.example.apicurso.model.Curso;
import com.example.apicurso.repository.AulaRepository;
import com.example.apicurso.repository.CursoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cursos")
@AllArgsConstructor
public class CursoController {

    private final CursoRepository cursoRepository;

    private final AulaRepository aulaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Curso> salvar(@RequestBody Curso curso) {

        Curso cursoSalvo = cursoRepository.save(curso);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cursoSalvo.getId())
                .toUri();

        return ResponseEntity.created(location).body(cursoSalvo);
    }

    @GetMapping
    public ResponseEntity<List<Curso>> listar() {
        List<Curso> cursos = cursoRepository.findAll();
        return ResponseEntity.ok(cursos);
    }

    @PutMapping("{id}")
    @Transactional
    public ResponseEntity<Curso> salvar(@PathVariable Long id, @RequestBody Curso curso) {

        Curso cursoFind = cursoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        cursoFind.setDescricao(curso.getDescricao());
        cursoFind.setNome(curso.getNome());
        cursoFind.setPreco(curso.getPreco());
        cursoFind.setNivel(curso.getNivel());
        cursoFind.setInstrutor(curso.getInstrutor());

        Curso cursoAtualizado = cursoRepository.save(curso);

        return ResponseEntity.ok(cursoAtualizado);
    }

    @DeleteMapping("{id}")
    @Transactional
    public ResponseEntity<?> excluir(@PathVariable Long id) {
        Curso cursoFind = cursoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        cursoRepository.delete(cursoFind);

        return ResponseEntity.noContent().build();
    }


    @PostMapping("{cursoId}/aulas")
    @Transactional
    public ResponseEntity<Aula> salvarAula(@PathVariable() Long cursoId, @RequestBody Aula aula) {

        Curso curso = cursoRepository.findById(cursoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        aula.setId(null);
        aula.setCurso(curso);

        Aula aulaSalva = aulaRepository.save(aula);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(aulaSalva.getId())
                .toUri();

        return ResponseEntity.created(location).body(aulaSalva);
    }


}
