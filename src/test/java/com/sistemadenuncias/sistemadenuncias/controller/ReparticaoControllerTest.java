package com.sistemadenuncias.sistemadenuncias.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemadenuncias.sistemadenuncias.DTO.ReparticaoRequestDTO;
import com.sistemadenuncias.sistemadenuncias.DTO.ReparticaoResponseDTO;
import com.sistemadenuncias.sistemadenuncias.models.Provincias;
import com.sistemadenuncias.sistemadenuncias.models.Reparticao;
import com.sistemadenuncias.sistemadenuncias.services.ReparticaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ReparticaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReparticaoService reparticaoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarReparticaoComSucesso() throws Exception {
        ReparticaoRequestDTO request = new ReparticaoRequestDTO();
        request.setNome("Repartição X");
        request.setProvinciaId(1L);

        Provincias provincia = new Provincias();
        provincia.setId(1L);
        provincia.setNome("Gaza");

        Reparticao reparticao = new Reparticao();
        reparticao.setIdReparticao(1L);
        reparticao.setNome("Repartição X");
        reparticao.setProvincias(provincia);

        ReparticaoResponseDTO response = new ReparticaoResponseDTO();
        response.setId(1);
        response.setNome("Repartição X");
        response.setProvincia("Gaza");

        when(reparticaoService.criar(any())).thenReturn(reparticao);
        when(reparticaoService.toResponseDTO(reparticao)).thenReturn(response);

        mockMvc.perform(post("/api/reparticoes")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Repartição X"))
                .andExpect(jsonPath("$.provincia").value("Gaza"));
    }

    @Test
    void deveListarReparticoesPorProvincia() throws Exception {
        ReparticaoResponseDTO dto1 = new ReparticaoResponseDTO();
        dto1.setId(1);
        dto1.setNome("Repartição A");
        dto1.setProvincia("Maputo");

        ReparticaoResponseDTO dto2 = new ReparticaoResponseDTO();
        dto2.setId(2);
        dto2.setNome("Repartição B");
        dto2.setProvincia("Maputo");

        when(reparticaoService.listarPorProvincia(1)).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/api/reparticoes/provincia/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Repartição A"))
                .andExpect(jsonPath("$[1].nome").value("Repartição B"));
    }
}
