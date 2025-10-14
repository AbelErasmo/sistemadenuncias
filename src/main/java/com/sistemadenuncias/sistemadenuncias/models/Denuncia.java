package com.sistemadenuncias.sistemadenuncias.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "denuncias")
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_denuncia")
    private Long id;

    @Column(nullable = false, unique = true)
    private String protocolo;

    private String nome;

    @Column(length = 100)
    private String email;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Column(name = "local_ocorrencia", nullable = false)
    private String localOcorrencia;

    @Column(name = "data_ocorrencia", nullable = false)
    private LocalDateTime dataOcorrencia;

    @Column(name = "data_denuncia", nullable = false)
    private LocalDateTime dataDenuncia = LocalDateTime.now();

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_provincias", referencedColumnName = "id_provincias")
    private Provincias provincia;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusDenuncia status = StatusDenuncia.PENDENTE;

    @ManyToOne
    @JoinColumn(name = "reparticao_id")
    private Reparticao reparticao;

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

    public LocalDateTime getDataOcorrencia() {
        return dataOcorrencia;
    }

    public void setDataOcorrencia(LocalDateTime dataOcorrencia) {
        this.dataOcorrencia = dataOcorrencia;
    }

    public LocalDateTime getDataDenuncia() {
        return dataDenuncia;
    }

    public void setDataDenuncia(LocalDateTime dataDenuncia) {
        this.dataDenuncia = dataDenuncia;
    }

    public StatusDenuncia getStatus() {
        return status;
    }

    public void setStatus(StatusDenuncia status) {
        this.status = status;
    }

    public Provincias getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincias provincia) {
        this.provincia = provincia;
    }

    public Reparticao getReparticao() {
        return reparticao;
    }

    public void setReparticao(Reparticao reparticao) {
        this.reparticao = reparticao;
    }
}
