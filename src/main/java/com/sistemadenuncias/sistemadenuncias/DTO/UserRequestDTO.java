package com.sistemadenuncias.sistemadenuncias.DTO;

public class UserRequestDTO {

    private String nome;
    private String apelido;
    private String email;
    private String senha;
    private Long reparticaoId;

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

    public Long getReparticaoId() {
        return reparticaoId;
    }

    public void setReparticaoId(Long reparticaoId) {
        this.reparticaoId = reparticaoId;
    }
}
