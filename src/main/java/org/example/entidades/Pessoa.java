package org.example.entidades;


import jakarta.persistence.*;
import org.example.dao.EntidadeAtivavel;

@Entity
@Table(name = "Pessoas")
@Inheritance(strategy = InheritanceType.JOINED)
public class Pessoa implements EntidadeAtivavel{


    public Pessoa(Boolean ativo, String cpf, String email, String endereco, String nome, String telefone) {
        this.ativo = ativo;
        this.cpf = cpf;
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

    protected String cpf;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
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
