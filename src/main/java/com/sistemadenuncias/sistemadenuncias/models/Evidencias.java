package com.sistemadenuncias.sistemadenuncias.models;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "evidencias")
public class Evidencias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDenuncia;

    private String caminho_arquivo;
    private String tipo_arquivo;

    private Timestamp data_upload;

//    @OneToMany
//    private Denuncia denuncias_id;

    public Long getIdDenuncia() {
        return idDenuncia;
    }

    public void setIdDenuncia(Long idDenuncia) {
        this.idDenuncia = idDenuncia;
    }

    public String getCaminho_arquivo() {
        return caminho_arquivo;
    }

    public void setCaminho_arquivo(String caminho_arquivo) {
        this.caminho_arquivo = caminho_arquivo;
    }

    public String getTipo_arquivo() {
        return tipo_arquivo;
    }

    public void setTipo_arquivo(String tipo_arquivo) {
        this.tipo_arquivo = tipo_arquivo;
    }

    public Timestamp getData_upload() {
        return data_upload;
    }

    public void setData_upload(Timestamp data_upload) {
        this.data_upload = data_upload;
    }

//    public Denuncia getDenuncias_id() {
//        return denuncias_id;
//    }
//
//    public void setDenuncias_id(Denuncia denuncias_id) {
//        this.denuncias_id = denuncias_id;
//    }

}
