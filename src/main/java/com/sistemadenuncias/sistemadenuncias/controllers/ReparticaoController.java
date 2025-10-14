package com.sistemadenuncias.sistemadenuncias.controllers;

import com.sistemadenuncias.sistemadenuncias.DTO.ReparticaoRequestDTO;
import com.sistemadenuncias.sistemadenuncias.DTO.ReparticaoResponseDTO;
import com.sistemadenuncias.sistemadenuncias.models.Reparticao;
import com.sistemadenuncias.sistemadenuncias.services.ReparticaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reparticoes")
public class ReparticaoController {

    private final ReparticaoService reparticaoService;

    public ReparticaoController(ReparticaoService reparticaoService) {
        this.reparticaoService = reparticaoService;
    }

    @PostMapping
    public ResponseEntity<ReparticaoResponseDTO> criar(@RequestBody ReparticaoRequestDTO dto) {
        Reparticao reparticao = reparticaoService.criar(dto);
        ReparticaoResponseDTO response = reparticaoService.toResponseDTO(reparticao);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/provincia/{id}")
    public ResponseEntity<List<ReparticaoResponseDTO>> listarPorProvincia(@PathVariable Integer id) {
        List<ReparticaoResponseDTO> lista = reparticaoService.listarPorProvincia(id);
        return ResponseEntity.ok(lista);
    }
}