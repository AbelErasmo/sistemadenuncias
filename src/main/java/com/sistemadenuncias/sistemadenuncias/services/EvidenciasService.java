package com.sistemadenuncias.sistemadenuncias.services;

import com.sistemadenuncias.sistemadenuncias.DTO.EvidenciaRequestDTO;
import com.sistemadenuncias.sistemadenuncias.DTO.EvidenciaResponseDTO;
import com.sistemadenuncias.sistemadenuncias.models.Denuncia;
import com.sistemadenuncias.sistemadenuncias.models.Evidencias;
import com.sistemadenuncias.sistemadenuncias.repository.DenunciaRepository;
import com.sistemadenuncias.sistemadenuncias.repository.EvidenciasRepository;
import com.sistemadenuncias.sistemadenuncias.utils.EvidenciaValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class EvidenciasService {

    private final EvidenciasRepository evidenciasRepository;
    private final DenunciaRepository denunciaRepository;
    private final EvidenciaStorageService storageService;
    private final EvidenciaValidator validator;

    public EvidenciasService(EvidenciasRepository evidenciasRepository,
                             DenunciaRepository denunciaRepository,
                             EvidenciaStorageService storageService,
                             EvidenciaValidator validator) {
        this.evidenciasRepository = evidenciasRepository;
        this.denunciaRepository = denunciaRepository;
        this.storageService = storageService;
        this.validator = validator;
    }

    public ResponseEntity<?> uploadMultiplo(Long denunciaId, List<MultipartFile> files) {
        Denuncia denuncia = denunciaRepository.findById(denunciaId)
                .orElseThrow(() -> new RuntimeException("Denúncia não encontrada"));

        List<String> salvos = new ArrayList<>();
        List<String> erros = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                validator.validar(file);

                String nomeFinal = storageService.salvar(file);

                Evidencias evidencia = new Evidencias();
                evidencia.setCaminhoArquivo(nomeFinal);
                evidencia.setTipoArquivo(file.getContentType());
                evidencia.setDataUpload(LocalDateTime.now());
                evidencia.setDenuncia(denuncia);
                evidenciasRepository.save(evidencia);

                salvos.add(nomeFinal);
            } catch (Exception e) {
                erros.add("Erro ao salvar: " + file.getOriginalFilename());
            }
        }

        Map<String, Object> resposta = new HashMap<>();
        resposta.put("status", "success");
        resposta.put("protocolo", denuncia.getProtocolo());
        resposta.put("arquivosSalvos", salvos);
        resposta.put("erros", erros);

        return ResponseEntity.ok(resposta);
    }

    public Evidencias salvar(EvidenciaRequestDTO dto) {
        Denuncia denuncia = denunciaRepository.findById(dto.getDenunciaId())
                .orElseThrow(() -> new RuntimeException("Denúncia não encontrada"));

        Evidencias evidencia = new Evidencias();
        evidencia.setCaminhoArquivo(dto.getCaminhoArquivo());
        evidencia.setTipoArquivo(dto.getTipoArquivo());
        evidencia.setDataUpload(LocalDateTime.now());
        evidencia.setDenuncia(denuncia);

        return evidenciasRepository.save(evidencia);
    }

    public EvidenciaResponseDTO toResponseDTO(Evidencias evidencia) {
        EvidenciaResponseDTO dto = new EvidenciaResponseDTO();
        dto.setId(evidencia.getId());
        dto.setCaminhoArquivo(evidencia.getCaminhoArquivo());
        dto.setTipoArquivo(evidencia.getTipoArquivo());
        dto.setDataUpload(evidencia.getDataUpload());
        dto.setProtocoloDenuncia(evidencia.getDenuncia().getProtocolo());
        return dto;
    }
}


