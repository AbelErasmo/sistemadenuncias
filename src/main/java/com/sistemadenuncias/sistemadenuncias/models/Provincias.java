package com.sistemadenuncias.sistemadenuncias.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "provincias")
public class Provincias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_provincias", nullable = false)
    private Long id;

    @Column(name = "nome", nullable = false, unique = true, length = 100)
    private String nome;

    public Provincias() {
    }

    public Provincias(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Provincias that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Provincias{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}
