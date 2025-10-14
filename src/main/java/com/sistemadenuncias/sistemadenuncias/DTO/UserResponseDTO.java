package com.sistemadenuncias.sistemadenuncias.DTO;

public class UserResponseDTO {
    private Integer id;
    private String nome;
    private String apelido;
    private String email;
    private String reparticao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getReparticao() {
        return reparticao;
    }

    public void setReparticao(String reparticao) {
        this.reparticao = reparticao;
    }
}
