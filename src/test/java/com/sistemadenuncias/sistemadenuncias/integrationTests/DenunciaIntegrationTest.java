package com.sistemadenuncias.sistemadenuncias.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemadenuncias.sistemadenuncias.DTO.DenunciaRequestDTO;
import com.sistemadenuncias.sistemadenuncias.models.Provincias;
import com.sistemadenuncias.sistemadenuncias.models.Reparticao;
import com.sistemadenuncias.sistemadenuncias.repository.ProvinciaRepository;
import com.sistemadenuncias.sistemadenuncias.repository.ReparticaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@Rollback
class DenunciaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private ReparticaoRepository reparticaoRepository;

    private Long provinciaId;
    private Long reparticaoId;

    @BeforeEach
    void setup() {
        Provincias provincia = new Provincias();
        provincia.setNome("Gaza");
        provincia = provinciaRepository.save(provincia);
        provinciaId = provincia.getId();

        Reparticao reparticao = new Reparticao();
        reparticao.setNome("Repartição Central");
        reparticao.setProvincias(provincia);
        reparticao = reparticaoRepository.save(reparticao);
        reparticaoId = reparticao.getIdReparticao().longValue();
    }

    @Test
    void deveCriarDenunciaComSucesso() throws Exception {
        DenunciaRequestDTO dto = new DenunciaRequestDTO();
        dto.setNome("João");
        dto.setEmail("joao@example.com");
        dto.setDescricao("Corrupção na repartição");
        dto.setLocalOcorrencia("Xai-Xai");
        dto.setDataOcorrencia(LocalDateTime.of(2025, 10, 14, 10, 0));
        dto.setProvinciaId(provinciaId);
        dto.setReparticaoId(reparticaoId);

        mockMvc.perform(post("/api/denuncias")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.protocolo").exists())
                .andExpect(jsonPath("$.nome").value("João"))
                .andExpect(jsonPath("$.status").value("PENDENTE"));
    }
}
