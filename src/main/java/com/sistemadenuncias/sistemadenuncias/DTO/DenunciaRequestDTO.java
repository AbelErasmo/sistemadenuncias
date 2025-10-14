package com.sistemadenuncias.sistemadenuncias.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class DenunciaRequestDTO {

    private String nome;

    @Email
    private String email;

    @NotBlank
    private String descricao;

    @NotBlank
    private String localOcorrencia;

    @NotNull
    private LocalDateTime dataOcorrencia;

    @NotNull
    private Long provinciaId;

    @NotNull
    private Long reparticaoId;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocalOcorrencia() {
        return localOcorrencia;
    }

    public void setLocalOcorrencia(String localOcorrencia) {
        this.localOcorrencia = localOcorrencia;
    }

    public LocalDateTime getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDateTime dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public Long getProvinciaId() {
        return provinciaId;
    }

    public void setProvinciaId(Long provinciaId) {
        this.provinciaId = provinciaId;
    }

    public Long getReparticaoId() {
        return reparticaoId;
    }

    public void setReparticaoId(Long reparticaoId) {
        this.reparticaoId = reparticaoId;
    }
}

