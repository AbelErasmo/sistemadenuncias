package com.sistemadenuncias.sistemadenuncias.repository;

import com.sistemadenuncias.sistemadenuncias.models.Evidencias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EvidenciasRepository extends JpaRepository<Evidencias, Long> {
    List<Evidencias> findByDenunciaId(Long denunciaId);
}

