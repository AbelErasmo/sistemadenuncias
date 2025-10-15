package com.sistemadenuncias.sistemadenuncias.controller;

import com.sistemadenuncias.sistemadenuncias.models.Provincias;
import com.sistemadenuncias.sistemadenuncias.models.Reparticao;
import com.sistemadenuncias.sistemadenuncias.models.User;
import com.sistemadenuncias.sistemadenuncias.repository.ProvinciaRepository;
import com.sistemadenuncias.sistemadenuncias.repository.ReparticaoRepository;
import com.sistemadenuncias.sistemadenuncias.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ReparticaoRepository reparticaoRepository;

    @Autowired
    private ProvinciaRepository provinciasRepository;


    @BeforeEach
    public void setupUsuario() {
        Provincias provincias = provinciasRepository.findById(1L)
                .orElseGet(() -> {
                    Provincias nova = new Provincias();
                    nova.setNome("Maputo");
                    return provinciasRepository.save(nova);
                });

        Reparticao reparticao = reparticaoRepository.findById(1L)
                .orElseGet(() -> {
                    Reparticao nova = new Reparticao();
                    nova.setNome("Central");
                    nova.setProvincias(provincias);
                    return reparticaoRepository.save(nova);
                });

        if (userRepository.findByEmail("admin@sistema.com").isEmpty()) {
            User user = new User();
            user.setEmail("admin@sistema.com");
            user.setSenha(passwordEncoder.encode("senha123"));
            user.setNome("Admin");
            user.setApelido("Master");
            user.setReparticao(reparticao);
            user.setDataRegisto(LocalDateTime.now());
            user.setRoles(Set.of("ROLE_ADMIN"));

            userRepository.save(user);
        }
    }


    @Test
    public void deveAutenticarEDevolverTokens() throws Exception {
        String json = """
            {
              "email": "admin@sistema.com",
              "senha": "senha123"
            }
        """;

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }
}

