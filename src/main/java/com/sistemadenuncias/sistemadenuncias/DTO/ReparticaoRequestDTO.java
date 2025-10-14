package com.sistemadenuncias.sistemadenuncias.DTO;

public class ReparticaoRequestDTO {
    private String nome;
    private Long provinciaId;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getProvinciaId() {
        return provinciaId;
    }

    public void setProvinciaId(Long provinciaId) {
        this.provinciaId = provinciaId;
    }
}
