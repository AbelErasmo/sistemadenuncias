package com.sistemadenuncias.sistemadenuncias.services;

import com.sistemadenuncias.sistemadenuncias.DTO.ReparticaoRequestDTO;
import com.sistemadenuncias.sistemadenuncias.DTO.ReparticaoResponseDTO;
import com.sistemadenuncias.sistemadenuncias.models.Provincias;
import com.sistemadenuncias.sistemadenuncias.models.Reparticao;
import com.sistemadenuncias.sistemadenuncias.repository.ProvinciaRepository;
import com.sistemadenuncias.sistemadenuncias.repository.ReparticaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReparticaoService {

    private final ReparticaoRepository reparticaoRepository;
    private final ProvinciaRepository provinciaRepository;

    public ReparticaoService(ReparticaoRepository reparticaoRepository, ProvinciaRepository provinciaRepository) {
        this.reparticaoRepository = reparticaoRepository;
        this.provinciaRepository = provinciaRepository;
    }

    public Reparticao criar(ReparticaoRequestDTO dto) {
        Provincias provincia = provinciaRepository.findProvinciaById(dto.getProvinciaId())
                .orElseThrow(() -> new RuntimeException("Província não encontrada"));

        Reparticao reparticao = new Reparticao();
        reparticao.setNome(dto.getNome());
        reparticao.setProvincias(provincia);

        return reparticaoRepository.save(reparticao);
    }

    public List<ReparticaoResponseDTO> listarPorProvincia(Integer provinciaId) {
        List<Reparticao> lista = reparticaoRepository.findByProvinciasId(provinciaId);
        return lista.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public ReparticaoResponseDTO toResponseDTO(Reparticao reparticao) {
        ReparticaoResponseDTO dto = new ReparticaoResponseDTO();
        dto.setId(Math.toIntExact(reparticao.getIdReparticao()));
        dto.setNome(reparticao.getNome());
        dto.setProvincia(reparticao.getProvincias().getNome());
        return dto;
    }
}
