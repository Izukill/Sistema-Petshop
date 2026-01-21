package org.example.entidades;


import jakarta.persistence.*;
import org.example.dao.EntidadeAtivavel;

@Entity
@Table(name = "Pessoas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa implements EntidadeAtivavel{


    public Pessoa(Boolean ativo, String email, String endereco, String nome, String telefone) {
        this.ativo = ativo;
        this.email = email;
        this.endereco = endereco;
        this.nome = nome;
        this.telefone = telefone;
    }

    public Pessoa(){

    }


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    protected Integer id;

    protected String nome;

    protected String endereco;

    protected String email;

    protected String telefone;

    protected Boolean ativo;


    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Integer getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
