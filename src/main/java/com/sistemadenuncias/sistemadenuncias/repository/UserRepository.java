package com.sistemadenuncias.sistemadenuncias.repository;

import com.sistemadenuncias.sistemadenuncias.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByReparticaoIdReparticao(Integer id);

    Optional<User> findByEmail(String email);
}
