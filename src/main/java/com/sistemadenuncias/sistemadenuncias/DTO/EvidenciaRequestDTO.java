package com.sistemadenuncias.sistemadenuncias.DTO;

public class EvidenciaRequestDTO {
    private String caminhoArquivo;
    private String tipoArquivo;
    private Long denunciaId;

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

    public Long getDenunciaId() {
        return denunciaId;
    }

    public void setDenunciaId(Long denunciaId) {
        this.denunciaId = denunciaId;
    }
}
