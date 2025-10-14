package com.sistemadenuncias.sistemadenuncias.DTO;

import java.time.LocalDateTime;

public class EvidenciaResponseDTO {
    private Long id;
    private String caminhoArquivo;
    private String tipoArquivo;
    private LocalDateTime dataUpload;
    private String protocoloDenuncia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }

    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public String getTipoArquivo() {
        return tipoArquivo;
    }

    public void setTipoArquivo(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }

    public LocalDateTime getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(LocalDateTime dataUpload) {
        this.dataUpload = dataUpload;
    }

    public String getProtocoloDenuncia() {
        return protocoloDenuncia;
    }

    public void setProtocoloDenuncia(String protocoloDenuncia) {
        this.protocoloDenuncia = protocoloDenuncia;
    }
}
