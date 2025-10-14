package com.sistemadenuncias.sistemadenuncias.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistemadenuncias.sistemadenuncias.DTO.UserRequestDTO;
import com.sistemadenuncias.sistemadenuncias.DTO.UserResponseDTO;
import com.sistemadenuncias.sistemadenuncias.models.Reparticao;
import com.sistemadenuncias.sistemadenuncias.models.User;
import com.sistemadenuncias.sistemadenuncias.services.UserService;
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
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void deveCriarUsuarioComSucesso() throws Exception {
        UserRequestDTO request = new UserRequestDTO();
        request.setNome("Erasmo");
        request.setApelido("Sibinde");
        request.setEmail("erasmo@example.com");
        request.setSenha("segura123");
        request.setReparticaoId(2L);

        Reparticao reparticao = new Reparticao();
        reparticao.setIdReparticao(2L);
        reparticao.setNome("Repartição Central");

        User user = new User();
        user.setIdUser(1);
        user.setNome("Erasmo");
        user.setApelido("Sibinde");
        user.setEmail("erasmo@example.com");
        user.setSenha("segura123");
        user.setReparticao(reparticao);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(1);
        response.setNome("Erasmo");
        response.setApelido("Sibinde");
        response.setEmail("erasmo@example.com");
        response.setReparticao("Repartição Central");

        when(userService.criar(any())).thenReturn(user);
        when(userService.toResponseDTO(user)).thenReturn(response);

        mockMvc.perform(post("/api/usuarios")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Erasmo"))
                .andExpect(jsonPath("$.reparticao").value("Repartição Central"));
    }

    @Test
    void deveListarUsuariosPorReparticao() throws Exception {
        UserResponseDTO dto1 = new UserResponseDTO();
        dto1.setId(1);
        dto1.setNome("Erasmo");
        dto1.setApelido("Sibinde");
        dto1.setEmail("erasmo@example.com");
        dto1.setReparticao("Repartição Central");

        UserResponseDTO dto2 = new UserResponseDTO();
        dto2.setId(2);
        dto2.setNome("Joana");
        dto2.setApelido("Matos");
        dto2.setEmail("joana@example.com");
        dto2.setReparticao("Repartição Central");

        when(userService.listarPorReparticao(2)).thenReturn(List.of(dto1, dto2));

        mockMvc.perform(get("/api/usuarios/reparticao/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Erasmo"))
                .andExpect(jsonPath("$[1].nome").value("Joana"));
    }
}
