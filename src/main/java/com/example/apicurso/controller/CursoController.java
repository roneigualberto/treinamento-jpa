package com.example.apicurso.controller;


import com.example.apicurso.dto.CursoProjectionDTO;
import com.example.apicurso.dto.CursoProjectionDTO2;
import com.example.apicurso.dto.CursoProjectionDTO3;
import com.example.apicurso.model.Aula;
import com.example.apicurso.model.Categoria;
import com.example.apicurso.model.Curso;
import com.example.apicurso.model.NivelCursoEnum;
import com.example.apicurso.repository.AulaRepository;
import com.example.apicurso.repository.CategoriaRepository;
import com.example.apicurso.repository.CursoRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/cursos")
@AllArgsConstructor
public class CursoController {

    private final CursoRepository cursoRepository;

    private final AulaRepository aulaRepository;

    private final CategoriaRepository categoriaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<Curso> salvar(@RequestBody Curso curso) {

        Curso cursoSalvo = cursoRepository.save(curso);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(cursoSalvo.getId())
                .toUri();


        return ResponseEntity.created(location).body(cursoSalvo);
    }

    @GetMapping
    public ResponseEntity<Page<Curso>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String instrutor,
            @RequestParam(required = false) NivelCursoEnum nivel,
            @RequestParam(required = false) Double preco,

            //@PageableDefault(size = 5, page = 1, sort = {"nome"})

            Pageable pageable) {

        Curso cursoFiltro = Curso.builder().nivel(nivel).instrutor(instrutor).nome(nome).preco(preco).build();

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("instrutor", match -> match.contains().ignoreCase())
                .withMatcher("nome", match -> match.contains().ignoreCase());

        Example<Curso> example = Example.of(cursoFiltro, matcher);

        Page<Curso> cursos = cursoRepository.findAll(example, pageable);

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


    @PostMapping("{cursoId}/categorias/{categoriaId}")
    @Transactional
    public ResponseEntity<Categoria> addCategoria(@PathVariable Long cursoId, @PathVariable Long categoriaId) {
        Curso curso = cursoRepository.findById(cursoId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso não encontrado"));

        Categoria categoria = categoriaRepository.findById(categoriaId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria nao encontrada"));

        curso.addCategoria(categoria);

        return ResponseEntity.ok().build();

    }

    @GetMapping("by-nome/{nome}")
    public ResponseEntity<List<Curso>> byNome(@PathVariable String nome) {

        List<Curso> cursos = cursoRepository.searchByNome(nome);

        return ResponseEntity.ok(cursos);
    }

    @GetMapping("by-nome-like/{nome}")
    public ResponseEntity<List<Curso>> byNomeLike(@PathVariable String nome) {

        List<Curso> cursos = cursoRepository.readByNomeLike("%" + nome + "%");

        return ResponseEntity.ok(cursos);
    }

    @GetMapping("by-nome-containing/{nome}")
    public ResponseEntity<List<Curso>> getByNomeContaining(@PathVariable String nome) {

        List<Curso> cursos = cursoRepository.getByNomeContaining(nome);

        return ResponseEntity.ok(cursos);
    }

    @GetMapping("by-nivel-in")
    public ResponseEntity<List<Curso>> queryByNivelIn(@RequestParam("nivel") Set<NivelCursoEnum> nivel) {

        List<Curso> cursos = cursoRepository.queryByNivelIn(nivel);

        return ResponseEntity.ok(cursos);
    }

    @GetMapping("top-3/by-nivel/{nivel}")
    public ResponseEntity<List<Curso>> queryByNivelIn(@PathVariable NivelCursoEnum nivel) {

        List<Curso> cursos = cursoRepository.findTop3ByNivelOrderByPreco(nivel);

        return ResponseEntity.ok(cursos);
    }

    @GetMapping("top-3/by-categoria/{categoria}")
    public ResponseEntity<List<Curso>> queryByCategoria(@PathVariable String categoria) {

        List<Curso> cursos = cursoRepository.findTop3ByCategorias_NomeOrderByPreco(categoria);

        return ResponseEntity.ok(cursos);
    }

    @GetMapping("by-preco-between/{min}/{max}")
    public ResponseEntity<List<Curso>> byPrecoBetween(@PathVariable Double min, @PathVariable Double max) {

        List<Curso> cursos = cursoRepository.findByPrecoBetween(min, max);

        return ResponseEntity.ok(cursos);
    }

    @GetMapping("by-instrutor/{instrutor}")
    public ResponseEntity<List<CursoProjectionDTO>> byInstrutor(@PathVariable String instrutor) {

        List<CursoProjectionDTO> cursos = cursoRepository.findByInstrutor(instrutor, CursoProjectionDTO.class);

        return ResponseEntity.ok(cursos);
    }

    @GetMapping("by-instrutor-2/{instrutor}")
    public ResponseEntity<List<CursoProjectionDTO2>> byInstrutor2(@PathVariable String instrutor) {

        List<CursoProjectionDTO2> cursos = cursoRepository.findByInstrutor(instrutor, CursoProjectionDTO2.class);

        return ResponseEntity.ok(cursos);
    }

    @GetMapping("by-instrutor-3/{instrutor}")
    public ResponseEntity<List<CursoProjectionDTO3>> byInstrutor3(@PathVariable String instrutor) {

        List<CursoProjectionDTO3> cursos = cursoRepository.findByInstrutor(instrutor, CursoProjectionDTO3.class);

        return ResponseEntity.ok(cursos);
    }


}
