package com.sistemadenuncias.sistemadenuncias.DTO;

import java.time.LocalDateTime;

public class DenunciaResponseDTO {

    private Long id;
    private String protocolo;
    private String nome;
    private String email;
    private String descricao;
    private String localOcorrencia;
    private LocalDateTime dataOcorrencia;
    private LocalDateTime dataDenuncia;
    private String provincia;
    private String reparticao;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

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

    public LocalDateTime getDataDenuncia() {
        return dataDenuncia;
    }

    public void setDataDenuncia(LocalDateTime dataDenuncia) {
        this.dataDenuncia = dataDenuncia;
    }

    public LocalDateTime getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDateTime dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getReparticao() {
        return reparticao;
    }

    public void setReparticao(String reparticao) {
        this.reparticao = reparticao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

