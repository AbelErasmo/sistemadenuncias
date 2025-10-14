package com.sistemadenuncias.sistemadenuncias.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemadenuncias.sistemadenuncias.DTO.ReparticaoRequestDTO;
import com.sistemadenuncias.sistemadenuncias.models.Provincias;
import com.sistemadenuncias.sistemadenuncias.repository.ProvinciaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@Rollback
class ReparticaoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    private Long provinciaId;

    @BeforeEach
    void setup() {
        Provincias provincia = new Provincias();
        provincia.setNome("Maputo");
        provincia = provinciaRepository.save(provincia);
        provinciaId = provincia.getId();
    }

    @Test
    void deveCriarReparticaoComSucesso() throws Exception {
        ReparticaoRequestDTO dto = new ReparticaoRequestDTO();
        dto.setNome("Repartição Fiscal");
        dto.setProvinciaId(provinciaId);

        mockMvc.perform(post("/api/reparticoes")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Repartição Fiscal"))
                .andExpect(jsonPath("$.provincia").value("Maputo"));
    }
}
