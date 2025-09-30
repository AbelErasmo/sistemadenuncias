package com.sistemadenuncias.sistemadenuncias.models;

import jakarta.persistence.*;

@Entity
@Table(name = "reparticao")
public class Reparticao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reparticao")
    private Integer idReparticao;

    public Integer getIdReparticao() {
        return idReparticao;
    }

    public void setIdReparticao(Integer idReparticao) {
        this.idReparticao = idReparticao;
    }
}
