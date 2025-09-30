package com.sistemadenuncias.sistemadenuncias.models;

import jakarta.persistence.*;

@Entity
@Table(name = "denuncias")
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDenuncia;

    /**
     * Protocolo único associado à uma denúncia, utilizado para identificação e acompanhamento da mesma.
     */
    @Id
    @Column
    private String protocolo;

    @Column
    private String tipoDenuncia;


}
