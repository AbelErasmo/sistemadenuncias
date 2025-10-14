package com.sistemadenuncias.sistemadenuncias.services;

import com.sistemadenuncias.sistemadenuncias.DTO.DenunciaRequestDTO;
import com.sistemadenuncias.sistemadenuncias.DTO.DenunciaResponseDTO;
import com.sistemadenuncias.sistemadenuncias.models.*;
import com.sistemadenuncias.sistemadenuncias.repository.DenunciaRepository;
import com.sistemadenuncias.sistemadenuncias.repository.ProvinciaRepository;
import com.sistemadenuncias.sistemadenuncias.repository.ReparticaoRepository;
import com.sistemadenuncias.sistemadenuncias.utils.ProtocoloGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DenunciaService {

    private final DenunciaRepository denunciaRepository;
    private final ProvinciaRepository provinciaRepository;
    private final ReparticaoRepository reparticaoRepository;

    public DenunciaService(DenunciaRepository denunciaRepository,
                           ProvinciaRepository provinciaRepository,
                           ReparticaoRepository reparticaoRepository) {
        this.denunciaRepository = denunciaRepository;
        this.provinciaRepository = provinciaRepository;
        this.reparticaoRepository = reparticaoRepository;
    }

    public Denuncia criar(DenunciaRequestDTO dto) {
        Denuncia denuncia = new Denuncia();
        Provincias provincia = provinciaRepository.findProvinciaById(dto.getProvinciaId())
                .orElseThrow(() -> new RuntimeException("Província não encontrada"));
        denuncia.setProvincia(provincia);
        Reparticao reparticao = reparticaoRepository.findById(dto.getReparticaoId())
                .orElseThrow(() -> new RuntimeException("Repartição não encontrada"));
        denuncia.setReparticao(reparticao);

        denuncia.setNome(dto.getNome());
        denuncia.setEmail(dto.getEmail());
        denuncia.setDescricao(dto.getDescricao());
        denuncia.setLocalOcorrencia(dto.getLocalOcorrencia());
        denuncia.setDataOcorrencia(dto.getDataOcorrencia());
        denuncia.setProtocolo(ProtocoloGenerator.gerarProtocoloPorProvincia(provincia));
        denuncia.setReparticao(reparticao);


        return denunciaRepository.save(denuncia);
    }

    public Denuncia buscarPorProtocolo(String protocolo) {
        return denunciaRepository.findByProtocolo(protocolo)
                .orElseThrow(() -> new RuntimeException("Denúncia não encontrada"));
    }

    public List<DenunciaResponseDTO> listarPorStatus(StatusDenuncia status) {
        List<Denuncia> denuncias = denunciaRepository.findByStatus(status);
        return denuncias.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public List<DenunciaResponseDTO> listarPorProvincia(Provincias provincia) {
        List<Denuncia> denuncias = denunciaRepository.findByProvincia(provincia);
        return denuncias.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public DenunciaResponseDTO toResponseDTO(Denuncia denuncia) {
        DenunciaResponseDTO dto = new DenunciaResponseDTO();
        dto.setId(denuncia.getId());
        dto.setProtocolo(denuncia.getProtocolo());
        dto.setNome(denuncia.getNome());
        dto.setEmail(denuncia.getEmail());
        dto.setDescricao(denuncia.getDescricao());
        dto.setLocalOcorrencia(denuncia.getLocalOcorrencia());
        dto.setDataOcorrencia(denuncia.getDataOcorrencia());
        dto.setDataDenuncia(denuncia.getDataDenuncia());
        dto.setProvincia(denuncia.getProvincia().getNome());
        dto.setReparticao(denuncia.getReparticao().getNome());
        dto.setStatus(denuncia.getStatus().name());
        return dto;
    }
}

