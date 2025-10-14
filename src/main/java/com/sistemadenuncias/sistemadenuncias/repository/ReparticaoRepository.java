package com.sistemadenuncias.sistemadenuncias.repository;

import com.sistemadenuncias.sistemadenuncias.models.Reparticao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReparticaoRepository extends JpaRepository<Reparticao, Long> {

    List<Reparticao> findByProvinciasId(Integer id);

    List<Reparticao> findByProvinciasNome(String nome);

}
