package com.sistemadenuncias.sistemadenuncias.controllers;

import com.sistemadenuncias.sistemadenuncias.DTO.DenunciaRequestDTO;
import com.sistemadenuncias.sistemadenuncias.DTO.DenunciaResponseDTO;
import com.sistemadenuncias.sistemadenuncias.models.Provincias;
import com.sistemadenuncias.sistemadenuncias.models.StatusDenuncia;
import com.sistemadenuncias.sistemadenuncias.repository.ProvinciaRepository;
import com.sistemadenuncias.sistemadenuncias.services.DenunciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/denuncias")
public class DenunciaController {

    private final DenunciaService denunciaService;
    private final ProvinciaRepository provinciaRepository;

    public DenunciaController(DenunciaService denunciaService, ProvinciaRepository provinciaRepository) {
        this.denunciaService = denunciaService;
        this.provinciaRepository = provinciaRepository;
    }

    @PostMapping
    public ResponseEntity<DenunciaResponseDTO> criar(@RequestBody DenunciaRequestDTO dto) {
        var denuncia = denunciaService.criar(dto);
        var response = denunciaService.toResponseDTO(denuncia);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/protocolo/{codigo}")
    public ResponseEntity<DenunciaResponseDTO> buscarPorProtocolo(@PathVariable String codigo) {
        var denuncia = denunciaService.buscarPorProtocolo(codigo);
        var response = denunciaService.toResponseDTO(denuncia);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<DenunciaResponseDTO>> listarPorStatus(@PathVariable StatusDenuncia status) {
        var lista = denunciaService.listarPorStatus(status);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/provincia/{nome}")
    public ResponseEntity<List<DenunciaResponseDTO>> listarPorProvincia(@PathVariable String nome) {
        Provincias provincia = provinciaRepository.findByNome(nome)
                .orElseThrow(() -> new RuntimeException("Província não encontrada"));
        var lista = denunciaService.listarPorProvincia(provincia);
        return ResponseEntity.ok(lista);
    }

}
