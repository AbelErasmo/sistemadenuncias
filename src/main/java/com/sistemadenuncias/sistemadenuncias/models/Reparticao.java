package com.sistemadenuncias.sistemadenuncias.models;

import jakarta.persistence.*;

@Entity
@Table(name = "reparticao")
public class Reparticao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reparticao")
    private Long idReparticao;

    @Column(name = "nome_reparticao", nullable = false)
    private String nome;

    @ManyToOne(optional = false)
    @JoinColumn(name = "provincias_id", referencedColumnName = "id_provincias")
    private Provincias provincias;

    public Long getIdReparticao() {
        return idReparticao;
    }

    public void setIdReparticao(Long idReparticao) {
        this.idReparticao = idReparticao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Provincias getProvincias() {
        return provincias;
    }

    public void setProvincias(Provincias provincias) {
        this.provincias = provincias;
    }
}
