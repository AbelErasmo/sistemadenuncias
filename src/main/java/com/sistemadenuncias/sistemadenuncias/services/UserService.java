package com.sistemadenuncias.sistemadenuncias.services;

import com.sistemadenuncias.sistemadenuncias.DTO.UserRequestDTO;
import com.sistemadenuncias.sistemadenuncias.DTO.UserResponseDTO;
import com.sistemadenuncias.sistemadenuncias.models.Reparticao;
import com.sistemadenuncias.sistemadenuncias.models.User;
import com.sistemadenuncias.sistemadenuncias.repository.ReparticaoRepository;
import com.sistemadenuncias.sistemadenuncias.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ReparticaoRepository reparticaoRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ReparticaoRepository reparticaoRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.reparticaoRepository = reparticaoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User criar(UserRequestDTO dto) {
        Reparticao reparticao = reparticaoRepository.findById(dto.getReparticaoId())
                .orElseThrow(() -> new RuntimeException("Repartição não encontrada"));

        validarSenha(dto);
        String senhaCriptografada = passwordEncoder.encode(dto.getSenha());

        User user = new User();
        user.setNome(dto.getNome());
        user.setApelido(dto.getApelido());
        user.setEmail(dto.getEmail());
        user.setSenha(senhaCriptografada);
        user.setReparticao(reparticao);
        user.setDataRegisto(java.time.LocalDateTime.now());

        return userRepository.save(user);
    }

    private static void validarSenha(UserRequestDTO dto) {
        if (dto.getSenha() == null || dto.getSenha().isBlank()) {
            throw new IllegalArgumentException("Senha não pode estar vazia");
        }
    }

    public List<UserResponseDTO> listarPorReparticao(Integer reparticaoId) {
        return userRepository.findByReparticaoIdReparticao(reparticaoId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getIdUser());
        dto.setNome(user.getNome());
        dto.setApelido(user.getApelido());
        dto.setEmail(user.getEmail());
        dto.setReparticao(user.getReparticao().getNome());
        return dto;
    }
}
