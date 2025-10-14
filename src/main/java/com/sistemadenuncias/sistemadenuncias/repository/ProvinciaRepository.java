package com.sistemadenuncias.sistemadenuncias.repository;

import com.sistemadenuncias.sistemadenuncias.models.Provincias;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincias, Long> {

    Optional<Provincias> findProvinciaById(Long id);
    Optional<Provincias> findByNome(String nome);
}
