package br.com.notifycar.model;

import java.io.Serializable;

/**
 * Created by Desenvolvimento on 23/09/2016.
 */
public class Usuario implements Serializable {

    private String nome;
    private String email;
    private String senha;
    private String id;
    private String dataAtualizado;

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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataAtualizado() {
        return dataAtualizado;
    }

    public void setDataAtualizado(String dataAtualizado) {
        this.dataAtualizado = dataAtualizado;
    }

    @Override
    public String toString() {
        return nome + " - " + id;
    }
}
