package org.example.entidades;


import jakarta.persistence.*;
import org.example.dao.EntidadeAtivavel;

import java.util.List;

@Entity
public class Estoque implements EntidadeAtivavel {

    public Estoque(List<ItemEstoque> itens) {
        this.itens = itens;
        this.ativo=true;
    }


    public Estoque() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;


    @OneToMany(mappedBy = "estoque", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<ItemEstoque> itens;

    private boolean ativo;


    public Integer getId() {
        return id;
    }

    public List<ItemEstoque> getItens() {
        return itens;
    }

    public void setItens(List<ItemEstoque> itens) {
        this.itens = itens;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    @Override
    public Boolean getAtivo() {
        return ativo;
    }
}
