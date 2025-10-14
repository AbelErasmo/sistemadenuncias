package com.sistemadenuncias.sistemadenuncias.integrationTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemadenuncias.sistemadenuncias.DTO.UserRequestDTO;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@Transactional
@Rollback
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private ReparticaoRepository reparticaoRepository;

    private Long reparticaoId;

    @BeforeEach
    void setup() {
        Provincias provincia = new Provincias();
        provincia.setNome("Maputo");
        provincia = provinciaRepository.save(provincia);

        Reparticao reparticao = new Reparticao();
        reparticao.setNome("Repartição Central");
        reparticao.setProvincias(provincia);
        reparticao = reparticaoRepository.save(reparticao);

        reparticaoId = reparticao.getIdReparticao();
    }

    @Test
    void deveCriarUsuarioComSucesso() throws Exception {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setNome("Erasmo");
        dto.setApelido("Sibinde");
        dto.setEmail("erasmo@example.com");
        dto.setSenha("segura123");
        dto.setReparticaoId(reparticaoId);

        mockMvc.perform(post("/api/usuarios")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("Erasmo"))
                .andExpect(jsonPath("$.reparticao").value("Repartição Central"));
    }
}
