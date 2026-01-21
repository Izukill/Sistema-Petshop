package org.example.entidades;


import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Fornecedores")
public class Fornecedor extends Pessoa{

    public Fornecedor(Boolean ativo, String email, String endereco, String nome, String telefone, String categoria, String cnpj) {
        super(ativo, email, endereco, nome, telefone);
        this.categoria = categoria;
        this.cnpj = cnpj;
    }

    public Fornecedor() {
    }

    @OneToMany(mappedBy = "fornecedor")
    private List<Produto> produtos = new ArrayList<>();

    private String categoria;

    private String cnpj;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
