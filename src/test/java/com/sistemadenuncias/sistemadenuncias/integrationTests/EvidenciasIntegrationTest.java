package com.sistemadenuncias.sistemadenuncias.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemadenuncias.sistemadenuncias.DTO.EvidenciaRequestDTO;
import com.sistemadenuncias.sistemadenuncias.models.Denuncia;
import com.sistemadenuncias.sistemadenuncias.models.StatusDenuncia;
import com.sistemadenuncias.sistemadenuncias.repository.DenunciaRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@Rollback
class EvidenciasIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DenunciaRepository denunciaRepository;

    private Long denunciaId;

    @BeforeEach
    void setup() {
        Denuncia denuncia = new Denuncia();
        denuncia.setNome("João");
        denuncia.setEmail("joao@example.com");
        denuncia.setDescricao("Corrupção");
        denuncia.setLocalOcorrencia("Xai-Xai");
        denuncia.setDataOcorrencia(LocalDateTime.now());
        denuncia.setDataDenuncia(LocalDateTime.now());
        denuncia.setStatus(StatusDenuncia.PENDENTE);
        denuncia.setProtocolo("DEN-001");
        denuncia = denunciaRepository.save(denuncia);
        denunciaId = denuncia.getId();
    }

    @Test
    void deveCriarEvidenciaComSucesso() throws Exception {
        EvidenciaRequestDTO dto = new EvidenciaRequestDTO();
        dto.setCaminhoArquivo("/uploads/denuncia001.jpg");
        dto.setTipoArquivo("image/jpeg");
        dto.setDenunciaId(denunciaId);

        mockMvc.perform(post("/api/evidencias")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.caminhoArquivo").value("/uploads/denuncia001.jpg"))
                .andExpect(jsonPath("$.protocoloDenuncia").value("DEN-001"));
    }
}

