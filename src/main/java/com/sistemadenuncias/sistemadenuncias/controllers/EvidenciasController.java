package com.sistemadenuncias.sistemadenuncias.controllers;

import com.sistemadenuncias.sistemadenuncias.DTO.EvidenciaRequestDTO;
import com.sistemadenuncias.sistemadenuncias.DTO.EvidenciaResponseDTO;
import com.sistemadenuncias.sistemadenuncias.models.Evidencias;
import com.sistemadenuncias.sistemadenuncias.services.EvidenciasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/evidencias")
public class EvidenciasController {

    private final EvidenciasService evidenciasService;

    public EvidenciasController(EvidenciasService evidenciasService) {
        this.evidenciasService = evidenciasService;
    }

    @PostMapping
    public ResponseEntity<EvidenciaResponseDTO> criar(@RequestBody EvidenciaRequestDTO dto) {
        Evidencias evidencia = evidenciasService.salvar(dto);
        EvidenciaResponseDTO response = evidenciasService.toResponseDTO(evidencia);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/upload/{denunciaId}")
    public ResponseEntity<?> uploadArquivos(@PathVariable Long denunciaId,
                                            @RequestParam("files") List<MultipartFile> files) {
        return evidenciasService.uploadMultiplo(denunciaId, files);
    }
}

