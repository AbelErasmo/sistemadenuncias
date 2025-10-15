package com.sistemadenuncias.sistemadenuncias.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    private String nome;
    private String apelido;
    private String email;
    private String senha;

    @Column(name = "data_registro")
    private LocalDateTime dataRegisto;

    @ManyToOne
    @JoinColumn(name = "reparticao_id", nullable = false)
    private Reparticao reparticao;

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getDataRegisto() {
        return dataRegisto;
    }

    public void setDataRegisto(LocalDateTime dataRegisto) {
        this.dataRegisto = dataRegisto;
    }

    public Reparticao getReparticao() {
        return reparticao;
    }

    public void setReparticao(Reparticao reparticao) {
        this.reparticao = reparticao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(getIdUser(), user.getIdUser());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getIdUser());
    }

    @Override
    public String toString() {
        return "User{" +
                "idUser=" + idUser +
                ", nome='" + nome + '\'' +
                ", apelido='" + apelido + '\'' +
                ", email='" + email + '\'' +
                ", dataRegisto=" + dataRegisto +
                ", reparticao=" + reparticao +
                '}';
    }
}
