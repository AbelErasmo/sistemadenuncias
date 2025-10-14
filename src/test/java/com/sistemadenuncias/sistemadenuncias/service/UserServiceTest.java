package com.sistemadenuncias.sistemadenuncias.service;

import com.sistemadenuncias.sistemadenuncias.DTO.UserRequestDTO;
import com.sistemadenuncias.sistemadenuncias.DTO.UserResponseDTO;
import com.sistemadenuncias.sistemadenuncias.models.Reparticao;
import com.sistemadenuncias.sistemadenuncias.models.User;
import com.sistemadenuncias.sistemadenuncias.repository.ReparticaoRepository;
import com.sistemadenuncias.sistemadenuncias.repository.UserRepository;
import com.sistemadenuncias.sistemadenuncias.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReparticaoRepository reparticaoRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void deveCriarUsuarioComSucesso() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setNome("Erasmo");
        dto.setApelido("Sibinde");
        dto.setEmail("erasmo@example.com");
        dto.setSenha("segura123");
        dto.setReparticaoId(2L);

        Reparticao reparticao = new Reparticao();
        reparticao.setIdReparticao(2L);
        reparticao.setNome("Repartição Central");

        when(reparticaoRepository.findById(2L)).thenReturn(Optional.of(reparticao));
        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        User userCriado = userService.criar(dto);

        assertEquals("Erasmo", userCriado.getNome());
        assertEquals("Repartição Central", userCriado.getReparticao().getNome());
        assertNotNull(userCriado.getDataRegisto());
    }

    @Test
    void deveListarUsuariosPorReparticao() {
        Reparticao reparticao = new Reparticao();
        reparticao.setIdReparticao(2L);
        reparticao.setNome("Repartição Central");

        User user1 = new User();
        user1.setIdUser(1);
        user1.setNome("Erasmo");
        user1.setApelido("Sibinde");
        user1.setEmail("erasmo@example.com");
        user1.setReparticao(reparticao);

        User user2 = new User();
        user2.setIdUser(2);
        user2.setNome("Joana");
        user2.setApelido("Matos");
        user2.setEmail("joana@example.com");
        user2.setReparticao(reparticao);

        when(userRepository.findByReparticaoIdReparticao(2)).thenReturn(List.of(user1, user2));

        List<UserResponseDTO> lista = userService.listarPorReparticao(2);

        assertEquals(2, lista.size());
        assertEquals("Erasmo", lista.get(0).getNome());
        assertEquals("Joana", lista.get(1).getNome());
        assertEquals("Repartição Central", lista.get(0).getReparticao());
    }

    @Test
    void deveConverterParaResponseDTO() {
        Reparticao reparticao = new Reparticao();
        reparticao.setIdReparticao(2L);
        reparticao.setNome("Repartição Central");

        User user = new User();
        user.setIdUser(1);
        user.setNome("Erasmo");
        user.setApelido("Sibinde");
        user.setEmail("erasmo@example.com");
        user.setReparticao(reparticao);

        UserResponseDTO dto = userService.toResponseDTO(user);

        assertEquals(1, dto.getId());
        assertEquals("Erasmo", dto.getNome());
        assertEquals("Sibinde", dto.getApelido());
        assertEquals("Repartição Central", dto.getReparticao());
    }
}
