package org.example.entidades;

import jakarta.persistence.*;
import org.example.dao.EntidadeAtivavel;

import java.math.BigDecimal;

@Entity
@Table(name = "Produtos")
public class Produto implements EntidadeAtivavel {


    public Produto(boolean ativo, String categoria, Fornecedor fornecedor, String nome, BigDecimal precoBase) {
        this.ativo = ativo;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
        this.nome = nome;
        this.precoBase = precoBase;
    }

    public Produto() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;

    private String nome;

    private String categoria;

    private BigDecimal precoBase;

    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id")
    private Fornecedor fornecedor;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
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

    public BigDecimal getPrecoBase() {
        return precoBase;
    }

    public void setPrecoBase(BigDecimal precoBase) {
        this.precoBase = precoBase;
    }

    @Override
    public void setAtivo(Boolean ativo) {
        this.ativo= ativo;
    }

    @Override
    public Boolean getAtivo() {
        return ativo;
    }
}
