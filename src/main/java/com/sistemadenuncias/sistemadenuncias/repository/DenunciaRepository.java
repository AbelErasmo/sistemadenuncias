package com.sistemadenuncias.sistemadenuncias.repository;

import com.sistemadenuncias.sistemadenuncias.models.Denuncia;
import com.sistemadenuncias.sistemadenuncias.models.Provincias;
import com.sistemadenuncias.sistemadenuncias.models.StatusDenuncia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DenunciaRepository extends JpaRepository<Denuncia, Long> {
    List<Denuncia> findByProvincia(Provincias provincia);
    List<Denuncia> findByStatus(StatusDenuncia status);
    Optional<Denuncia> findByProtocolo(String protocolo);
}

