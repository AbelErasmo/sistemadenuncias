package com.sistemadenuncias.sistemadenuncias.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemadenuncias.sistemadenuncias.DTO.DenunciaRequestDTO;
import com.sistemadenuncias.sistemadenuncias.DTO.DenunciaResponseDTO;
import com.sistemadenuncias.sistemadenuncias.models.Denuncia;
import com.sistemadenuncias.sistemadenuncias.models.StatusDenuncia;
import com.sistemadenuncias.sistemadenuncias.repository.ProvinciaRepository;
import com.sistemadenuncias.sistemadenuncias.services.DenunciaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class DenunciaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DenunciaService denunciaService;

    @MockBean
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarDenunciaComSucesso() throws Exception {
        DenunciaRequestDTO request = new DenunciaRequestDTO();
        request.setNome("João");
        request.setEmail("joao@example.com");
        request.setDescricao("Corrupção na repartição");
        request.setLocalOcorrencia("Xai-Xai");
        request.setDataOcorrencia(LocalDateTime.of(2025, 10, 14, 10, 0));
        request.setProvinciaId(1L);
        request.setReparticaoId(2L);

        Denuncia denuncia = new Denuncia();
        denuncia.setId(1L);
        denuncia.setProtocolo("DNC-GA-20251014-ABC123");
        denuncia.setNome("João");
        denuncia.setEmail("joao@example.com");
        denuncia.setDescricao("Corrupção na repartição");
        denuncia.setLocalOcorrencia("Xai-Xai");
        denuncia.setDataOcorrencia(request.getDataOcorrencia());
        denuncia.setDataDenuncia(LocalDateTime.of(2025, 10, 14, 11, 0));
        denuncia.setStatus(StatusDenuncia.PENDENTE);

        DenunciaResponseDTO response = new DenunciaResponseDTO();
        response.setId(1L);
        response.setProtocolo("DNC-GA-20251014-ABC123");
        response.setNome("João");
        response.setEmail("joao@example.com");
        response.setDescricao("Corrupção na repartição");
        response.setLocalOcorrencia("Xai-Xai");
        response.setDataOcorrencia(request.getDataOcorrencia());
        response.setDataDenuncia(denuncia.getDataDenuncia());
        response.setProvincia("Gaza");
        response.setReparticao("Repartição X");
        response.setStatus(StatusDenuncia.PENDENTE.name());

        when(denunciaService.criar(any())).thenReturn(denuncia);
        when(denunciaService.toResponseDTO(denuncia)).thenReturn(response);

        mockMvc.perform(post("/api/denuncias")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.protocolo").value("DNC-GA-20251014-ABC123"))
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }

    @Test
    void deveBuscarPorProtocolo() throws Exception {
        String protocolo = "DNC-GA-20251014-ABC123";

        Denuncia denuncia = new Denuncia();
        denuncia.setId(1L);
        denuncia.setProtocolo(protocolo);
        denuncia.setNome("João");
        denuncia.setStatus(StatusDenuncia.PENDENTE);

        DenunciaResponseDTO response = new DenunciaResponseDTO();
        response.setId(1L);
        response.setProtocolo(protocolo);
        response.setNome("João");
        response.setStatus(StatusDenuncia.PENDENTE.name());

        when(denunciaService.buscarPorProtocolo(protocolo)).thenReturn(denuncia);
        when(denunciaService.toResponseDTO(denuncia)).thenReturn(response);

        mockMvc.perform(get("/api/denuncias/protocolo/" + protocolo))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.protocolo").value(protocolo))
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    void deveListarPorStatus() throws Exception {
        DenunciaResponseDTO dto1 = new DenunciaResponseDTO();
        dto1.setId(1L);
        dto1.setNome("João");
        dto1.setStatus(StatusDenuncia.PENDENTE.name());

        DenunciaResponseDTO dto2 = new DenunciaResponseDTO();
        dto2.setId(2L);
        dto2.setNome("Maria");
        dto2.setStatus(StatusDenuncia.PENDENTE.name());

        when(denunciaService.listarPorStatus(StatusDenuncia.PENDENTE)).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/api/denuncias/status/PENDENTE"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("João"))
                .andExpect(jsonPath("$[1].nome").value("Maria"));
    }
}
