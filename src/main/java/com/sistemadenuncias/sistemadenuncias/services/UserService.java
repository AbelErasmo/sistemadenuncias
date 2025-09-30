package com.sistemadenuncias.sistemadenuncias.services;

import com.sistemadenuncias.sistemadenuncias.models.User;
import com.sistemadenuncias.sistemadenuncias.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> listarUsuariosPorReparticao(Integer idReparticao) {
        return userRepository.findByReparticao_idReparticao(idReparticao);
    }

    public User cadastrar(@NotNull User user) {
        String senhaCriptografada = new BCryptPasswordEncoder().encode(user.getSenha());
        user.setSenha(senhaCriptografada);
        return userRepository.save(user);
    }

}
