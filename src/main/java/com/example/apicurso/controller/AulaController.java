package com.example.apicurso.controller;


import com.example.apicurso.model.Aula;
import com.example.apicurso.model.VideoAula;
import com.example.apicurso.repository.AulaRepository;
import com.example.apicurso.repository.VideoAulaRepository;
import lombok.AllArgsConstructor;
import org.apache.tika.Tika;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/aulas")
@AllArgsConstructor
public class AulaController {


    private final AulaRepository aulaRepository;

    private final VideoAulaRepository videoAulaRepository;


    @PostMapping("{aulaId}/video-aula")
    public ResponseEntity<?> upload(@PathVariable Long aulaId, @RequestParam("video") MultipartFile file) throws IOException {

        Aula aula = aulaRepository.findById(aulaId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula não encontrada"));

        Tika tika = new Tika();

        String contentType = tika.detect(file.getBytes());

        if (!contentType.equals("video/mp4")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Formato invalido");
        }


        VideoAula videoAula = VideoAula.builder().aula(aula).conteudo(file.getBytes()).tipo(file.getContentType())
                .nomeArquivo(file.getOriginalFilename())
                .tamanho(file.getSize())
                .build();

        VideoAula videoAulaSalva = videoAulaRepository.save(videoAula);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(videoAulaSalva.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @GetMapping("{aulaId}/video-aula/{videoAulaId}")
    public ResponseEntity<?> download(@PathVariable Long aulaId, @PathVariable Long videoAulaId) {

        Aula aula = aulaRepository.findById(aulaId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aula não encontrada"));

        VideoAula videoAula = videoAulaRepository.findById(videoAulaId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Video-Aula não encontrada"));


        if (!videoAula.getAula().equals(aula)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Video Aula não pertence a esta aula");
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(videoAula.getTipo()))
                .body(videoAula.getConteudo());
    }


}
